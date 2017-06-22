package com.yangcb.seckill.exception;

public class SeckillCloseException extends SeckillException {

	private static final long serialVersionUID = 2097251203693613231L;

	public SeckillCloseException(String message) {
		super(message);
	}

	public SeckillCloseException(String message, Throwable cause) {
		super(message, cause);
	}

}
