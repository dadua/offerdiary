package com.itech.user.manager;

import java.sql.Date;

import com.itech.common.services.CommonBaseManager;
import com.itech.event.user.UserEventGenerator;
import com.itech.fb.model.FbProfile;
import com.itech.fb.services.FbService;
import com.itech.user.dao.InterestedUserSubscriptionDAO;
import com.itech.user.dao.UserDAO;
import com.itech.user.model.Gender;
import com.itech.user.model.InterestedUserSubscription;
import com.itech.user.model.LoginType;
import com.itech.user.model.User;

public class UserManagerImpl extends CommonBaseManager implements UserManager {
	private UserDAO userDAO;
	private InterestedUserSubscriptionDAO intUserSubscriptionDAO;
	private UserEventGenerator userEventGenerator;


	
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
		user.setRegistrationTime((new Date(System.currentTimeMillis())));
		/*
		user.setAge(age);
		user.setLocation(location);
		 */
		save(user);
		return user;
	}
	
	@Override
	public User getByEmail(String email) {
		return getUserDAO().getByEmail(email);
	}	
	
	@Override
	public void saveInterestedUserForSubscription(String email) {
		InterestedUserSubscription interestedUser = new InterestedUserSubscription();
		interestedUser.setEmailId(email);
		interestedUser.setSusbscriptionTime(new Date(System.currentTimeMillis()));
		getIntUserSubscriptionDAO().addOrUpdate(interestedUser);
		getHibernateSessionFactory().commitCurrentTransaction();
		User subscribedUser = new User();
		subscribedUser.setEmailId(email);
		getUserEventGenerator().newUserSubscribed(subscribedUser);
	}


	@Override
	public void notifyPassword(User user) {
		user.setNotifyPasswordTime((new Date(System.currentTimeMillis())));
		getUserDAO().addOrUpdate(user);
		getUserEventGenerator().forgotPassword(user);
	}
	
	@Override
	public User saveEmailUser(String name, String email, String password) {
		User user = new User();
		user.setLoginType(LoginType.INTERNAL);
		user.setUserId(email);
		user.setEmailId(email);
		user.setPassword(password);
		user.setName(name);
		user.setRegistrationTime((new Date(System.currentTimeMillis())));
		save(user);
		getUserEventGenerator().newUserAdded(user);
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
		getUserDAO().addOrUpdate(user);
		User existingUser = getUserDAO().getByUserId(user.getUserId());
		user.setId(existingUser.getId());
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public UserEventGenerator getUserEventGenerator() {
		return userEventGenerator;
	}

	public void setUserEventGenerator(UserEventGenerator userEventGenerator) {
		this.userEventGenerator = userEventGenerator;
	}

	public InterestedUserSubscriptionDAO getIntUserSubscriptionDAO() {
		return intUserSubscriptionDAO;
	}

	public void setIntUserSubscriptionDAO(InterestedUserSubscriptionDAO intUserSubscriptionDAO) {
		this.intUserSubscriptionDAO = intUserSubscriptionDAO;
	}
}
