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
import com.itech.user.model.LoginType;
import com.itech.user.model.User;
import com.itech.user.model.UserRole;

public class UserManagerImpl extends CommonBaseManager implements UserManager, Initialize {
	private static final String OD_ADMIN_NAME = "odadmin";
	private static final String OD_ADMIN_PASSWORD = "od@123!@#";
	private static final String OD_ADMIN_EMAIL_ID = "admin@offerdiary.com";
	private static final Logger logger = Logger.getLogger(UserManagerImpl.class);
	private UserDAO userDAO;
	private final User odAdmin = null;

	@Override
	public void init() {
		String odAdminName = OD_ADMIN_NAME;
		String odAdminPassword = OD_ADMIN_PASSWORD;
		String odAdminEmail = OD_ADMIN_EMAIL_ID;
		User odAdminUser = getUserDAO().getByEmail(odAdminEmail);
		if (odAdminUser == null) {
			odAdminUser = saveEmailUser(odAdminName, odAdminEmail, odAdminPassword, UserRole.OD_ADMIN);
			odAdminUser.setEmailVarified(true);
		}
	}

	@Override
	public User getODAdminUser() {
		return getUserDAO().getByEmail(OD_ADMIN_EMAIL_ID);
	}

	private User saveEmailUser(String name, String email,
			String password, UserRole userRole) {
		User user = new User();
		user.setLoginType(LoginType.EMAIL);
		user.setUserId(email);
		user.setEmailId(email);
		user.setPassword(password);
		user.setName(name);
		user.setUserRole(userRole);
		user.setRegistrationTime((new Date(System.currentTimeMillis())));
		String emailVarficationAccessCode = CommonUtilities.getUniqueId("USER");
		user.setEmailVarficationAccessCode(emailVarficationAccessCode);
		save(user);
		return user;
	}


	private void updateExistingUser(User existingUser, User user) {
		existingUser.setUserId(user.getUserId());
		existingUser.setGender(user.getGender());
		existingUser.setName(user.getName());
		user.setEmailId(user.getEmailId());
		existingUser.setLanguage(user.getLanguage());
		existingUser.setLoginType(LoginType.MULTI);

	}

	@Override
	public User getByEmail(String email) {
		return getUserDAO().getByEmail(email);
	}

	@Override
	public void notifyPassword(User user) {
		user.setNotifyPasswordTime((new Date(System.currentTimeMillis())));
		getUserDAO().addOrUpdate(user);
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
	public User saveEmailUser(String name, String email, String password) {
		return saveEmailUser(name, email, password, UserRole.CONSUMER);
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
	public User getUserForEmailVarificationCode(String emailVarificationCode) {
		return getUserDAO().getUserForEmailVarificationCode(emailVarificationCode);
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public UserDAO getUserDAO() {
		return userDAO;
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


}
