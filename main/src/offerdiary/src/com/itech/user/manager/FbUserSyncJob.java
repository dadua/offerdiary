package com.itech.user.manager;

import org.apache.log4j.Logger;

import com.itech.common.services.ServiceLocator;
import com.itech.fb.services.FbService;
import com.itech.offer.job.BaseItechJob;
import com.itech.user.model.User;

public class FbUserSyncJob extends BaseItechJob {
	private static final int INITIAL_DELAY = 15000;

	private static final Logger logger = Logger.getLogger(FbUserSyncJob.class);

	private SocialProfileManager socialProfileManager;
	private UserManager userManager;
	private final FbService fbService;
	private  User user;

	public FbUserSyncJob(FbService fbService, User user) {
		this.fbService = fbService;
		this.user = user;

	}

	@Override
	protected void doJobTask() {
		try {
			Thread.sleep(INITIAL_DELAY);
		} catch (InterruptedException e) {
			logger.error("Error while waiting of fb sync task for user:" + user.getName(), e);
		}
		this.user = getUserManager().getByUserId(user.getUserId());
		getSocialProfileManager().syncFbSocialProfilesFor(fbService, user);
	}

	public SocialProfileManager getSocialProfileManager() {
		if (socialProfileManager == null) {
			socialProfileManager = ServiceLocator.getInstance().getBean(SocialProfileManager.class);
		}
		return socialProfileManager;
	}

	public void setSocialProfileManager(SocialProfileManager socialProfileManager) {
		this.socialProfileManager = socialProfileManager;
	}

	public UserManager getUserManager() {
		if (userManager == null) {
			userManager = ServiceLocator.getInstance().getBean(UserManager.class);
		}
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

}
