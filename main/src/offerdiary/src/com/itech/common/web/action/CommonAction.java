package com.itech.common.web.action;

import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itech.alert.services.AlertManager;
import com.itech.common.CommonUtilities;
import com.itech.common.db.SearchCriteria;
import com.itech.common.security.SecurityContext;
import com.itech.common.security.SecurityContextHolder;
import com.itech.common.security.SecurityManager;
import com.itech.common.security.ThreadLocalSecurityContextHolder;
import com.itech.common.services.ServiceLocator;
import com.itech.offer.manager.OfferCardManager;
import com.itech.offer.manager.OfferManager;
import com.itech.offer.manager.VendorManager;
import com.itech.user.manager.UserManager;
import com.itech.user.model.User;

public class CommonAction {
	private static final String SEARCH_CRITERIA_PARAM_KEY = "searchCriteria";
	private SecurityManager securityManager;
	private SecurityContextHolder securityContextHolder;
	private UserManager userManager;
	private AlertManager alertManager;
	private OfferManager offerManager;
	private VendorManager vendorManager;
	private OfferCardManager offerCardManager;

	public User getLoggedInUser() {
		return getSecurityManager().getLoggedInUser();
	}

	public void updateLoggedInUser(HttpServletRequest req, User user) {
		req.getSession().setAttribute(SecurityContext.USER_SESSION_KEY, user);
		getSecurityContextHolder().setContext(new SecurityContext(user));
	}

	protected SearchCriteria getSearchCriteria(HttpServletRequest req){
		String searchCriteriaJSON = req.getParameter(SEARCH_CRITERIA_PARAM_KEY);
		if (CommonUtilities.isNullOrEmpty(searchCriteriaJSON)) {
			return new SearchCriteria();
		}
		Gson gson = new Gson();
		Type searchCriteriaJsonType = new TypeToken<SearchCriteria>() { }.getType();
		SearchCriteria searchCriteria = gson.fromJson(searchCriteriaJSON, searchCriteriaJsonType);
		return searchCriteria;
	}


	protected String getAbsoluteURL(HttpServletRequest httpRequest, String url) {
		//TODO: add handling for production server
		String absoluteURL = "http://" + httpRequest.getServerName() + ":" + httpRequest.getServerPort() + httpRequest.getContextPath();
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

	public void setAlertManager(AlertManager alertManager) {
		this.alertManager = alertManager;
	}

	public AlertManager getAlertManager() {
		if (alertManager == null) {
			alertManager = ServiceLocator.getInstance().getBean(AlertManager.class);
		}
		return alertManager;
	}

	public OfferManager getOfferManager() {
		if (offerManager == null) {
			offerManager = ServiceLocator.getInstance().getBean(OfferManager.class);
		}
		return offerManager;
	}

	public void setOfferManager(OfferManager offerManager) {
		this.offerManager = offerManager;
	}

	public void setVendorManager(VendorManager vendorManager) {
		this.vendorManager = vendorManager;
	}

	public VendorManager getVendorManager() {
		if (vendorManager == null) {
			vendorManager = ServiceLocator.getInstance().getBean(VendorManager.class);
		}
		return vendorManager;
	}

	public void setOfferCardManager(OfferCardManager offerCardManager) {
		this.offerCardManager = offerCardManager;
	}

	public OfferCardManager getOfferCardManager() {
		if (offerCardManager == null) {
			offerCardManager = ServiceLocator.getInstance().getBean(OfferCardManager.class);
		}
		return offerCardManager;
	}


}
