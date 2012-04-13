package com.itech.common.web.action;

import javax.servlet.http.HttpServletRequest;

import com.itech.common.security.SecurityContext;
import com.itech.common.security.SecurityContextHolder;
import com.itech.common.security.SecurityManager;
import com.itech.common.security.SecurityManagerImpl;
import com.itech.common.security.ThreadLocalSecurityContextHolder;
import com.itech.coupon.model.User;

public class CommonAction {
	private SecurityManager securityManager;
	private SecurityContextHolder securityContextHolder;
	public User getLoggedInUser() {
		return getSecurityManager().getLoggedInUser();
	}

	public void updateLoggedInUser(HttpServletRequest req, User user) {
		req.getSession().setAttribute(SecurityContext.USER_SESSION_KEY, user);
		getSecurityContextHolder().setContext(new SecurityContext(user));
	}


	public void setSecurityManager(SecurityManager securityManager) {
		this.securityManager = securityManager;
	}
	public SecurityManager getSecurityManager() {
		if (securityManager == null) {
			securityManager = new SecurityManagerImpl();
		}
		return securityManager;
	}

	public void setSecurityContextHolder(SecurityContextHolder securityContextHolder) {
		this.securityContextHolder = securityContextHolder;
	}

	public SecurityContextHolder getSecurityContextHolder() {
		if (securityContextHolder == null) {
			securityContextHolder = new ThreadLocalSecurityContextHolder();
		}
		return securityContextHolder;
	}

}
