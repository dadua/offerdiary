package com.itech.alert.services;

import com.itech.alert.dao.AlertConfigDAO;
import com.itech.alert.model.AlertConfig;

public class AlertConfigManagerImpl implements AlertConfigManager {
	private AlertConfigDAO alertConfigDAO;

	@Override
	public void save(AlertConfig alertConfig) {
		// TODO Auto-generated method stub

	}
	public void setAlertConfigDAO(AlertConfigDAO alertConfigDAO) {
		this.alertConfigDAO = alertConfigDAO;
	}
	public AlertConfigDAO getAlertConfigDAO() {
		return alertConfigDAO;
	}

}
