package com.itech.common.security;

import com.itech.user.model.User;

public interface SecurityManager {
	public User getLoggedInUser();
}
