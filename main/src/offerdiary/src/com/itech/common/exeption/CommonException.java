package com.itech.common.exeption;


public class CommonException extends RuntimeException{
	private static final long serialVersionUID = -184856896L;
	private ReturnCode retCode = ReturnCodes.INTERNAL_ERROR;

	public CommonException() {
		super();
	}

	public CommonException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommonException(String message) {
		super(message);
	}

	public CommonException(Throwable cause) {
		super(cause);
	}

	public CommonException(ReturnCode retCode) {
		super();
		this.retCode = retCode;
	}

	public CommonException(String message, Throwable cause, ReturnCode retCode) {
		super(message, cause);
		this.retCode = retCode;
	}

	public CommonException(String message, ReturnCode retCode) {
		super(message);
		this.retCode = retCode;
	}

	public CommonException(Throwable cause, ReturnCode retCode) {
		super(cause);
		this.retCode = retCode;
	}

	public void setRetCode(ReturnCode retCode) {
		this.retCode = retCode;
	}

	public ReturnCode getRetCode() {
		return retCode;
	}
}
