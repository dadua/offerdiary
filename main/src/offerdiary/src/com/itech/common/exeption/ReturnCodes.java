package com.itech.common.exeption;


public class ReturnCodes {
	//Common Error Codes
	public static final ReturnCode SUCCESS = new ReturnCode(0);
	public static final ReturnCode VALIDATION_FAILURE = new ReturnCode(1);
	public static final ReturnCode INTERNAL_ERROR = new ReturnCode(2);
	public static final ReturnCode UNCLASSIFIED_DB_ERROR = new ReturnCode(3);
	public static final ReturnCode FEATURE_NOT_SUPPORTED_ERROR = new ReturnCode(6);
	public static final ReturnCode NO_ROWS_RETURNED = new ReturnCode(7);

}
