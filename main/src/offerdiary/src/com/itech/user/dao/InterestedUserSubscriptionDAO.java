package com.itech.user.dao;

import java.util.List;

import com.itech.common.db.CommonBaseDAO;
import com.itech.user.model.InterestedUserSubscription;

public interface InterestedUserSubscriptionDAO extends CommonBaseDAO<InterestedUserSubscription>{

	public List<InterestedUserSubscription> getAllInterestedUsersSusbscribed(); 
}
