package com.itech.common.security;

import com.itech.coupon.model.User;


public class SecurityManagerImpl implements SecurityManager {
	private SecurityContextHolder securityContextHolder;

	@Override
	public User getLoggedInUser() {
		return getSecurityContextHolder().getContext().getUser();
	}

	public void setSecurityContextHolder(SecurityContextHolder securityContextHolder) {
		this.securityContextHolder = securityContextHolder;
	}

	public SecurityContextHolder getSecurityContextHolder() {
		return securityContextHolder;
	}

}
