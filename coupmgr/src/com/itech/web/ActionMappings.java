package com.itech.web;

import java.util.HashMap;
import java.util.Map;

import com.itech.alert.web.AlertAction;
import com.itech.common.web.action.ActionMapping;
import com.itech.coupon.web.CouponAction;

public class ActionMappings {
	private static final Map<String, ActionMapping> actions = new HashMap<String, ActionMapping>();
	public static  void add(ActionMapping actionMapping) {
		actions.put(actionMapping.getUri(), actionMapping);
	}

	public static ActionMapping getAction(String uri) {
		return actions.get(uri);
	}

	static {
		//FbLogin Mappings
		add(new ActionMapping("fbLogin.do", FbLoginAction.class, "doFbUserLoginToApp"));
		add(new ActionMapping("facebookCallbackUrl.do", FbLoginAction.class, "doHandleFbUserCode"));
		add(new ActionMapping("setFbAdapter.do", FbLoginAction.class, "setFbAdapter"));
		add(new ActionMapping("testFbAction.do", FbLoginAction.class, "testFbAction"));

		//Coupon/Offer actions
		add(new ActionMapping("home.do", CouponAction.class, "goToHome"));
		add(new ActionMapping("wallet.do", CouponAction.class, "goToMyWallet"));
		add(new ActionMapping("getMyCoupons.do", CouponAction.class, "getMyCoupons"));
		add(new ActionMapping("coupons.do", CouponAction.class, "goToMyWallet"));
		add(new ActionMapping("saveCoupons.do", CouponAction.class, "saveCoupons"));
		add(new ActionMapping("deleteCoupons.do", CouponAction.class, "deleteCoupons"));

		//Alert actions
		add(new ActionMapping("alerts.do", AlertAction.class, "goToMyAlerts"));
		add(new ActionMapping("getMyAlerts.do", AlertAction.class, "getMyAlerts"));
		add(new ActionMapping("deleteAlerts.do", AlertAction.class, "deleteAlerts"));
		add(new ActionMapping("markAlertsRead.do", AlertAction.class, "markAlertRead"));

		//Login actions
		add(new ActionMapping("login.do", LoginAction.class, "login"));
		add(new ActionMapping("signup.do", LoginAction.class, "signup"));
		add(new ActionMapping("logout.do", LoginAction.class, "logout"));
		add(new ActionMapping("getPassword.do", LoginAction.class, "getPassword"));


		add(new ActionMapping("loginDone.do", LoginAction.class, "loginDone"));
		add(new ActionMapping("signupDone.do", LoginAction.class, "signUpDone"));
		add(new ActionMapping("emailLogin.do", LoginAction.class, "emailLogin"));
		add(new ActionMapping("emailSignup.do", LoginAction.class, "emailSignUp"));

	}
}
