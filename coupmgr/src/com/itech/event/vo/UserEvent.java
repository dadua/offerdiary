package com.itech.event.vo;

import com.itech.coupon.model.User;

public class UserEvent extends Event {
	
	public enum UserEventType {
		NEW_USER_ADDED, FORGOT_PASSWORD
	}

	private UserEventType userEventType;
	private User user;

	public UserEvent(UserEventType userEventType,User user) {
		super();
		this.setUserEventType(userEventType);
		this.setUser(user);
	}

	public UserEventType getUserEventType() {
		return userEventType;
	}

	public void setUserEventType(UserEventType userEventType) {
		this.userEventType = userEventType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
