package com.itech.common.security;


public interface SecurityContextHolder {

	SecurityContext getContext();

	void clearContext();

	void setContext(SecurityContext context);

}
