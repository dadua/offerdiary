package com.itech.alert.services;

import java.util.List;

import com.itech.alert.model.AlertConfig;

public interface AlertConfigManager {

	void save(AlertConfig alertConfig);

	List<AlertConfig> getAllActiveExpiredAlertConfigs();
	
	List<AlertConfig> getAllActiveAlertConfigs();
	
	List<AlertConfig> getAllSuspendedAlertConfigs();
	
	List<AlertConfig> getAllHandledAlertConfigs();
	
	
	void delete(AlertConfig alertConfig);

	void deleteAlertConfigFor(String dataType, long dataId);
	
	

}
