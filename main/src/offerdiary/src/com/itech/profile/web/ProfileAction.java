package com.itech.profile.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itech.common.web.action.ActionResponseAnnotation;
import com.itech.common.web.action.CommonAction;
import com.itech.common.web.action.Forward;
import com.itech.common.web.action.Response;

public class ProfileAction extends CommonAction {


	@ActionResponseAnnotation(responseType=Forward.class)
	public Response goToProfile(HttpServletRequest req, HttpServletResponse resp) {
		return new Forward("profile/pages/profile.jsp");
	}

}
