package com.itech.alert.dao;

import java.util.List;

import com.itech.alert.model.Alert;
import com.itech.common.db.CommonBaseDAO;
import com.itech.coupon.model.User;

public interface AlertDAO extends CommonBaseDAO<Alert>{

	void delete(Alert alert);

	boolean deleteAlertsFor(String dataType, long dataId);

	List<Alert> getAlertsForUser(User user);

	List<Alert> getAlertsForDataType(String dataType, long dataId);

	void deleteByIds(List<Long> alertIds);

	void markAlertsRead(User loggedInUser);

}
