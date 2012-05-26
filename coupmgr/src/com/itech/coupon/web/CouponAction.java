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
import com.itech.coupon.CouponConstants;
import com.itech.coupon.model.User;
import com.itech.offer.model.Offer;
import com.itech.offer.vo.OfferNotifyVO;

public class CouponAction extends CommonAction{

	private static final String MY_COUPONS_ATTR_KEY = "myCoupons";

	public Response goToHome(HttpServletRequest req, HttpServletResponse resp) {
		return new Forward(CouponConstants.INDEX_PAGE);

	}

	public Response goToMyWallet(HttpServletRequest req, HttpServletResponse resp) {
		User loggedInUser = getLoggedInUser();
		List<Offer> coupons = new ArrayList<Offer>();
		if (loggedInUser != null) {
			coupons = getOfferManager().getAllOffersForUser(loggedInUser);
		}
		req.setAttribute(MY_COUPONS_ATTR_KEY, coupons);
		return new Forward(CouponConstants.WALLET_PAGE);
	}

	public Response getMyCoupons (HttpServletRequest req, HttpServletResponse resp) {
		User loggedInUser = getLoggedInUser();
		List<Offer> coupons = new ArrayList<Offer>();
		if (loggedInUser != null) {
			coupons = getOfferManager().getAllOffersForUser(loggedInUser);
		} else {
			coupons = new ArrayList<Offer>();
		}
		Result<List<Offer>> result = new Result<List<Offer>>(true, coupons);
		Type type = new TypeToken<Result<List<Offer>>>() { }.getType();
		return new CommonBeanResponse(result, type);
	}

	public Response saveCoupons (HttpServletRequest req, HttpServletResponse resp) {
		String couponNotificationConfigsJson = req.getParameter(CouponConstants.COUPON_NOTIFICATION_CONFIG_PARAM_KEY);
		Gson gson = new Gson();
		Type offerNotifyVOsType = new TypeToken<List<OfferNotifyVO>>() { }.getType();
		List<OfferNotifyVO> offerNotificationVOs = gson.fromJson(couponNotificationConfigsJson, offerNotifyVOsType);
		List<Offer> offersProcessed = getOfferManager().addOffersEventsConfigForUser(offerNotificationVOs, getLoggedInUser());
		Result<List<Offer>> result = new Result<List<Offer>>(true, offersProcessed, "Successfully Added the coupons");
		Type resultOffersType = new TypeToken<Result<List<Offer>>>() {
		}.getType();
		return new CommonBeanResponse(result, resultOffersType);
	}

	public Response deleteCoupons (HttpServletRequest req, HttpServletResponse resp) {
		String couponsJson = req.getParameter(CouponConstants.COUPON_IDS_PARAM_KEY);
		Gson gson = new Gson();
		Type type = new TypeToken<List<String>>() { }.getType();
		List<String> couponIdStrings = gson.fromJson(couponsJson, type);
		List<Long> couponIds = new ArrayList<Long>();
		for (String couponIdString : couponIdStrings) {
			couponIds.add(Long.parseLong(couponIdString));
		}
		getOfferManager().deleteByIds(couponIds);
		Result<String> result = new Result<String>("Successfully Deleted the coupons");
		Type resultStringType = new TypeToken<Result<String>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

}
