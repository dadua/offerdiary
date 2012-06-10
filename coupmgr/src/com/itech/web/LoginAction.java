package com.itech.web;

import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itech.common.web.action.CommonAction;
import com.itech.common.web.action.CommonBeanResponse;
import com.itech.common.web.action.Forward;
import com.itech.common.web.action.Redirect;
import com.itech.common.web.action.Response;
import com.itech.common.web.action.Result;
import com.itech.coupon.model.User;
import com.itech.fb.model.FbCreds;
import com.itech.fb.services.FbService;

public class LoginAction extends CommonAction{


	public Response login(HttpServletRequest req, HttpServletResponse resp) {
		return new Forward("login.jsp");
	}

	public Response signup(HttpServletRequest req, HttpServletResponse resp) {
		return new Forward("signup.jsp");
	}

	public Response loginDone (HttpServletRequest req, HttpServletResponse resp) {
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

	public Response emailLogin(HttpServletRequest req, HttpServletResponse resp) {
		User user = getLoggedInUser();
		if (user == null) {
			String email = req.getParameter("email");
			String password = req.getParameter("password");
			user = getUserManager().getByUserId(email);

			if(password.equals(user.getPassword())){
				updateLoggedInUser(req, user);
			}
		}
		/*
		Result<User> result = new Result<User>(true, user);
		Type userResultType = new TypeToken<Result<User>>() {
		}.getType();
		return new CommonBeanResponse(result, userResultType);
		 */
		return new Forward("wallet.do");
	}

	public Response signUpDone (HttpServletRequest req, HttpServletResponse resp) {
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

	public Response emailSignUp (HttpServletRequest req, HttpServletResponse resp) {
		User user = getLoggedInUser();
		if (user == null) {
			String email = req.getParameter("email");
			String password = req.getParameter("password");
			user = getUserManager().saveEmailUser(email, password);
			updateLoggedInUser(req, user);
		}
		/*
		Result<User> result = new Result<User>(true, user);
		Type userResultType = new TypeToken<Result<User>>() {
		}.getType();
		return new CommonBeanResponse(result, userResultType);
		 */
		return new Forward("wallet.do");
	}


	public Response logout (HttpServletRequest req, HttpServletResponse resp) {
		updateLoggedInUser(req, null);
		return new Redirect("");
	}

}
