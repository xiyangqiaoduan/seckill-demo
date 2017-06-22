package com.yangcb.seckill.dto;

import java.io.Serializable;

import com.yangcb.seckill.enums.SeckillStatEnum;
import com.yangcb.seckill.model.SuccessSeckilled;

/**
 * 秒杀执行后的结果
 * 
 * @author Administrator
 *
 */
public class SeckillExecution implements Serializable {

	private static final long serialVersionUID = 9038116503245680322L;
	private long seckillId;
	/**
	 * 秒杀状态
	 */
	private int state;

	/**
	 * 状态标示
	 */
	private String stateInfo;

	private SuccessSeckilled successSeckilled;

	public SeckillExecution(long seckillId, SeckillStatEnum state, SuccessSeckilled successSeckilled) {
		super();
		this.seckillId = seckillId;
		this.state = state.getState();
		this.stateInfo = state.getStateInfo();
		this.successSeckilled = successSeckilled;
	}

	public SeckillExecution(long seckillId, SeckillStatEnum state) {
		super();
		this.seckillId = seckillId;
		this.state = state.getState();
		this.stateInfo = state.getStateInfo();
	}

	public SeckillExecution() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public SuccessSeckilled getSuccessSeckilled() {
		return successSeckilled;
	}

	public void setSuccessSeckilled(SuccessSeckilled successSeckilled) {
		this.successSeckilled = successSeckilled;
	}

	@Override
	public String toString() {
		return "SeckillExecution [seckillId=" + seckillId + ", state=" + state + ", stateInfo=" + stateInfo
				+ ", successSeckilled=" + successSeckilled + "]";
	}

	
	
	
}
