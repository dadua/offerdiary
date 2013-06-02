package com.itech.user.manager;

import java.util.List;

import com.itech.user.model.User;

public interface UserManager {
	public User getODAdminUser();
	public void save(User user);
	public User getByUserId(String userId);
	public User getById(long id);
	public User saveEmailUser(String email, String password, String password2);
	public User getByEmail(String email);
	public void notifyPassword(User user);
	public void changePassword(String userId, String currentPassword, String newPassword);



	//User notifications
	public User getUserForEmailVarificationCode(String emailVarificationCode);

	//For Od admin as of now
	public List<User> getAllUsers();
	public void updateUserLastLoginTime(User user);
}
