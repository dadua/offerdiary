package com.itech.coupon.manager;

import com.itech.coupon.model.User;
import com.itech.fb.services.FbService;

public interface UserManager {
	public void save(User user);
	public User getByUserId(String userId);
	public User getById(long id);
	public User saveEmailUser(String email, String password);
	public User saveFbUser(FbService fbService);
	public void saveInterestedUserForSubscription(String email);

}
