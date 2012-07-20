package com.itech.coupon.web;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

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
import com.itech.offer.model.OfferShare;
import com.itech.offer.vo.OfferNotifyVO;

public class OfferAction extends CommonAction{

	private static final String MY_OFFERS_JSON_ATTR_KEY = "myOffersJson";

	public Response goToHome(HttpServletRequest req, HttpServletResponse resp) {
		return new Forward(OfferWalletConstants.INDEX_PAGE);
	}

	public Response goToMyWallet(HttpServletRequest req, HttpServletResponse resp) {
		User loggedInUser = getLoggedInUser();
		List<Offer> offers = new ArrayList<Offer>();
		if (loggedInUser != null) {
			offers = getOfferManager().getAllOffersForUser(loggedInUser);
		}
		Gson gson = new Gson();
		Type listOfOffersType = new TypeToken<List<Offer>>() {
		}.getType();
		String offersJson = gson.toJson(offers, listOfOffersType);
		req.setAttribute(MY_OFFERS_JSON_ATTR_KEY, StringEscapeUtils.escapeJavaScript(offersJson));
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

	public Response saveOffers (HttpServletRequest req, HttpServletResponse resp) {
		String offerNotificationConfigsJson = req.getParameter(OfferWalletConstants.OFFER_NOTIFICATION_CONFIG_PARAM_KEY);
		Gson gson = new Gson();
		Type offerNotifyVOsType = new TypeToken<List<OfferNotifyVO>>() { }.getType();
		List<OfferNotifyVO> offerNotificationVOs = gson.fromJson(offerNotificationConfigsJson, offerNotifyVOsType);
		List<Offer> offersProcessed = getOfferManager().addOffersEventsConfigForUser(offerNotificationVOs, getLoggedInUser());
		Result<List<Offer>> result = new Result<List<Offer>>(true, offersProcessed, "Successfully Added the offers");
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
		Result<String> result = new Result<String>("Successfully Deleted the offers");
		Type resultStringType = new TypeToken<Result<String>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

	public Response getSharedOffer (HttpServletRequest req, HttpServletResponse resp) {
		String accessToken = req.getParameter(OfferWalletConstants.SHARED_OFFER_ACCESS_CODE_PARAM_KEY);
		OfferShare offerShare = null;
		if (accessToken != null) {
			offerShare = getOfferManager().getOfferShareFor(accessToken);
		}
		req.setAttribute(OfferWalletConstants.SHARED_OFFER_ATTR_KEY, offerShare.getOffer());
		return new Forward(OfferWalletConstants.GET_SHARED_OFFER_PAGE);

	}

	public Response shareOffer(HttpServletRequest req, HttpServletResponse resp) {
		String offerId = req.getParameter(OfferWalletConstants.SHARED_OFFER_ID_ATTR_KEY);
		OfferShare offerShare = null;
		if (offerId != null) {
			Offer offer = getOfferManager().getOfferFor(Long.parseLong(offerId));
			offerShare = getOfferManager().createOfferShare(offer);
		}
		String shareOfferUrl = "/getSharedOffer.do?access-token=" + offerShare.getAccessToken();
		Result<String> result = new Result<String>(shareOfferUrl);
		Type resultStringType = new TypeToken<Result<String>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);

	}

}
