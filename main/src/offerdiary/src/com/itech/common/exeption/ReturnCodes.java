package com.itech.common.exeption;


public class ReturnCodes {
	//Common Error Codes
	public static final ReturnCode SUCCESS = new ReturnCode(0);
	public static final ReturnCode VALIDATION_FAILURE = new ReturnCode(1);
	public static final ReturnCode INTERNAL_ERROR = new ReturnCode(2);
	public static final ReturnCode UNCLASSIFIED_DB_ERROR = new ReturnCode(3);
	public static final ReturnCode FEATURE_NOT_SUPPORTED_ERROR = new ReturnCode(6);
	public static final ReturnCode NO_ROWS_RETURNED = new ReturnCode(7);
	public static final ReturnCode UNEXPECTED_RESULT_COUNT_FROM_DB = new ReturnCode(8);
	public static final ReturnCode OBJECT_DOES_NOT_EXIST_ANYMORE =  new ReturnCode(9);


	public static final ReturnCode INVALID_CURRENT_PASSWORD =  new ReturnCode(100);
	public static final ReturnCode AUTHENTICATION_FAILURE  =  new ReturnCode(101);

	/**
	 * If the a ajax call which requires authentication is being called from a public page
	 * then this error is sent.. In this case the Client handles the login
	 */
	public static final ReturnCode AUTHENTICATION_FAILURE_FROM_PUBLIC_PAGE  =  new ReturnCode(103);
	public static final ReturnCode AUTHORIZATION_FAILURE  =  new ReturnCode(102);


}
