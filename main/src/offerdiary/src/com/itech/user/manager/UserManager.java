package com.itech.user.manager;

import java.util.List;

import com.itech.fb.services.FbService;
import com.itech.user.model.User;
import com.itech.user.model.UserNotificationConfig;

public interface UserManager {
	public User getODAdminUser();
	public void save(User user);
	public User getByUserId(String userId);
	public User getById(long id);
	public User saveEmailUser(String email, String password, String password2);
	public User saveFbUser(FbService fbService);
	public void saveInterestedUserForSubscription(String email);
	public User getByEmail(String email);
	public void notifyPassword(User user);
	public void changePassword(String userId, String currentPassword, String newPassword);



	//User notifications
	public UserNotificationConfig getUserNotificationConfigFor(User user);
	public void save(UserNotificationConfig userNotificationConfig);
	public User getUserForEmailVarificationCode(String emailVarificationCode);

	public List<User> getAllUsers();
}
