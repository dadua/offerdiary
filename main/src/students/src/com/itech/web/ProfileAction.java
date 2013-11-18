package com.itech.web;

import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.portlet.bind.annotation.ActionMapping;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itech.common.web.action.ActionResponseAnnotation;
import com.itech.common.web.action.CommonAction;
import com.itech.common.web.action.CommonBeanResponse;
import com.itech.common.web.action.Forward;
import com.itech.common.web.action.Response;
import com.itech.common.web.action.Result;
import com.itech.user.model.User;
import com.itech.user.vos.UserInfoVO;

@Controller
public class ProfileAction extends CommonAction {

	private static final String USER_INFO_PARAM_KEY = "userInfo";
	private static final String OPTION_PARAM_KEY = "o";
	private static final String OPTION_ATTR_KEY = "option";
	private static final String IS_LINKED_USER_PARAM_KEY = "isLinkedUser";
	private static final String NEW_PASSWORD_PARAM_KEY = "newPassword";
	private static final String CURRENT_PASSWORD_PARAM_KEY = "currentPassword";


	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="profile.do")
	public Response goToProfile(HttpServletRequest req, HttpServletResponse resp) {
		User user = getUserManager().getByUserId(getLoggedInUser().getUserId());
		UserInfoVO userInfoVO = UserInfoVO.getUserInfoVOFor(user);
		req.setAttribute(IS_LINKED_USER_PARAM_KEY, userInfoVO.isLinkedUser());
		req.setAttribute(USER_INFO_PARAM_KEY, userInfoVO);
		req.setAttribute(OPTION_ATTR_KEY, req.getParameter(OPTION_PARAM_KEY));
		return new Forward("profile/pages/profile.jsp");
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getProfiles.do")
	public Response getProfiles(HttpServletRequest req, HttpServletResponse resp) {
		List<User> users = getUserManager().getAllUsers();
		List<UserInfoVO> userInfoVOs = UserInfoVO.getUserInfoVOs(users);
		Result<List<UserInfoVO>> result = new Result<List<UserInfoVO>>(userInfoVOs);
		Type type = new TypeToken<Result<List<UserInfoVO>>>() { }.getType();
		return new CommonBeanResponse(result, type);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getUserInfo.do")
	public Response getUserInfo(HttpServletRequest req, HttpServletResponse resp) {
		User user = getUserManager().getByUserId(getLoggedInUser().getUserId());
		UserInfoVO userInfoVO = UserInfoVO.getUserInfoVOFor(user);
		Result<UserInfoVO> result = new Result<UserInfoVO>(userInfoVO);
		Type type = new TypeToken<Result<UserInfoVO>>() { }.getType();
		return new CommonBeanResponse(result, type);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="updateUserInfo.do")
	public Response updateUserInfo(HttpServletRequest req, HttpServletResponse resp) {
		String userInfoJson = req.getParameter(USER_INFO_PARAM_KEY);
		Gson gson = new Gson();
		Type userInfoType = new TypeToken<UserInfoVO>() { }.getType();
		UserInfoVO userInfoVO = gson.fromJson(userInfoJson, userInfoType);
		User user = getUserManager().getByUserId(getLoggedInUser().getUserId());
		UserInfoVO.fillUserFromUserVO(user, userInfoVO);
		getUserManager().save(user);
		Result<String> result = new Result<String>();
		result.setMsg("Successfully updated user information.");
		Type type = new TypeToken<Result<String>>() { }.getType();
		return new CommonBeanResponse(result, type);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="changePassword.do")
	public Response changePassword(HttpServletRequest req, HttpServletResponse resp) {
		String currentPassword = req.getParameter(CURRENT_PASSWORD_PARAM_KEY);
		String newPassword = req.getParameter(NEW_PASSWORD_PARAM_KEY);
		getUserManager().changePassword(getLoggedInUser().getUserId(), currentPassword, newPassword);
		Result<String> result = new Result<String>("Successfully updated new password.");
		result.setMsg("Successfully updated new password.");
		Type type = new TypeToken<Result<String>>() { }.getType();
		return new CommonBeanResponse(result, type);
	}

	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="alumni.do")
	public Response goToAlumni(HttpServletRequest req, HttpServletResponse resp) {

		List<User> allUsers = getUserManager().getAllUsers();
		req.setAttribute("users", allUsers);
		return new Forward("profile/pages/profiles.jsp");
	}
}
