package com.itech.user.web;

import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itech.common.web.action.ActionResponseAnnotation;
import com.itech.common.web.action.CommonAction;
import com.itech.common.web.action.CommonBeanResponse;
import com.itech.common.web.action.Forward;
import com.itech.common.web.action.Response;
import com.itech.common.web.action.Result;
import com.itech.offer.OfferWalletConstants;
import com.itech.user.model.User;
import com.itech.user.vos.UserInfoVO;

public class ProfileAction extends CommonAction {


	private static final String NEW_PASSWORD_PARAM_KEY = "newPassword";
	private static final String CURRENT_PASSWORD_PARAM_KEY = "currentPassword";


	@ActionResponseAnnotation(responseType=Forward.class)
	public Response goToProfile(HttpServletRequest req, HttpServletResponse resp) {
		return new Forward("profile/pages/profile.jsp");
	}


	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	public Response getUserInfo(HttpServletRequest req, HttpServletResponse resp) {
		User user = getUserManager().getByUserId(getLoggedInUser().getUserId());
		UserInfoVO userInfoVO = UserInfoVO.getUserInfoVOFor(user);
		Result<UserInfoVO> result = new Result<UserInfoVO>(userInfoVO);
		Type type = new TypeToken<Result<UserInfoVO>>() { }.getType();
		return new CommonBeanResponse(result, type);
	}

	@ActionResponseAnnotation(responseType=Forward.class)
	public Response updateUserInfo(HttpServletRequest req, HttpServletResponse resp) {
		String userInfoJson = req.getParameter(OfferWalletConstants.USER_INFO_PARAM_KEY);
		Gson gson = new Gson();
		Type userInfoType = new TypeToken<UserInfoVO>() { }.getType();
		UserInfoVO userInfoVO = gson.fromJson(userInfoJson, userInfoType);
		User user = getUserManager().getByUserId(getLoggedInUser().getUserId());
		UserInfoVO.fillUserFromUserVO(user, userInfoVO);
		getUserManager().save(user);
		Result<String> result = new Result<String>("Successfully updated user information.");
		Type type = new TypeToken<Result<String>>() { }.getType();
		return new CommonBeanResponse(result, type);
	}


	@ActionResponseAnnotation(responseType=Forward.class)
	public Response changePassword(HttpServletRequest req, HttpServletResponse resp) {
		String currentPassword = req.getParameter(CURRENT_PASSWORD_PARAM_KEY);
		String newPassword = req.getParameter(NEW_PASSWORD_PARAM_KEY);
		getUserManager().changePassword(getLoggedInUser().getUserId(), currentPassword, newPassword);
		Result<String> result = new Result<String>("Successfully updated new password.");
		Type type = new TypeToken<Result<String>>() { }.getType();
		return new CommonBeanResponse(result, type);
	}
}