package com.itech.common.web.action;

import javax.servlet.http.HttpServletRequest;

import com.itech.alert.services.AlertManager;
import com.itech.common.security.SecurityContext;
import com.itech.common.security.SecurityContextHolder;
import com.itech.common.security.SecurityManager;
import com.itech.common.security.ThreadLocalSecurityContextHolder;
import com.itech.common.services.ServiceLocator;
import com.itech.coupon.manager.CouponManager;
import com.itech.coupon.manager.StoreManager;
import com.itech.coupon.manager.UserManager;
import com.itech.coupon.model.User;
import com.itech.offer.manager.OfferManager;
import com.itech.offer.manager.VendorManager;

public class CommonAction {
	private SecurityManager securityManager;
	private SecurityContextHolder securityContextHolder;
	private CouponManager couponManager;
	private UserManager userManager;
	private StoreManager storeManager;
	private AlertManager alertManager;
	private OfferManager offerManager;
	private VendorManager vendorManager;

	public User getLoggedInUser() {
		return getSecurityManager().getLoggedInUser();
	}

	public void updateLoggedInUser(HttpServletRequest req, User user) {
		req.getSession().setAttribute(SecurityContext.USER_SESSION_KEY, user);
		getSecurityContextHolder().setContext(new SecurityContext(user));
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

	public CouponManager getCouponManager() {
		if (couponManager == null) {
			couponManager = ServiceLocator.getInstance().getBean(CouponManager.class);
		}
		return couponManager;
	}

	public void setCouponManager(CouponManager couponManager) {
		this.couponManager = couponManager;
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

	public StoreManager getStoreManager() {
		if (storeManager == null) {
			storeManager = ServiceLocator.getInstance().getBean(StoreManager.class);
		}
		return storeManager;
	}

	public void setStoreManager(StoreManager storeManager) {

		this.storeManager = storeManager;
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


}
