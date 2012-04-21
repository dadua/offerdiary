package com.itech.alert.services;

import java.util.List;

import com.itech.alert.model.AlertConfig;

public interface AlertConfigManager {

	void save(AlertConfig alertConfig);

	List<AlertConfig> getActiveExpiredConfigs();

	void delete(AlertConfig alertConfig);

	void deleteAlertConfigFor(String dataType, long dataId);

}
