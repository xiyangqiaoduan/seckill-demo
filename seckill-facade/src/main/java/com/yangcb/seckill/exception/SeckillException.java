package com.yangcb.seckill.exception;

public class SeckillException extends RuntimeException{

	private static final long serialVersionUID = -7396415307402624097L;

	public SeckillException(String message) {
		super(message);
	}

	public SeckillException(String message, Throwable cause) {
		super(message, cause);
	}

	
	
}
