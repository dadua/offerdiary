package com.itech.alert.web;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itech.alert.AlertConstants;
import com.itech.alert.model.Alert;
import com.itech.alert.model.Alert.AlertStatus;
import com.itech.common.web.action.ActionResponseAnnotation;
import com.itech.common.web.action.CommonAction;
import com.itech.common.web.action.CommonBeanResponse;
import com.itech.common.web.action.Forward;
import com.itech.common.web.action.Response;
import com.itech.common.web.action.Result;
import com.itech.coupon.model.Coupon;
import com.itech.coupon.model.User;

public class AlertAction extends CommonAction{

	private static final String NEW_ALERT_COUNT_ATTR_KEY = "newAlertCount";
	private static final String MY_ALERTS_ATTR_KEY = "myAlerts";

	@ActionResponseAnnotation(responseType=Forward.class)
	public Response goToMyAlerts(HttpServletRequest req, HttpServletResponse resp) {
		User loggedInUser = getLoggedInUser();
		List<Alert> alerts = new ArrayList<Alert>();
		if (loggedInUser != null) {
			alerts = getAlertManager().getAlertsFor(loggedInUser);
		}
		int newAlertCount = 0;
		for (Alert alert : alerts) {
			if (AlertStatus.NEW.equals(alert.getAlertStatus())) {
				newAlertCount++;
			}
		}
		req.setAttribute(NEW_ALERT_COUNT_ATTR_KEY, newAlertCount);
		req.setAttribute(MY_ALERTS_ATTR_KEY, alerts);
		return new Forward(AlertConstants.MY_ALERT_PAGE);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	public Response getMyAlerts (HttpServletRequest req, HttpServletResponse resp) {
		User loggedInUser = getLoggedInUser();
		List<Alert> alerts = new ArrayList<Alert>();
		if (loggedInUser != null) {
			alerts = getAlertManager().getAlertsFor(loggedInUser);
		}

		Result<List<Alert>> result = new Result<List<Alert>>(alerts);
		Type type = new TypeToken<Result<List<Coupon>>>() { }.getType();
		return new CommonBeanResponse(result, type);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	public Response deleteAlerts (HttpServletRequest req, HttpServletResponse resp) {
		String alertIdsJson = req.getParameter(AlertConstants.ALERT_IDS_PARAM_KEY);
		Gson gson = new Gson();
		Type type = new TypeToken<List<Long>>() { }.getType();
		List<Long> alertIds = gson.fromJson(alertIdsJson, type);
		getAlertManager().deleteByIds(alertIds);
		Result<String> result = new Result<String>("Successfully Deleted the coupons");
		Type resultStringType = new TypeToken<Result<String>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	public Response markAlertRead(HttpServletRequest req, HttpServletResponse resp) {
		User loggedInUser = getLoggedInUser();
		getAlertManager().markAlertsRead(loggedInUser);
		Result<String> result = new Result<String>("Successfully Updated alerts");
		Type resultStringType = new TypeToken<Result<String>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}
}
