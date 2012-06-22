package com.itech.coupon.web;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itech.common.web.action.CommonAction;
import com.itech.common.web.action.CommonBeanResponse;
import com.itech.common.web.action.Forward;
import com.itech.common.web.action.Response;
import com.itech.common.web.action.Result;
import com.itech.coupon.OfferWalletConstants;
import com.itech.coupon.model.User;
import com.itech.offer.model.Offer;
import com.itech.offer.vo.OfferNotifyVO;

public class OfferAction extends CommonAction{

	private static final String MY_OFFERS_ATTR_KEY = "myCoupons";

	public Response goToHome(HttpServletRequest req, HttpServletResponse resp) {
		return new Forward(OfferWalletConstants.INDEX_PAGE);

	}

	public Response goToMyWallet(HttpServletRequest req, HttpServletResponse resp) {
		User loggedInUser = getLoggedInUser();
		List<Offer> offers = new ArrayList<Offer>();
		if (loggedInUser != null) {
			offers = getOfferManager().getAllOffersForUser(loggedInUser);
		}
		req.setAttribute(MY_OFFERS_ATTR_KEY, offers);
		return new Forward(OfferWalletConstants.WALLET_PAGE);
	}

	public Response getMyOffers (HttpServletRequest req, HttpServletResponse resp) {
		User loggedInUser = getLoggedInUser();
		List<Offer> offers = new ArrayList<Offer>();
		if (loggedInUser != null) {
			offers = getOfferManager().getAllOffersForUser(loggedInUser);
		} else {
			offers = new ArrayList<Offer>();
		}
		Result<List<Offer>> result = new Result<List<Offer>>(true, offers);
		Type type = new TypeToken<Result<List<Offer>>>() { }.getType();
		return new CommonBeanResponse(result, type);
	}

	public Response saveCoupons (HttpServletRequest req, HttpServletResponse resp) {
		String offerNotificationConfigsJson = req.getParameter(OfferWalletConstants.OFFER_NOTIFICATION_CONFIG_PARAM_KEY);
		Gson gson = new Gson();
		Type offerNotifyVOsType = new TypeToken<List<OfferNotifyVO>>() { }.getType();
		List<OfferNotifyVO> offerNotificationVOs = gson.fromJson(offerNotificationConfigsJson, offerNotifyVOsType);
		List<Offer> offersProcessed = getOfferManager().addOffersEventsConfigForUser(offerNotificationVOs, getLoggedInUser());
		Result<List<Offer>> result = new Result<List<Offer>>(true, offersProcessed, "Successfully Added the coupons");
		Type resultOffersType = new TypeToken<Result<List<Offer>>>() {
		}.getType();
		return new CommonBeanResponse(result, resultOffersType);
	}

	public Response deleteOffers (HttpServletRequest req, HttpServletResponse resp) {
		String offersJson = req.getParameter(OfferWalletConstants.OFFER_IDS_PARAM_KEY);
		Gson gson = new Gson();
		Type type = new TypeToken<List<String>>() { }.getType();
		List<String> offerIdStrings = gson.fromJson(offersJson, type);
		List<Long> offerIds = new ArrayList<Long>();
		for (String offerIdString : offerIdStrings) {
			offerIds.add(Long.parseLong(offerIdString));
		}
		getOfferManager().deleteByIds(offerIds);
		Result<String> result = new Result<String>("Successfully Deleted the coupons");
		Type resultStringType = new TypeToken<Result<String>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

}
