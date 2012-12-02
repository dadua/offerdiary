package com.itech.user.manager;

import java.util.List;

import com.itech.fb.services.FbService;
import com.itech.user.model.LinkedAccountType;
import com.itech.user.model.SocialProfileConnection;
import com.itech.user.model.User;

public interface SocialProfileManager {
	public void syncFbSocialProfilesFor(FbService fbService, User user);
	public List<SocialProfileConnection> getSocialProfileConnectionsFor(User user);
	public List<SocialProfileConnection> getSocialProfileConnectionsFor(User user, LinkedAccountType socialProfileType);
}
