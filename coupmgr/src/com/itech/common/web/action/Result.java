package com.itech.common.web.action;


public class Result <T> {

	private T result = null;
	private String msg = null;
	private boolean success;
	public Result(boolean success, T result, String msg) {
		super();
		this.setSuccess(success);
		this.result = result;
		this.msg = msg;
	}

	public Result() {
		super();
	}

	public Result(boolean success, final T result) {
		this.setSuccess(success);
		this.result = result;
	}

	public Result(final T result) {
		this(true, result);
	}


	public T getResult() {
		return result;
	}

	public void setResult(final T result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isSuccess() {
		return success;
	}

}
