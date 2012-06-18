package com.itech.common.services;

import com.itech.common.security.SecurityManager;
import com.itech.coupon.model.User;

public class CommonBaseManager {
	private SecurityManager securityManager;

	public User getLoggedInUser() {
		return getSecurityManager().getLoggedInUser();
	}

	public void setSecurityManager(SecurityManager securityManager) {
		this.securityManager = securityManager;
	}

	public SecurityManager getSecurityManager() {
		return securityManager;
	}

}
