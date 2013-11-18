package com.itech.common.web.action;

import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itech.common.security.SecurityContext;
import com.itech.common.security.SecurityContextHolder;
import com.itech.common.security.SecurityManager;
import com.itech.common.security.ThreadLocalSecurityContextHolder;
import com.itech.common.services.ServiceLocator;
import com.itech.config.ProjectConfigs;
import com.itech.student.manager.StudentManager;
import com.itech.user.manager.UserManager;
import com.itech.user.model.User;

public class CommonAction {
	private static final String SEARCH_CRITERIA_PARAM_KEY = "searchCriteria";
	private static final String SEARCH_CRITERIA_TYPE_PARAM_KEY = "searchCriteriaType"; //offer, card etc

	private static final String SEARCH_CRITERIA_SEARCH_STRING_PARAM_KEY = "q";
	private static final String SEARCH_CRITERIA_PUBLIC_SEARCH_PARAM_KEY = "public";

	private SecurityManager securityManager;
	private SecurityContextHolder securityContextHolder;
	private UserManager userManager;
	private StudentManager studentManager;

	public User getLoggedInUser() {
		return getSecurityManager().getLoggedInUser();
	}

	public List<Long> getIdListFromRequest(HttpServletRequest req, String paramName) {
		String listJson = req.getParameter(paramName);
		Gson gson = new Gson();
		Type type = new TypeToken<List<Long>>() { }.getType();
		List<Long> ids = gson.fromJson(listJson, type);
		return ids;
	}

	public void updateLoggedInUser(HttpServletRequest req, User user) {
		req.getSession().setAttribute(SecurityContext.USER_SESSION_KEY, user);
		getSecurityContextHolder().setContext(new SecurityContext(user));
		if (user != null) {
			userManager.updateUserLastLoginTime(user);
		}
	}

	protected String getAbsoluteURL(HttpServletRequest httpRequest, String url) {
		//TODO: add handling for production server
		String absoluteURL = ProjectConfigs.defaultServerUrl.get();
		absoluteURL += url;
		return absoluteURL;
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

	public UserManager getUserManager() {
		if (userManager == null) {
			userManager = ServiceLocator.getInstance().getBean(UserManager.class);
		}
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public void setSecurityManager(SecurityManager securityManager) {
		this.securityManager = securityManager;
	}
	public SecurityManager getSecurityManager() {
		if (securityManager == null) {
			securityManager = ServiceLocator.getInstance().getBean(SecurityManager.class);
		}
		return securityManager;
	}

	public StudentManager getStudentManager() {
		if (studentManager == null) {
			studentManager = ServiceLocator.getInstance().getBean(StudentManager.class);
		}
		return studentManager;
	}

	public void setStudentManager(StudentManager studentManager) {
		this.studentManager = studentManager;
	}

}
