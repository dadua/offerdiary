package com.itech.alert.services;

import java.util.List;

import com.itech.alert.dao.AlertDAO;
import com.itech.alert.model.Alert;
import com.itech.user.model.User;

public class AlertManagerImpl implements AlertManager {
	private AlertDAO alertDAO;

	@Override
	public void delete(Alert alert) {
		getAlertDAO().delete(alert);
	}

	@Override
	public void deleteAlertsFor(String dataType, long dataId) {
		getAlertDAO().deleteAlertsFor(dataType, dataId);

	}

	@Override
	public List<Alert> getAlertsFor(User user) {
		return getAlertDAO().getAlertsForUser(user);
	}

	@Override
	public List<Alert> getAlertsFor(String dataType, long dataId) {
		return getAlertDAO().getAlertsForDataType(dataType, dataId);
	}

	@Override
	public void save(Alert alert) {
		getAlertDAO().addOrUpdate(alert);
	}

	@Override
	public void deleteByIds(List<Long> alertIds) {
		getAlertDAO().deleteByIds(alertIds);
	}

	@Override
	public void markAlertsRead(User loggedInUser) {
		getAlertDAO().markAlertsRead(loggedInUser);
	}

	public void setAlertDAO(AlertDAO alertDAO) {
		this.alertDAO = alertDAO;
	}

	public AlertDAO getAlertDAO() {
		return alertDAO;
	}




}
