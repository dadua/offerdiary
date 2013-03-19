package com.itech.web;

import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.portlet.bind.annotation.ActionMapping;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itech.common.exeption.CommonException;
import com.itech.common.exeption.ReturnCodes;
import com.itech.common.web.action.ActionResponseAnnotation;
import com.itech.common.web.action.CommonAction;
import com.itech.common.web.action.CommonBeanResponse;
import com.itech.common.web.action.Forward;
import com.itech.common.web.action.Redirect;
import com.itech.common.web.action.Response;
import com.itech.common.web.action.Result;
import com.itech.fb.model.FbCreds;
import com.itech.fb.services.FbService;
import com.itech.user.model.LoginType;
import com.itech.user.model.User;
import com.itech.user.vos.UserEmailCredsVO;

@Controller
public class LoginAction extends CommonAction{

	private static final String SIGN_UP_FAILED_JSP = "user/pages/signUpFailed.jsp";
	private static final String PAGE_ATTR_KEY = "pageAttrKey";
	public static final String FORGOT_PASSWORD_EXECUTION_STATUS = "forgotPasswordStatus";



	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="authorizationFailureJsonResponse.do")
	public Response authorizationFailureJsonResponse(HttpServletRequest req, HttpServletResponse resp) {
		Result<String> result = new Result<String>("User is not logged in.");
		result.setReturnCode(ReturnCodes.AUTHENTICATION_FAILURE);
		Type resultType = new TypeToken<Result<String>>() {
		}.getType();
		return new CommonBeanResponse(result, resultType);
	}


	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="login.do")
	public Response login(HttpServletRequest req, HttpServletResponse resp) {
		req.setAttribute(PAGE_ATTR_KEY, "login");
		return new Forward("user/pages/login.jsp");
	}

	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="signup.do")
	public Response signup(HttpServletRequest req, HttpServletResponse resp) {

		req.setAttribute(PAGE_ATTR_KEY, "signup");
		return new Forward("user/pages/signup.jsp");
	}


	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="getPassword.do")
	public Response getPassword(HttpServletRequest req, HttpServletResponse resp) {
		req.setAttribute(FORGOT_PASSWORD_EXECUTION_STATUS, Boolean.FALSE.toString());
		return new Forward("user/pages/forgotPassword.jsp");
	}

	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="gotPassword.do")
	public Response gotPassword(HttpServletRequest req, HttpServletResponse resp){
		User user = getLoggedInUser();
		if (user == null) {
			String email = req.getParameter("email");
			user = getUserManager().getByEmail(email);
			if(null != user){
				getUserManager().notifyPassword(user);
			}else{
				throw new CommonException(ReturnCodes.OBJECT_DOES_NOT_EXIST_ANYMORE);
			}
		}
		req.setAttribute(FORGOT_PASSWORD_EXECUTION_STATUS, Boolean.TRUE.toString());
		return new Forward("user/pages/forgotPassword.jsp");
	}


	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="verifyEmail.do")
	public Response verifyEmail(HttpServletRequest req, HttpServletResponse resp){
		User user = getLoggedInUser();
		if (user == null) {
			String emailPasswordCredsVOJson = req.getParameter("credVoKey");
			Gson gson = new Gson();
			UserEmailCredsVO userEmailCredsVO = gson.fromJson(emailPasswordCredsVOJson, UserEmailCredsVO.class);
			user = getUserManager().getByUserId(userEmailCredsVO.getEmail());
			if ((user == null) || ! user.getLoginType().toString().equalsIgnoreCase(LoginType.INTERNAL.toString())
					|| ! user.getLoginType().toString().equalsIgnoreCase(LoginType.MULTI.toString())) {
				return emailDoesntExist();
			}
		}
		Result<User> result = new Result<User>( user);
		Type userResultType = new TypeToken<Result<User>>() {
		}.getType();
		return new CommonBeanResponse(result, userResultType);
	}


	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="loginDone.do")
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
		Result<User> result = new Result<User>( user);
		Type userResultType = new TypeToken<Result<User>>() {
		}.getType();
		return new CommonBeanResponse(result, userResultType);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="emailLogin.do")
	public Response emailLogin(HttpServletRequest req, HttpServletResponse resp) {
		User user = getLoggedInUser();
		if (user == null) {
			String emailPasswordCredsVOJson = req.getParameter("credVoKey");
			Gson gson = new Gson();
			UserEmailCredsVO userEmailCredsVO = gson.fromJson(emailPasswordCredsVOJson, UserEmailCredsVO.class);
			user = getUserManager().getByUserId(userEmailCredsVO.getEmail());

			if (user == null ) {
				return invalidPasswordResponse();
			}

			if((userEmailCredsVO.getPassword() !=null) && userEmailCredsVO.getPassword().equals(user.getPassword())){
				updateLoggedInUser(req, user);
			} else {
				return invalidPasswordResponse();
			}
		}
		Result<User> result = new Result<User>( user);
		Type userResultType = new TypeToken<Result<User>>() {
		}.getType();

		return new CommonBeanResponse(result, userResultType);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="emailDoesntExist.do")
	private Response emailDoesntExist() {
		Result<String> result = new Result<String>(ReturnCodes.INTERNAL_ERROR, LoginConstants.INVALID_CREDS);;
		Type stringResultType = new TypeToken<Result<String>>() {
		}.getType();
		return new CommonBeanResponse(result, stringResultType);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="invalidPasswordResponse.do")
	private Response invalidPasswordResponse() {
		Result<String> result = new Result<String>(ReturnCodes.INTERNAL_ERROR, LoginConstants.INVALID_CREDS);;
		Type stringResultType = new TypeToken<Result<String>>() {
		}.getType();
		return new CommonBeanResponse(result, stringResultType);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="signupDone.do")
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
		Result<User> result = new Result<User>( user);
		Type userResultType = new TypeToken<Result<User>>() {
		}.getType();
		return new CommonBeanResponse(result, userResultType);
	}

	@ActionResponseAnnotation(responseType=Redirect.class)
	@ActionMapping(value="emailSignup.do")
	public Response emailSignUp (HttpServletRequest req, HttpServletResponse resp) {
		User user = getLoggedInUser();
		if (user == null) {
			String name = req.getParameter("name");
			String email = req.getParameter("email");
			String password = req.getParameter("password");
			user = getUserManager().getByEmail(email);
			if((user != null) && (user.getLoginType().toString().equalsIgnoreCase(LoginType.INTERNAL.toString())
					|| user.getLoginType().toString().equalsIgnoreCase(LoginType.MULTI.toString()))){
				return new Forward(SIGN_UP_FAILED_JSP);
			}

			if((user != null) && user.getLoginType().toString().equalsIgnoreCase(LoginType.FACEBOOK.toString())){
				user.setLoginType(LoginType.MULTI);
				user.setPassword(password);
				getUserManager().save(user);
				updateLoggedInUser(req, user);
				return new Redirect("wallet.do");
			}

			if(null != user){
				return new Forward(SIGN_UP_FAILED_JSP);
			}

			user = getUserManager().saveEmailUser(name, email, password);
			updateLoggedInUser(req, user);
		}
		return new Redirect("wallet.do");
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="hearMore.do")
	public Response newInterestedUserSuscription(HttpServletRequest req, HttpServletResponse resp){
		String email = req.getParameter("email");
		getUserManager().saveInterestedUserForSubscription(email);
		Result<String> result = new Result<String>();
		Type stringResultType = new TypeToken<Result<String>>() {
		}.getType();
		return new CommonBeanResponse(result, stringResultType);
	}

	@ActionResponseAnnotation(responseType=Redirect.class)
	@ActionMapping(value="logout.do")
	public Response logout (HttpServletRequest req, HttpServletResponse resp) {
		updateLoggedInUser(req, null);
		return new Redirect("");
	}
}
