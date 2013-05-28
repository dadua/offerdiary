package com.itech.admin.web;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
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
import com.itech.common.web.action.Forward;
import com.itech.common.web.action.Response;
import com.itech.common.web.action.Result;
import com.itech.offer.job.ItechBackGroundTasksService;
import com.itech.user.model.User;
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
	 * Task names : redWineSync, vendorSync, hdfcOfferSync
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

	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="odusers.do")
	public Response goToOdUsers(HttpServletRequest req, HttpServletResponse resp) {

		List<User> allUsers = getUserManager().getAllUsers();
		req.setAttribute("users", allUsers);
		return new Forward("admin/users.jsp");
	}


	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="odadmin.do")
	public Response goToAdminAction(HttpServletRequest req, HttpServletResponse resp) {

		Map<String, String> taskNames = getBackGroundTasksService().getTaskNames();
		List<String> taskActions = getBackGroundTasksService().getTaskActions();
		req.setAttribute("taskNameToDisplayNameMap", taskNames);
		req.setAttribute("taskActions", taskActions);
		return new Forward("admin/adminActions.jsp");
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getTaskNames.do")
	public Response getTaskNames (HttpServletRequest req, HttpServletResponse resp) {
		Map<String, String> taskNames = getBackGroundTasksService().getTaskNames();
		Result<Map<String, String>> result = new Result<Map<String, String>>(taskNames);
		Type resultStringType = new TypeToken<Result<Map<String, String>>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getTaskActions.do")
	public Response getTaskActions (HttpServletRequest req, HttpServletResponse resp) {
		List<String> taskNames = getBackGroundTasksService().getTaskActions();
		Result<List<String>> result = new Result<List<String>>(taskNames);
		Type resultStringType = new TypeToken<Result<List<String>>>() {
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
