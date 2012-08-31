package com.itech.common.security;

import com.itech.coupon.model.User;

public interface SecurityManager {
	public User getLoggedInUser();
}
