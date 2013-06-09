package com.itech.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.portlet.bind.annotation.ActionMapping;

import com.itech.common.web.action.ActionResponseAnnotation;
import com.itech.common.web.action.CommonAction;
import com.itech.common.web.action.Forward;
import com.itech.common.web.action.Response;


@Controller
public class HomeAction extends CommonAction{


	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="privacy.do")
	public Response goToPrivacy(HttpServletRequest req, HttpServletResponse resp) {
		return new Forward("privacy.jsp");
	}


	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="tnc.do")
	public Response goToTermsAndConditions(HttpServletRequest req, HttpServletResponse resp) {
		return new Forward("termsAndConditions.jsp");
	}

	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="aboutUs.do")
	public Response goToAboutUs(HttpServletRequest req, HttpServletResponse resp) {
		return new Forward("aboutUs.jsp");
	}

	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="home.do")
	public Response goToHome(HttpServletRequest req, HttpServletResponse resp) {
		return new Forward(UrlConstants.FLOWERS_JSP_RELATIVE_URL);
	}

}
