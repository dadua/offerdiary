package com.itech.coupon.manager;

import com.itech.coupon.dao.UserDAO;
import com.itech.coupon.model.Gender;
import com.itech.coupon.model.LoginType;
import com.itech.coupon.model.User;
import com.itech.fb.model.FbProfile;
import com.itech.fb.services.FbService;

public class UserManagerImpl implements UserManager {
	private UserDAO userDAO;


	@Override
	public User saveFbUser(FbService fbService) {
		FbProfile fbProfile = fbService.getUserProfile();
		User user = new User();
		user.setLoginType(LoginType.FACEBOOK);
		user.setUserId(fbProfile.getId());
		user.setGender(Gender.getGender(fbProfile.getGender()));
		user.setName(fbProfile.getFullName());
		user.setEmailId(fbProfile.getEmailId());
		user.setLanguage(fbProfile.getLocale());

		/*
		user.setAge(age);
		user.setLocation(location);
		 */
		save(user);
		return user;
	}

	@Override
	public User getById(long id) {
		return getUserDAO().getById(id);
	}

	@Override
	public User getByUserId(String userId) {
		return getUserDAO().getByUserId(userId);
	}

	@Override
	public void save(User user) {
		User existingUser = getUserDAO().getByUserId(user.getUserId());
		if (existingUser != null) {
			user.setId(existingUser.getId());
			return;
		}
		getUserDAO().addOrUpdate(user);
		existingUser = getUserDAO().getByUserId(user.getUserId());
		user.setId(existingUser.getId());
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

}
