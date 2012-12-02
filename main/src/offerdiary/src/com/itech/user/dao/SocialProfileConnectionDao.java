package com.itech.user.dao;

import java.util.List;

import com.itech.common.db.CommonBaseDAO;
import com.itech.user.model.LinkedAccountType;
import com.itech.user.model.SocialProfileConnection;
import com.itech.user.model.User;


public interface SocialProfileConnectionDao extends CommonBaseDAO<SocialProfileConnection> {
	public SocialProfileConnection getSocialProfileConnectionFor(long socialProfileId1, long socialProfileId2);
	public List<SocialProfileConnection> getSocialProfileConnectionsFor(long socialProfileId);
	public List<SocialProfileConnection> getSocialProfileConnectionFor(User user);
	public List<SocialProfileConnection> getSocialProfileConnectionFor(
			User user, LinkedAccountType socialProfileType);
}
