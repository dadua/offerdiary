package com.itech.user.manager;

import java.util.ArrayList;
import java.util.List;

import com.itech.common.services.CommonBaseManager;
import com.itech.fb.model.FbProfile;
import com.itech.fb.services.FbService;
import com.itech.user.dao.SocialProfileConnectionDao;
import com.itech.user.dao.SocialProfileDAO;
import com.itech.user.model.LinkedAccount;
import com.itech.user.model.LinkedAccountType;
import com.itech.user.model.SocialProfile;
import com.itech.user.model.SocialProfileConnection;
import com.itech.user.model.User;

public class SocialProfileManagerImpl extends CommonBaseManager implements
SocialProfileManager {

	private SocialProfileConnectionDao socialProfileConnectionDao;
	private SocialProfileDAO socialProfileDAO;
	private LinkedAccountManager linkedAccountManager;

	@Override
	public void syncFbSocialProfilesFor(FbService fbService, User user) {
		FbProfile fbProfile = fbService.getUserProfile();
		LinkedAccount linkedAccount = getLinkedAccountManager().getPrimaryLinkedAccountFor(user, LinkedAccountType.FACEBOOK);
		SocialProfile selfSocialProfile = convertToUserProfile(fbProfile);
		selfSocialProfile.setLinkedAccount(linkedAccount);
		SocialProfile existingSelfSocialProfile = getSocialProfileDAO().getSocialProfileFor(selfSocialProfile.getUniqueId(),
				selfSocialProfile.getSocialProfileType());

		if (existingSelfSocialProfile == null) {
			getSocialProfileDAO().addOrUpdate(selfSocialProfile);
		} else {
			selfSocialProfile = existingSelfSocialProfile;
			if (selfSocialProfile.getLinkedAccount() == null) {
				selfSocialProfile.setLinkedAccount(linkedAccount);
				selfSocialProfile.setLinkedAccount(linkedAccount);
				getSocialProfileDAO().addOrUpdate(selfSocialProfile);
			}
		}


		List<SocialProfile> existingSocialProfilesForFriends = new ArrayList<SocialProfile>();
		List<SocialProfile> nonExistingSocialProfilesForFriends = new ArrayList<SocialProfile>();
		List<FbProfile> friendProfiles = fbService.getFriendProfiles();
		for (FbProfile friendProfile : friendProfiles) {
			SocialProfile socialProfileForFriend = convertToUserProfile(friendProfile);
			SocialProfile existingSocialProfileForFriend = getSocialProfileDAO().getSocialProfileFor(socialProfileForFriend.getUniqueId(), socialProfileForFriend.getSocialProfileType());
			if (existingSocialProfileForFriend != null) {
				existingSocialProfilesForFriends.add(existingSocialProfileForFriend);
			} else {
				nonExistingSocialProfilesForFriends.add(socialProfileForFriend);
			}
		}
		getSocialProfileDAO().addOrUpdate(nonExistingSocialProfilesForFriends);
		existingSocialProfilesForFriends.addAll(nonExistingSocialProfilesForFriends);
		addSocialProfileConnections(selfSocialProfile, existingSocialProfilesForFriends);

	}

	private void addSocialProfileConnections(SocialProfile selfSocialProfile,
			List<SocialProfile> socialProfilesForFriends) {
		for (SocialProfile socialProfileForFriend : socialProfilesForFriends) {
			SocialProfileConnection existingSocialProfileConnection = getSocialProfileConnectionDao().getSocialProfileConnectionFor(selfSocialProfile.getId(),
					socialProfileForFriend.getId());
			if (existingSocialProfileConnection != null) {
				continue;
			}
			SocialProfileConnection socialProfileConnection = new SocialProfileConnection();
			socialProfileConnection.setSocialProfile1(selfSocialProfile);
			socialProfileConnection.setSocialProfile2(socialProfileForFriend);
			socialProfileConnection.setSocialProfileType(selfSocialProfile.getSocialProfileType());
			getSocialProfileConnectionDao().addOrUpdate(socialProfileConnection);
		}

	}

	private SocialProfile convertToUserProfile(FbProfile fbProfile) {
		SocialProfile socialProfile = new SocialProfile();
		socialProfile.setUniqueId(fbProfile.getId());
		socialProfile.setName(fbProfile.getName());
		socialProfile.setEmailId(fbProfile.getEmailId());
		socialProfile.setSocialProfileType(LinkedAccountType.FACEBOOK);
		return socialProfile;
	}

	@Override
	public List<SocialProfileConnection> getSocialProfileConnectionsFor(
			User user) {
		return getSocialProfileConnectionDao().getSocialProfileConnectionFor(user);
	}

	@Override
	public List<SocialProfileConnection> getSocialProfileConnectionsFor(
			User user, LinkedAccountType socialProfileType) {
		return getSocialProfileConnectionDao().getSocialProfileConnectionFor(user, socialProfileType);
	}

	public SocialProfileConnectionDao getSocialProfileConnectionDao() {
		return socialProfileConnectionDao;
	}

	public void setSocialProfileConnectionDao(SocialProfileConnectionDao socialProfileConnectionDao) {
		this.socialProfileConnectionDao = socialProfileConnectionDao;
	}

	public SocialProfileDAO getSocialProfileDAO() {
		return socialProfileDAO;
	}

	public void setSocialProfileDAO(SocialProfileDAO socialProfileDAO) {
		this.socialProfileDAO = socialProfileDAO;
	}

	public LinkedAccountManager getLinkedAccountManager() {
		return linkedAccountManager;
	}

	public void setLinkedAccountManager(LinkedAccountManager linkedAccountManager) {
		this.linkedAccountManager = linkedAccountManager;
	}

}
