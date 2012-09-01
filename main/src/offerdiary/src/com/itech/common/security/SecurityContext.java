package com.itech.common.security;

import com.itech.user.model.User;


public class SecurityContext {
	public static final String USER_SESSION_KEY = "user_session_key";
	private final User user;

	public SecurityContext(User user) {
		super();
		this.user = user;
	}

	public User getUser() {
		return user;
	}

}
