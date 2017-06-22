package com.yangcb.seckill.service.facade;

import com.yangcb.seckill.dto.Exposer;
import com.yangcb.seckill.dto.SeckillExecution;
import com.yangcb.seckill.exception.RepeatKillException;
import com.yangcb.seckill.exception.SeckillCloseException;
import com.yangcb.seckill.exception.SeckillException;
import com.yangcb.seckill.model.Seckill;
import com.yangcb.seckill.service.SeckillFacade;
import com.yangcb.seckill.service.core.biz.SeckillServiceBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author yangcb
 * @create 2017-06-22 13:39
 **/
@Component("seckillFacade")
public class SeckillFacadeImpl implements SeckillFacade{

    @Autowired
    private SeckillServiceBiz seckillServiceBiz;

    public List<Seckill> getSeckillList() {
        return seckillServiceBiz.getSeckillList();
    }

    public Seckill getById(long seckillId) {
        return seckillServiceBiz.getById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        return seckillServiceBiz.exportSeckillUrl(seckillId);
    }

    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        return seckillServiceBiz.executeSeckill(seckillId, userPhone, md5);
    }

    public SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        return seckillServiceBiz.executeSeckillProcedure(seckillId, userPhone, md5);
    }
}
