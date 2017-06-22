package com.yangcb.seckill.service;

import java.util.List;

import com.yangcb.seckill.dto.Exposer;
import com.yangcb.seckill.dto.SeckillExecution;
import com.yangcb.seckill.exception.RepeatKillException;
import com.yangcb.seckill.exception.SeckillCloseException;
import com.yangcb.seckill.exception.SeckillException;
import com.yangcb.seckill.model.Seckill;


/***
 * 站在使用者的角度设计接口
 * 
 * 1、方法 定义粒度明确   2、参数  简练直接  3、返回类型   return 类型友好 异常的抛出
 * 
 * @author Administrator
 *
 */
public interface SeckillFacade {

	/**
	 * 查询所有秒杀记录
	 * @return
	 */
	
	List<Seckill> getSeckillList();
	/**
	 * 单个秒杀记录
	 * @param seckillId
	 * @return
	 */
	Seckill getById(long seckillId);
	/**
	 * 秒杀开启输出秒杀接口地址，
	 * 否则输出系统时间和秒杀时间 
	 * 
	 * @param seckillId
	 */
	Exposer exportSeckillUrl(long seckillId);
	
	/**
	 * 执行秒杀操作
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 */
	SeckillExecution executeSeckill(long seckillId,long userPhone,String md5) throws SeckillException,RepeatKillException,SeckillCloseException;
	
	/**
	 * 调用存储过程执行秒杀
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 * @return
	 * @throws SeckillException
	 * @throws RepeatKillException
	 * @throws SeckillCloseException
	 */
	SeckillExecution executeSeckillProcedure(long seckillId,long userPhone,String md5) throws SeckillException,RepeatKillException,SeckillCloseException;

	
	
}
