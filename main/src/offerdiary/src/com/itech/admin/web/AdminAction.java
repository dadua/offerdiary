package com.itech.admin.web;

import java.lang.reflect.Type;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.portlet.bind.annotation.ActionMapping;

import com.google.gson.reflect.TypeToken;
import com.itech.common.security.Authorization;
import com.itech.common.services.ServiceLocator;
import com.itech.common.web.action.ActionResponseAnnotation;
import com.itech.common.web.action.CommonAction;
import com.itech.common.web.action.CommonBeanResponse;
import com.itech.common.web.action.Response;
import com.itech.common.web.action.Result;
import com.itech.offer.job.ItechBackGroundTasksService;
import com.itech.user.model.UserRole;
import com.itech.web.ActionMappings;

@Authorization(userRole=UserRole.OD_ADMIN)
@Controller
public class AdminAction extends CommonAction {


	private ItechBackGroundTasksService backGroundTasksService;

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="testAdminAuth.do")
	public Response removeOffersFromWallet (HttpServletRequest req, HttpServletResponse resp) {
		Result<String> result = new Result<String>("Wow you are real OD Admin");
		Type resultStringType = new TypeToken<Result<String>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getAllActions.do")
	public Response getAllActions (HttpServletRequest req, HttpServletResponse resp) {
		Set<String> actions = ActionMappings.getActions();
		Result<Set<String>> result = new Result<Set<String>>(actions);
		Type resultStringType = new TypeToken<Result<Set<String>>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

	/**
	 * Task names : redWIneSync, vendorSync, hdfcOfferSync
	 * @param req
	 * @param resp
	 * @return
	 */
	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="manageBackGroundTask.do")
	public Response manageBackGroundTask (HttpServletRequest req, HttpServletResponse resp) {
		String taskName = req.getParameter("taskName");
		String actionTaken = req.getParameter("action");
		getBackGroundTasksService().manageBackgroundTask(taskName, actionTaken);
		Result<String> result = new Result<String>("Action: " + actionTaken  + " taken on Task: " + taskName);
		Type resultStringType = new TypeToken<Result<String>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getTaskStatus.do")
	public Response getTaskStatus (HttpServletRequest req, HttpServletResponse resp) {
		String taskName = req.getParameter("taskName");
		String taskStatus = getBackGroundTasksService().getTaskStatus(taskName);
		Result<String> result = new Result<String>(taskStatus);
		Type resultStringType = new TypeToken<Result<String>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

	public ItechBackGroundTasksService getBackGroundTasksService() {
		if (backGroundTasksService == null) {
			backGroundTasksService = ServiceLocator.getInstance().getBean(ItechBackGroundTasksService.class);
		}
		return backGroundTasksService;
	}

	public void setBackGroundTasksService(ItechBackGroundTasksService backGroundTasksService) {
		this.backGroundTasksService = backGroundTasksService;
	}
}
