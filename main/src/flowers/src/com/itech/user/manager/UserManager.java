package com.itech.user.manager;

import java.util.List;

import com.itech.user.model.User;

public interface UserManager {
	public void save(User user);
	public User getByUserId(String userId);
	public User getById(long id);
	public void changePassword(String userId, String currentPassword, String newPassword);

	public List<User> getAllUsers();
	public void updateUserLastLoginTime(User user);
}
