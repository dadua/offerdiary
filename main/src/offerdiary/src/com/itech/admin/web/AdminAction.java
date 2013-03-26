package com.itech.admin.web;

import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.portlet.bind.annotation.ActionMapping;

import com.google.gson.reflect.TypeToken;
import com.itech.common.security.Authorization;
import com.itech.common.web.action.ActionResponseAnnotation;
import com.itech.common.web.action.CommonAction;
import com.itech.common.web.action.CommonBeanResponse;
import com.itech.common.web.action.Response;
import com.itech.common.web.action.Result;
import com.itech.user.model.UserRole;

@Authorization(userRole=UserRole.OD_ADMIN)
@Controller
public class AdminAction extends CommonAction {

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="testAdminAuth.do")
	public Response removeOffersFromWallet (HttpServletRequest req, HttpServletResponse resp) {
		Result<String> result = new Result<String>("Wow you are real OD Admin");
		Type resultStringType = new TypeToken<Result<String>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}
}
