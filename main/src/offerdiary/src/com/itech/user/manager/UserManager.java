package com.itech.user.manager;

import com.itech.fb.services.FbService;
import com.itech.user.model.User;

public interface UserManager {
	public void save(User user);
	public User getByUserId(String userId);
	public User getById(long id);
	public User saveEmailUser(String email, String password, String password2);
	public User saveFbUser(FbService fbService);
	public void saveInterestedUserForSubscription(String email);
	public User getByEmail(String email);
	public void notifyPassword(User user);
}
