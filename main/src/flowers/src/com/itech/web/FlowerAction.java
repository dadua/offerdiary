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
public class FlowerAction extends CommonAction {



	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value=UrlConstants.FLOWERS_DO_RELATIVE_URL)
	public Response goToFlowers(HttpServletRequest req, HttpServletResponse resp) {
		return new Forward(UrlConstants.HOME_RELATIVE_URL);
	}

}
