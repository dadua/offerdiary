package com.itech.web;

import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.portlet.bind.annotation.ActionMapping;

import com.google.gson.reflect.TypeToken;
import com.itech.common.exeption.ReturnCodes;
import com.itech.common.web.action.ActionResponseAnnotation;
import com.itech.common.web.action.CommonAction;
import com.itech.common.web.action.CommonBeanResponse;
import com.itech.common.web.action.Forward;
import com.itech.common.web.action.Redirect;
import com.itech.common.web.action.Response;
import com.itech.common.web.action.Result;
import com.itech.user.model.LoginType;
import com.itech.user.model.User;

@Controller
public class LoginAction extends CommonAction{

	private static final String PAGE_ATTR_KEY = "pageAttrKey";
	public static final String FORGOT_PASSWORD_EXECUTION_STATUS = "forgotPasswordStatus";

	private static final String SIGN_UP_FAILED_JSP = "user/pages/signUpFailed.jsp";


	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="authorizationFailureJsonResponse.do")
	public Response authorizationFailureJsonResponse(HttpServletRequest req, HttpServletResponse resp) {
		Result<String> result = new Result<String>("User is not logged in.");
		if (req.getAttribute(LoginFilter.IS_PUBLIC_PAGE_SOURCE_ATTR_KEY)!=null) {
			result.setReturnCode(ReturnCodes.AUTHENTICATION_FAILURE_FROM_PUBLIC_PAGE);
		} else {
			result.setReturnCode(ReturnCodes.AUTHENTICATION_FAILURE);
		}
		Type resultType = new TypeToken<Result<String>>() {
		}.getType();
		return new CommonBeanResponse(result, resultType);
	}


	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="login.do")
	public Response login(HttpServletRequest req, HttpServletResponse resp) {
		req.setAttribute(PAGE_ATTR_KEY, "login");
		return new Forward("login.jsp");
	}


	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="authenticate.do")
	public Response authenticate(HttpServletRequest req, HttpServletResponse resp) {
		String userName = req.getParameter("username");
		String password = req.getParameter("password");
		User user = getUserManager().getByUserId(userName);

		if (user == null ) {
			return invalidPasswordResponse();
		}

		if((password !=null) && password.equals(user.getPassword())){
			updateLoggedInUser(req, user);
		} else {
			return invalidPasswordResponse();
		}

		Result<User> result = new Result<User>( user);
		Type userResultType = new TypeToken<Result<User>>() {
		}.getType();

		return new CommonBeanResponse(result, userResultType);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="invalidPasswordResponse.do")
	private Response invalidPasswordResponse() {
		Result<String> result = new Result<String>(ReturnCodes.INTERNAL_ERROR, LoginConstants.INVALID_CREDS);;
		Type stringResultType = new TypeToken<Result<String>>() {
		}.getType();
		return new CommonBeanResponse(result, stringResultType);
	}


	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="getPassword.do")
	public Response getPassword(HttpServletRequest req, HttpServletResponse resp) {
		req.setAttribute(FORGOT_PASSWORD_EXECUTION_STATUS, Boolean.FALSE.toString());
		return new Forward("user/pages/forgotPassword.jsp");
	}



	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="loginDone.do")
	public Response loginDone (HttpServletRequest req, HttpServletResponse resp) {
		User user = getLoggedInUser();
		if (user == null) {
			updateLoggedInUser(req, user);
		}
		Result<User> result = new Result<User>( user);
		Type userResultType = new TypeToken<Result<User>>() {
		}.getType();
		return new CommonBeanResponse(result, userResultType);
	}


	@ActionResponseAnnotation(responseType=Redirect.class)
	@ActionMapping(value="logout.do")
	public Response logout (HttpServletRequest req, HttpServletResponse resp) {
		updateLoggedInUser(req, null);
		return new Redirect("");
	}


	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="register.do")
	public Response register(HttpServletRequest req, HttpServletResponse resp) {

		req.setAttribute(PAGE_ATTR_KEY, "signup");
		if (getLoggedInUser()!=null) {
			return new Redirect("alumni.do");
		}
		return new Forward("user/pages/signup.jsp");
	}

	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="signup.do")
	public Response signup(HttpServletRequest req, HttpServletResponse resp) {

		req.setAttribute(PAGE_ATTR_KEY, "signup");
		if (getLoggedInUser()!=null) {
			return new Redirect("alumni.do");
		}
		return new Forward("user/pages/signup.jsp");
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
				return new Redirect("diary.do");
			}

			if(null != user){
				return new Forward(SIGN_UP_FAILED_JSP);
			}

			user = getUserManager().saveEmailUser(name, email, password);
			updateLoggedInUser(req, user);
		}
		return new Redirect("diary.do");
	}
}
