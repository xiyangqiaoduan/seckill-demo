package com.yangcb.seckill.model;

import java.io.Serializable;
import java.util.Date;

public class Seckill implements Serializable {
	private static final long serialVersionUID = 5311217228022800034L;

	private long seckillId;
	private String name;
	private Date startTime;
	private Date endTime;
	private Date createTime;
	private int number;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	@Override
	public String toString() {
		return "Seckill [seckillId=" + seckillId + ", name=" + name + ", startTime=" + startTime + ", endTime="
				+ endTime + ", createTime=" + createTime + ", number=" + number + "]";
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

}
