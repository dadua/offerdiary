package com.itech.user.manager;

import java.sql.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.itech.common.CommonUtilities;
import com.itech.common.exeption.CommonException;
import com.itech.common.exeption.ReturnCodes;
import com.itech.common.services.CommonBaseManager;
import com.itech.common.services.Initialize;
import com.itech.event.user.UserEventGenerator;
import com.itech.fb.model.FbProfile;
import com.itech.fb.services.FbService;
import com.itech.offer.job.ItechJob;
import com.itech.offer.job.ItechJobManager;
import com.itech.user.dao.InterestedUserSubscriptionDAO;
import com.itech.user.dao.UserDAO;
import com.itech.user.dao.UserNotificationConfigDAO;
import com.itech.user.model.Gender;
import com.itech.user.model.InterestedUserSubscription;
import com.itech.user.model.LinkedAccount;
import com.itech.user.model.LinkedAccountType;
import com.itech.user.model.LoginType;
import com.itech.user.model.User;
import com.itech.user.model.User.ActivationStatus;
import com.itech.user.model.UserNotificationConfig;
import com.itech.user.model.UserRole;

public class UserManagerImpl extends CommonBaseManager implements UserManager, Initialize {
	private static final String OD_ADMIN_NAME = "odadmin";
	private static final String OD_ADMIN_PASSWORD = "od@123!@#";
	private static final String OD_ADMIN_EMAIL_ID = "admin@offerdiary.com";
	private static final Logger logger = Logger.getLogger(UserManagerImpl.class);
	private UserDAO userDAO;
	private InterestedUserSubscriptionDAO intUserSubscriptionDAO;
	private UserEventGenerator userEventGenerator;
	private LinkedAccountManager linkedAccountManager;
	private SocialProfileManager socialProfileManager;
	private UserNotificationConfigDAO  userNotificationConfigDAO;
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
			UserNotificationConfig userNotificationConfigForOdAdmin = getUserNotificationConfigFor(odAdminUser);
			userNotificationConfigForOdAdmin.switchOffAllAlerts();
			getUserNotificationConfigDAO().addOrUpdate(userNotificationConfigForOdAdmin);
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
		getUserEventGenerator().newUserAdded(user);
		return user;
	}


	@Override
	public User saveFbUser(FbService fbService) {
		FbProfile fbProfile = fbService.getUserProfile();
		User user = convertToUserProfile(fbProfile);
		user.setLoginType(LoginType.FACEBOOK);
		user.setActivationStatus(ActivationStatus.ACTIVE);
		user.setRegistrationTime((new Date(System.currentTimeMillis())));
		String emailVarficationAccessCode = CommonUtilities.getUniqueId("USER");
		user.setEmailVarficationAccessCode(emailVarficationAccessCode);

		User existingUser = getUserDAO().getByUserId(user.getUserId());
		if (existingUser != null) {
			return existingUser;
		}

		User existingEmailUser = getUserDAO().getByEmail(user.getEmailId());
		if (existingEmailUser != null) {
			updateExistingUser(existingEmailUser, user);
			user = existingEmailUser;
		}

		save(user);
		LinkedAccount fbLinkedAccount = createFbLinkedAccountFor(fbProfile, user);
		fbLinkedAccount.setUsedForLogin(Boolean.TRUE);
		getLinkedAccountManager().saveOrUpdate(fbLinkedAccount);
		createFbSyncTaskFor(user, fbService);
		getUserEventGenerator().newUserAdded(user);
		return user;
	}

	private void createFbSyncTaskFor(User user, FbService fbService) {
		ItechJob job = new FbUserSyncJob(fbService, user);
		ItechJobManager.addJob(job, "FB_SYNC_" + user.getUserId());
	}

	private void updateExistingUser(User existingUser, User user) {
		existingUser.setUserId(user.getUserId());
		existingUser.setGender(user.getGender());
		existingUser.setName(user.getName());
		user.setEmailId(user.getEmailId());
		existingUser.setLanguage(user.getLanguage());
		existingUser.setLoginType(LoginType.MULTI);

	}
	private LinkedAccount createFbLinkedAccountFor(FbProfile fbProfile, User user) {
		LinkedAccount existingLinkedAccount = getLinkedAccountManager().getLinkedAccountFor(fbProfile.getId(), LinkedAccountType.FACEBOOK);
		if (existingLinkedAccount !=null) {
			return existingLinkedAccount;
		}
		LinkedAccount linkedAccount = new LinkedAccount();
		linkedAccount.setLinkedAccountType(LinkedAccountType.FACEBOOK);
		linkedAccount.setUniqueId(fbProfile.getId());
		linkedAccount.setUser(user);
		return linkedAccount;
	}

	private User convertToUserProfile(FbProfile fbProfile) {
		User user = new User();
		user.setUserId(fbProfile.getId());
		user.setGender(Gender.getGender(fbProfile.getGender()));
		user.setName(fbProfile.getFullName());
		user.setEmailId(fbProfile.getEmailId());
		user.setLanguage(fbProfile.getLocale());
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
		if (existingUser == null) {
			UserNotificationConfig userNotificationConfig = createDefaultUserNotificationConfig();
			userNotificationConfig.setUser(user);
			save(userNotificationConfig);
		}
	}

	@Override
	public void save(UserNotificationConfig userNotificationConfig) {
		getUserNotificationConfigDAO().addOrUpdate(userNotificationConfig);
	}

	private UserNotificationConfig createDefaultUserNotificationConfig() {
		return new UserNotificationConfig();
	}

	@Override
	public UserNotificationConfig getUserNotificationConfigFor(User user) {
		return getUserNotificationConfigDAO().getUserNotificationConfigFor(user);
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

	public LinkedAccountManager getLinkedAccountManager() {
		return linkedAccountManager;
	}

	public void setLinkedAccountManager(LinkedAccountManager linkedAccountManager) {
		this.linkedAccountManager = linkedAccountManager;
	}
	public SocialProfileManager getSocialProfileManager() {
		return socialProfileManager;
	}
	public void setSocialProfileManager(SocialProfileManager socialProfileManager) {
		this.socialProfileManager = socialProfileManager;
	}
	public UserNotificationConfigDAO getUserNotificationConfigDAO() {
		return userNotificationConfigDAO;
	}
	public void setUserNotificationConfigDAO(UserNotificationConfigDAO userNotificationConfigDAO) {
		this.userNotificationConfigDAO = userNotificationConfigDAO;
	}

	@Override
	public List<User> getAllUsers() {
		return userDAO.getAll();
	}


}
