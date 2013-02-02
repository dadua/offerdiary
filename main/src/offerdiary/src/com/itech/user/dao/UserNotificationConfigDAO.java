package com.itech.user.dao;

import com.itech.common.db.CommonBaseDAO;
import com.itech.user.model.User;
import com.itech.user.model.UserNotificationConfig;

public interface UserNotificationConfigDAO extends CommonBaseDAO<UserNotificationConfig> {
	public UserNotificationConfig getUserNotificationConfigFor(User user);
}
