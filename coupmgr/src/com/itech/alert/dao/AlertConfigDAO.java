package com.itech.alert.dao;

import java.util.List;

import com.itech.alert.model.AlertConfig;
import com.itech.alert.model.AlertConfig.ActivationStatus;
import com.itech.common.db.CommonBaseDAO;

public interface AlertConfigDAO extends CommonBaseDAO<AlertConfig>{

	void delete(AlertConfig alertConfig);

	boolean deleteForDataType(String dataType, long dataId);

	List<AlertConfig> getAllAlertConfigExpiredWithStatus(ActivationStatus active);

	List<AlertConfig> getAllAlertConfigWithStatus(ActivationStatus status);

}
