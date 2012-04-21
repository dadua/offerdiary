package com.itech.alert.services;

import java.util.List;

import com.itech.alert.dao.AlertDAO;
import com.itech.alert.model.Alert;
import com.itech.coupon.model.User;

public class AlertManagerImpl implements AlertManager {
	private AlertDAO alertDAO;

	@Override
	public void delete(Alert alert) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAlertsFor(String dataType, long dataId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Alert> getAlertsFor(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Alert> getAlertsFor(String dataType, long dataId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Alert alert) {
		// TODO Auto-generated method stub

	}

	public void setAlertDAO(AlertDAO alertDAO) {
		this.alertDAO = alertDAO;
	}

	public AlertDAO getAlertDAO() {
		return alertDAO;
	}

}
