package com.yangcb.seckill.service.core.biz;

import com.yangcb.seckill.dto.Exposer;
import com.yangcb.seckill.dto.SeckillExecution;
import com.yangcb.seckill.enums.SeckillStatEnum;
import com.yangcb.seckill.exception.RepeatKillException;
import com.yangcb.seckill.exception.SeckillCloseException;
import com.yangcb.seckill.exception.SeckillException;
import com.yangcb.seckill.model.Seckill;
import com.yangcb.seckill.model.SuccessSeckilled;
import com.yangcb.seckill.service.core.dao.SeckillDao;
import com.yangcb.seckill.service.core.dao.SuccessSeckilledDao;
import com.yangcb.seckill.service.core.dao.cache.RedisDao;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Component("seckillServiceBiz")
@Transactional(rollbackFor = Exception.class)
public class SeckillServiceBiz {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SeckillDao seckillDao;
	@Autowired
	private SuccessSeckilledDao successSeckilledDao;
	@Autowired
	private RedisDao redisDao;

	/**
	 * 混淆md5
	 */
	private final String slat = "123456";// 混淆的作用

	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, 4);
	}

	public Seckill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	public Exposer exportSeckillUrl(long seckillId) {
		// 缓存中获取
		Seckill seckill = redisDao.getSeckill(seckillId);
		if (seckill == null) {
			seckill = seckillDao.queryById(seckillId);
			if (seckill == null) {
				return new Exposer(false, seckillId);
			}
		} else {
			redisDao.putSeckill(seckill);
		}
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		// 系统当前时间
		Date nowTime = new Date();
		if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
			return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
		}
		/** md5加密 **/
		String md5 = getMD5(seckillId);
		return new Exposer(true, md5, seckillId);
	}

	private String getMD5(long seckillId) {
		String base = seckillId + "/" + slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

	@Transactional
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillCloseException {
		try {
			if (md5 == null || !md5.equals(getMD5(seckillId))) {
				throw new SeckillException("seckill data rewrite");
			}
			// 购买行为 降低mysql的持有时间
			int insertCount = successSeckilledDao.insertSuccessKilled(seckillId, userPhone);
			if (insertCount <= 0) {
				throw new RepeatKillException("repeat seckill");
			} else {
				Date now = new Date();
				// 更新 mysql会有行级锁
				int updateCount = seckillDao.reduceNumber(seckillId, now);
				if (updateCount <= 0) {
					// 没有更新记录，秒杀结束
					throw new SeckillCloseException("seckill is close");
				} else {
					// 秒杀成功
					SuccessSeckilled successSeckilled = successSeckilledDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successSeckilled);
				}

			}
			// 执行减库存

		} catch (SeckillCloseException closeException) {
			throw closeException;
		} catch (RepeatKillException repeatKillException) {
			throw repeatKillException;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// 所有编译期异常
			throw new SeckillException("seckill inner error" + e.getMessage());
		}
	}

	public SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5) {
		try {
			if (md5 == null || !md5.equals(getMD5(seckillId))) {
				throw new SeckillException("seckill data rewrite");
			}
			Date killTime = new Date();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("seckillId", seckillId);
			param.put("userPhone", userPhone);
			param.put("killTime", killTime);
			param.put("result", null);
			// 执行完sql result被赋值
			seckillDao.killByProcedure(param);
			// 获取result 值
			int result = MapUtils.getInteger(param, "result", -2);
			if (result == 1) {
				SuccessSeckilled successSeckilled = successSeckilledDao.queryByIdWithSeckill(seckillId, userPhone);
				return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successSeckilled);
			} else {
				return new SeckillExecution(seckillId, SeckillStatEnum.stateOf(result));
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// 所有编译期异常
			return new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
		}
	}

}
