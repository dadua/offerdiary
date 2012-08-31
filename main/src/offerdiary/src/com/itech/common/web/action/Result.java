package com.itech.common.web.action;


import java.util.Locale;

import com.itech.common.exeption.ReturnCode;
import com.itech.common.exeption.ReturnCodes;
import com.itech.common.resource.Resource;

public class Result <T> {

	private ReturnCode returnCode = ReturnCodes.SUCCESS;
	private T result = null;
	private String msg = null;
	private boolean success;

	public Result(ReturnCode returnCode, T result, String msg) {
		super();
		this.returnCode = returnCode;
		this.result = result;
		this.msg = msg;
		this.success = this.returnCode.equals(ReturnCodes.SUCCESS);
	}

	public Result() {
		super();
	}

	public Result(final ReturnCode returnCode, final T result) {
		this.returnCode = returnCode;
		this.result = result;
		this.success = this.returnCode.equals(ReturnCodes.SUCCESS);
	}

	public Result(final T result) {
		this(ReturnCodes.SUCCESS, result);
	}

	public ReturnCode getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(final ReturnCode returnCode) {
		this.returnCode = returnCode;
	}

	public T getResult() {
		return result;
	}

	public void setResult(final T result) {
		this.result = result;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setError(Resource resource, Locale locale,ReturnCode code, Object... objects){
		setReturnCode(code);
		setMsg(resource.formatString(code.getDisplayKey(), locale, objects));
	}


}
