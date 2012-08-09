package com.itech.alert.services;

import java.util.List;

import com.itech.alert.dao.AlertConfigDAO;
import com.itech.alert.model.AlertConfig;

public class AlertConfigManagerImpl implements AlertConfigManager {
	private AlertConfigDAO alertConfigDAO;

	
	@Override
	public void delete(AlertConfig alertConfig) {
		getAlertConfigDAO().delete(alertConfig);
	}

	@Override
	public void deleteAlertConfigFor(String dataType, long dataId) {
		getAlertConfigDAO().deleteForDataType(dataType, dataId);
	}
	
	@Override
	public List<AlertConfig> getAllActiveExpiredAlertConfigs() {
		return getAlertConfigDAO().getAllAlertConfigExpiredWithStatus(AlertConfig.ActivationStatus.ACTIVE);
	}

	@Override
	public List<AlertConfig> getAllActiveAlertConfigs() {
		return getAlertConfigDAO().getAllAlertConfigWithStatus(AlertConfig.ActivationStatus.ACTIVE);
	}
	
	@Override
	public List<AlertConfig> getAllSuspendedAlertConfigs() {
		return getAlertConfigDAO().getAllAlertConfigWithStatus(AlertConfig.ActivationStatus.SUSPENDED);
		
	}

	@Override
	public List<AlertConfig> getAllHandledAlertConfigs() {
		return getAlertConfigDAO().getAllAlertConfigWithStatus(AlertConfig.ActivationStatus.HANDLED);
	}

	
	@Override
	public void save(AlertConfig alertConfig) {
		getAlertConfigDAO().addOrUpdate(alertConfig);
	}

	public void setAlertConfigDAO(AlertConfigDAO alertConfigDAO) {
		this.alertConfigDAO = alertConfigDAO;
	}
	public AlertConfigDAO getAlertConfigDAO() {
		return alertConfigDAO;
	}
	
}
