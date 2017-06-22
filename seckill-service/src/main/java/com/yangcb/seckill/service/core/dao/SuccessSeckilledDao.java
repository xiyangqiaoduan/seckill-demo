package com.yangcb.seckill.service.core.dao;

import com.yangcb.seckill.model.SuccessSeckilled;
import org.apache.ibatis.annotations.Param;


public interface SuccessSeckilledDao {
	/***
	 * 插入购买明细，可过滤重复
	 * 
	 * @param seckillId
	 * @param userPhone
	 * @return
	 */
	int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

	/**
	 * 根据ID查询SuccessSeckilled 并携带秒杀产品的对象信息
	 * 
	 * @param seckillId
	 * @return
	 */
	SuccessSeckilled queryByIdWithSeckill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

}
