package com.itech.coupon.dao;

import java.util.List;

import com.itech.common.db.CommonBaseDAO;
import com.itech.coupon.model.InterestedUserSubscription;

public interface InterestedUserSubscriptionDAO extends CommonBaseDAO<InterestedUserSubscription>{

	public List<InterestedUserSubscription> getAllInterestedUsersSusbscribed(); 
}
