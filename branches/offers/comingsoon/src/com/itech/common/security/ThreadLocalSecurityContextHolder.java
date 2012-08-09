package com.itech.common.security;


public class ThreadLocalSecurityContextHolder implements SecurityContextHolder{
	private static final ThreadLocal<SecurityContext> securityContext = new ThreadLocal<SecurityContext>();

	@Override
	public SecurityContext getContext() {
		return securityContext.get();
	}

	@Override
	public void clearContext() {
		securityContext.set(null);
	}

	@Override
	public void setContext(SecurityContext context) {
		if (context == null) {
			throw new RuntimeException("Only non-null SecurityContext instances are permitted");
		}
		securityContext.set(context);
	}
}
