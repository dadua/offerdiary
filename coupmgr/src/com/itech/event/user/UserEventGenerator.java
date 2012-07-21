package com.itech.event.user;

import com.itech.coupon.model.User;

public interface UserEventGenerator {
	
	public void newUserAdded(User user);
	
	public void forgotPassword(User user);
	
	public void newUserSubscribed(User user);
	
	
}
