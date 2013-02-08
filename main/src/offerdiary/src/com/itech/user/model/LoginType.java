package com.itech.user.model;

public enum LoginType {
	INTERNAL, EMAIL,  FACEBOOK, TWITTER, GOOGLE, UNKNOWN, MULTI;



	public String getName(){
		return this.name();

	}


}
