package com.itech.web;

import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itech.common.web.action.CommonAction;
import com.itech.common.web.action.CommonBeanResponse;
import com.itech.common.web.action.Redirect;
import com.itech.common.web.action.Response;
import com.itech.common.web.action.Result;
import com.itech.coupon.model.User;
import com.itech.fb.model.FbCreds;
import com.itech.fb.services.FbService;

public class LoginAction extends CommonAction{


	public Response doLogin (HttpServletRequest req, HttpServletResponse resp) {
		User user = getLoggedInUser();
		if (user == null) {
			String sessionJson = req.getParameter("fbData");
			Gson gson = new Gson();
			FbCreds fbCreds = gson.fromJson(sessionJson, FbCreds.class);
			FbService fbService = new FbService(fbCreds);
			user = getUserManager().saveFbUser(fbService);
			updateLoggedInUser(req, user);
		}
		Result<User> result = new Result<User>(true, user);
		Type userResultType = new TypeToken<Result<User>>() {
		}.getType();
		return new CommonBeanResponse(result, userResultType);
	}


	public Response doSignup (HttpServletRequest req, HttpServletResponse resp) {
		User user = getLoggedInUser();
		if (user == null) {
			String sessionJson = req.getParameter("fbData");
			Gson gson = new Gson();
			FbCreds fbCreds = gson.fromJson(sessionJson, FbCreds.class);
			FbService fbService = new FbService(fbCreds);
			user = getUserManager().saveFbUser(fbService);
			updateLoggedInUser(req, user);
		}
		Result<User> result = new Result<User>(true, user);
		Type userResultType = new TypeToken<Result<User>>() {
		}.getType();
		return new CommonBeanResponse(result, userResultType);
	}


	public Response doLogout (HttpServletRequest req, HttpServletResponse resp) {
		updateLoggedInUser(req, null);
		return new Redirect("");
	}

}
