package com.itech.user.dao;

import java.util.List;

import com.itech.common.db.CommonBaseDAO;
import com.itech.user.model.LinkedAccountType;
import com.itech.user.model.SocialProfile;
import com.itech.user.model.User;

public interface SocialProfileDAO extends CommonBaseDAO<SocialProfile> {
	public SocialProfile getSocialProfileFor(String uniqueId, LinkedAccountType socialProfileType);
	public List<SocialProfile> getSocialProfilesFor(User user);
}
