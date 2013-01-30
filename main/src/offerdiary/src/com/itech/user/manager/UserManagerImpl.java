package com.itech.user.manager;

import java.sql.Date;

import org.apache.log4j.Logger;

import com.itech.common.CommonUtilities;
import com.itech.common.exeption.CommonException;
import com.itech.common.exeption.ReturnCodes;
import com.itech.common.services.CommonBaseManager;
import com.itech.event.user.UserEventGenerator;
import com.itech.fb.model.FbProfile;
import com.itech.fb.services.FbService;
import com.itech.user.dao.InterestedUserSubscriptionDAO;
import com.itech.user.dao.UserDAO;
import com.itech.user.model.Gender;
import com.itech.user.model.InterestedUserSubscription;
import com.itech.user.model.LinkedAccount;
import com.itech.user.model.LinkedAccountType;
import com.itech.user.model.LoginType;
import com.itech.user.model.User;
import com.itech.user.model.User.ActivationStatus;

public class UserManagerImpl extends CommonBaseManager implements UserManager {
	private static final Logger logger = Logger.getLogger(UserManagerImpl.class);
	private UserDAO userDAO;
	private InterestedUserSubscriptionDAO intUserSubscriptionDAO;
	private UserEventGenerator userEventGenerator;
	private LinkedAccountManager linkedAccountManager;
	private SocialProfileManager socialProfileManager;

	@Override
	public User saveFbUser(FbService fbService) {
		FbProfile fbProfile = fbService.getUserProfile();
		User user = convertToUserProfile(fbProfile);
		user.setLoginType(LoginType.FACEBOOK);
		user.setActivationStatus(ActivationStatus.ACTIVE);
		user.setRegistrationTime((new Date(System.currentTimeMillis())));
		User existingUser = getUserDAO().getByUserId(user.getUserId());
		if (existingUser != null) {
			return existingUser;
		}
		save(user);
		LinkedAccount fbLinkedAccount = createFbLinkedAccountFor(fbProfile, user);
		fbLinkedAccount.setUsedForLogin(Boolean.TRUE);
		getLinkedAccountManager().saveOrUpdate(fbLinkedAccount);
		getSocialProfileManager().syncFbSocialProfilesFor(fbService, user);
		getUserEventGenerator().newUserAdded(user);
		return user;
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
		User user = new User();
		user.setLoginType(LoginType.EMAIL);
		user.setUserId(email);
		user.setEmailId(email);
		user.setPassword(password);
		user.setName(name);
		user.setRegistrationTime((new Date(System.currentTimeMillis())));
		save(user);
		getUserEventGenerator().newUserAdded(user);
		return user;
	}

	//	@Override
	//	public void syncFbUserFreinds(FbService fbService) {
	//		FbProfile fbProfile = fbService.getUserProfile();
	//		User user = getUserDAO().getUserByFbId(fbProfile.getId());
	//		if (user == null) {
	//			logger.warn("Non existing user can not be synced, fbuserid: " + user.getUserId());
	//		}
	//		List<User> existingUserFriends = new ArrayList<User>();
	//		List<User> nonExistingUserFriends = new ArrayList<User>();
	//		List<FbProfile> friendProfiles = fbService.getFriendProfiles();
	//		for (FbProfile friendProfile : friendProfiles) {
	//			User existingUser = getUserDAO().getUserByFbId(friendProfile.getId());
	//			if (existingUser != null) {
	//				existingUserFriends.add(existingUser);
	//			} else {
	//				User newUser = convertToUserProfile(friendProfile);
	//				nonExistingUserFriends.add(newUser);
	//			}
	//		}
	//		getUserDAO().addOrUpdate(nonExistingUserFriends);
	//		existingUserFriends.addAll(nonExistingUserFriends);
	//		addUserConnections(user, existingUserFriends);
	//	}
	//

	//	private void addUserConnections(User existingUser, List<User> friends) {
	//		for (User userFriend : friends) {
	//			UserConnection existingUserConnection = getUserConnectionDao().getUserConnectionFor(existingUser.getId(), userFriend.getId());
	//			if (existingUserConnection != null) {
	//				continue;
	//			}
	//			UserConnection userConnection = new UserConnection();
	//			userConnection.setUser1(existingUser);
	//			userConnection.setUser2(userFriend);
	//			userConnection.setConnectionType(UserConnectionType.FACEBOOK);
	//			getUserConnectionDao().addOrUpdate(userConnection);
	//		}
	//	}
	//
	//	@Override
	//	public User addUserProfileFrom(FbService fbService) {
	//		FbProfile fbProfile = fbService.getUserProfile();
	//		User user = convertToUserProfile(fbProfile);
	//		user.setActivationStatus(User.ActivationStatus.ACTIVE);
	//		User existingUser = getUserByLoginId(user.getLoginId());
	//		if (existingUser != null) {
	//			if (User.ActivationStatus.INACTIVE.equals(existingUser.getActivationStatus())) {
	//				existingUser.setActivationStatus(User.ActivationStatus.ACTIVE);
	//				userDao.addOrUpdate(existingUser);
	//				syncUserFreinds(fbService);
	//			}
	//			return existingUser;
	//		}
	//
	//		userDao.addOrUpdate(user);
	//
	//		syncUserFreinds(fbService);
	//		return user;
	//
	//	}
	//
	//	@Override
	//	public List<User> getAllConnectedActiveUsersFor(long userId) {
	//		List<UserConnection> listOfConnections = getUserConnectionDao().getUserConnectionsFor(userId);
	//		List<Long> userIds = new ArrayList<Long>();
	//		Set<User> activeConnectedUsers = new HashSet<User>();
	//
	//		for(UserConnection userConnection : listOfConnections){
	//			if(userConnection.getUser1().getId() != userId && ActivationStatus.ACTIVE.equals(userConnection.getUser1().getActivationStatus())){
	//				activeConnectedUsers.add(userConnection.getUser1());
	//				userIds.add(userConnection.getUser1().getId());
	//			}else if(userConnection.getUser2().getId() != userId && ActivationStatus.ACTIVE.equals(userConnection.getUser2().getActivationStatus())){
	//				activeConnectedUsers.add(userConnection.getUser2());
	//				userIds.add(userConnection.getUser2().getId());
	//			}
	//		}
	//		List<User> connectedUsers = new ArrayList<User>(activeConnectedUsers);
	//		setInstalledAppCountsInUsers(connectedUsers, userIds);
	//		return connectedUsers;
	//	}
	//
	//

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
}
