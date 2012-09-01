package com.itech.alert.services;

import java.util.List;

import com.itech.alert.model.Alert;
import com.itech.user.model.User;

public interface AlertManager {
	void save(Alert alert);

	List<Alert> getAlertsFor(User user);

	List<Alert> getAlertsFor(String dataType, long dataId);

	void delete(Alert alert);

	void deleteAlertsFor(String dataType, long dataId);

	void deleteByIds(List<Long> alertIds);

	void markAlertsRead(User loggedInUser);

}
