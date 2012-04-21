package com.itech.alert.services;

import java.util.List;

import com.itech.alert.dao.AlertConfigDAO;
import com.itech.alert.model.AlertConfig;

public class AlertConfigManagerImpl implements AlertConfigManager {
	private AlertConfigDAO alertConfigDAO;

	@Override
	public void save(AlertConfig alertConfig) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<AlertConfig> getActiveExpiredConfigs() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setAlertConfigDAO(AlertConfigDAO alertConfigDAO) {
		this.alertConfigDAO = alertConfigDAO;
	}
	public AlertConfigDAO getAlertConfigDAO() {
		return alertConfigDAO;
	}

	@Override
	public void delete(AlertConfig alertConfig) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAlertConfigFor(String dataType, long dataId) {
		// TODO Auto-generated method stub

	}

}
