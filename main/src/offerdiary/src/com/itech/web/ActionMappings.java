package com.itech.web;

import java.util.HashMap;
import java.util.Map;

import com.itech.alert.web.AlertAction;
import com.itech.common.web.action.ActionMapping;
import com.itech.offer.web.OfferAction;
import com.itech.offer.web.OfferCardAction;
import com.itech.offer.web.VendorAction;
import com.itech.user.web.ProfileAction;

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
		add(new ActionMapping("home.do", OfferAction.class, "goToHome"));
		add(new ActionMapping("wallet.do", OfferAction.class, "goToMyWallet"));
		add(new ActionMapping("getMyOffers.do", OfferAction.class, "getMyOffers"));
		add(new ActionMapping("offers.do", OfferAction.class, "goToMyWallet"));
		add(new ActionMapping("saveOffers.do", OfferAction.class, "saveOffers"));
		add(new ActionMapping("deleteOffers.do", OfferAction.class, "deleteOffers"));
		add(new ActionMapping("searchOffers.do", OfferAction.class, "searchOffers"));
		add(new ActionMapping("getOffersOnMyCards.do", OfferAction.class, "getOffersOnMyCards"));
		add(new ActionMapping("getOffersOnCard.do", OfferAction.class, "getOffersOnCard"));

		//Share offer Actions
		add(new ActionMapping("getSharedOffer.do", OfferAction.class, "getSharedOffer"));
		add(new ActionMapping("shareOffer.do", OfferAction.class, "shareOffer"));

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
		add(new ActionMapping("verifyEmail.do", LoginAction.class, "verifyEmail"));
		add(new ActionMapping("gotPassword.do", LoginAction.class, "gotPassword"));

		add(new ActionMapping("loginDone.do", LoginAction.class, "loginDone"));
		add(new ActionMapping("signupDone.do", LoginAction.class, "signUpDone"));
		add(new ActionMapping("emailLogin.do", LoginAction.class, "emailLogin"));
		add(new ActionMapping("emailSignup.do", LoginAction.class, "emailSignUp"));
		add(new ActionMapping("hearMore.do", LoginAction.class, "newInterestedUserSuscription"));

		//vendor actions
		add(new ActionMapping("addVendor.do", VendorAction.class, "addVendor"));
		add(new ActionMapping("searchVendor.do", VendorAction.class, "searchVendors"));

		//offer card actions
		add(new ActionMapping("addNewCard.do", OfferCardAction.class, "addOfferCard"));
		add(new ActionMapping("getMyCards.do", OfferCardAction.class, "getMyCards"));
		add(new ActionMapping("addCardToWallet.do", OfferCardAction.class, "addCardToWallet"));
		add(new ActionMapping("getCardByKey.do", OfferCardAction.class, "getCardByKey"));
		add(new ActionMapping("searchOfferCards.do", OfferCardAction.class, "searchOfferCards"));
		add(new ActionMapping("cards.do", OfferCardAction.class, "goToMyCards"));
		add(new ActionMapping("removeCardFromWallet.do", OfferCardAction.class, "removeCardFromWallet"));


		//Profile actions
		add(new ActionMapping("profile.do", ProfileAction.class, "goToProfile"));
		add(new ActionMapping("getUserInfo.do", ProfileAction.class, "getUserInfo"));
		add(new ActionMapping("updateUserInfo.do", ProfileAction.class, "updateUserInfo"));
		add(new ActionMapping("changePassword.do", ProfileAction.class, "changePassword"));
		add(new ActionMapping("updateUserNotificationConfig.do", ProfileAction.class, "updateUserNotificationConfig"));
		add(new ActionMapping("getUserNotificationConfig.do", ProfileAction.class, "getUserNotificationConfig"));

	}
}
