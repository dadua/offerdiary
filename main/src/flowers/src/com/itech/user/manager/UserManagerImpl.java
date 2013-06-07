package com.itech.user.manager;

import java.sql.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.itech.common.CommonUtilities;
import com.itech.common.exeption.CommonException;
import com.itech.common.exeption.ReturnCodes;
import com.itech.common.services.CommonBaseManager;
import com.itech.common.services.Initialize;
import com.itech.user.dao.UserDAO;
import com.itech.user.model.User;
import com.itech.user.model.UserRole;

public class UserManagerImpl extends CommonBaseManager implements UserManager, Initialize {
	private static final String ADMIN_USER_ID = "admin";
	private static final String ADMIN_USER_NAME = "admin";
	private static final String ADMIN_PASSWORD = "admin";
	private static final String ADMIN_EMAIL_ID = "admin@offerdiary.com";
	private static final Logger logger = Logger.getLogger(UserManagerImpl.class);
	private UserDAO userDAO;
	private final User odAdmin = null;

	@Override
	public void init() {
		String adminUserId = ADMIN_USER_ID;
		String adminUserName = ADMIN_USER_NAME;
		String adminUserPassword = ADMIN_PASSWORD;
		String odAdmadminUserEmailinEmail = ADMIN_EMAIL_ID;
		User adminUser = getUserDAO().getByUserId(adminUserId);
		if (adminUser == null) {
			adminUser = saveUser(adminUserId, adminUserName, odAdmadminUserEmailinEmail, adminUserPassword, UserRole.ADMIN);
		}
	}

	private User saveUser(String userId, String name, String email,
			String password, UserRole userRole) {
		User user = new User();
		user.setUserId(userId);
		user.setEmailId(email);
		user.setPassword(password);
		user.setName(name);
		user.setUserRole(userRole);
		user.setRegistrationTime((new Date(System.currentTimeMillis())));
		save(user);
		return user;
	}



	@Override
	public void changePassword(String userId, String currentPassword,
			String newPassword) {
		User existingUser = getByUserId(userId);

		if (CommonUtilities.isNullOrEmpty(newPassword)) {
			throw new CommonException(ReturnCodes.VALIDATION_FAILURE);
		}
		if(!existingUser.getPassword().equals(currentPassword)) {
			throw new CommonException(ReturnCodes.INVALID_CURRENT_PASSWORD);
		}

		existingUser.setPassword(newPassword);
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
		}
		getUserDAO().addOrUpdate(user);
	}


	@Override
	public List<User> getAllUsers() {
		return userDAO.getAll();
	}

	@Override
	public void updateUserLastLoginTime(User user) {
		long currentTimeMillis = System.currentTimeMillis();
		user.setLastLoginTime(new Date(currentTimeMillis));
		userDAO.addOrUpdate(user);
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}


}
