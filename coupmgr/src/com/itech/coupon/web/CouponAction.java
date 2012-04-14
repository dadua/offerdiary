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
import com.itech.coupon.manager.CouponManager;
import com.itech.coupon.model.Coupon;
import com.itech.coupon.model.User;

public class CouponAction extends CommonAction{
	private CouponManager couponManager;

	public Response goToMyWallet(HttpServletRequest req, HttpServletResponse resp) {

		return new Forward(CouponConstants.WALLET_PAGE);
	}

	public Response getMyCoupons (HttpServletRequest req, HttpServletResponse resp) {
		User loggedInUser = getLoggedInUser();
		List<Coupon> coupons = null;
		if (loggedInUser != null) {
			coupons = getCouponManager().searchBy(loggedInUser);
		} else {
			coupons = new ArrayList<Coupon>();
		}
		Result<List<Coupon>> result = new Result<List<Coupon>>(true, coupons);
		Type type = new TypeToken<Result<List<Coupon>>>() { }.getType();
		return new CommonBeanResponse(result, type);
	}

	public Response saveCoupons (HttpServletRequest req, HttpServletResponse resp) {
		String couponsJson = req.getParameter(CouponConstants.COUPON_LIST_PARAM_KEY);
		Gson gson = new Gson();
		Type type = new TypeToken<List<Coupon>>() { }.getType();
		List<Coupon> coupons = gson.fromJson(couponsJson, type);
		couponManager.save(coupons);
		Result<String> result = new Result<String>("Successfully Added the coupons");
		return new CommonBeanResponse(result, User.class);
	}

	public Response deleteCoupons (HttpServletRequest req, HttpServletResponse resp) {
		String couponsJson = req.getParameter(CouponConstants.COUPON_LIST_PARAM_KEY);
		Gson gson = new Gson();
		Type type = new TypeToken<List<Coupon>>() { }.getType();
		List<Coupon> coupons = gson.fromJson(couponsJson, type);
		couponManager.delete(coupons);
		Result<String> result = new Result<String>("Successfully Deleted the coupons");
		return new CommonBeanResponse(result, User.class);
	}



	public void setCouponManager(CouponManager couponManager) {
		this.couponManager = couponManager;
	}
	public CouponManager getCouponManager() {
		return couponManager;
	}
}