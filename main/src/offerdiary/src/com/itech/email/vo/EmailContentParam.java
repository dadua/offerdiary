package com.itech.email.vo;

import java.util.HashMap;
import java.util.Map;

public class EmailContentParam {

	private  Map<String, String> PARAM_MAP =  new HashMap<String, String>();

	public static final String NEW_USER_REG_PARAM_NAME = "name";
	
	public static final String FORGOT_PASSWORD_PARAM_NAME="name";
	public static final String FORGOT_PASSWORD_PARAM_PASSWORD = "password";

	public static final String OFFER_EXPIRY_PARAM_NAME = "name";
	public static final String OFFER_EXPIRY_PARAM_OFFER= "offer";
	public Map<String, String> getPARAM_MAP() {
		return PARAM_MAP;
	}
	public void setPARAM_MAP(Map<String, String> PARAM_MAP) {
		this.PARAM_MAP = PARAM_MAP;
	}
	
	public EmailContentParam(){
		
	}
	
	public EmailContentParam(Map<String, String> paramMap){
		setPARAM_MAP(paramMap);	
	}
}