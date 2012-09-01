package com.itech.user.dao;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;


import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.user.model.InterestedUserSubscription;

public class HibernateInterestedUserSubscriptionDAOImpl extends HibernateCommonBaseDAO<InterestedUserSubscription> implements InterestedUserSubscriptionDAO{

	@Override
	public List<InterestedUserSubscription> getAllInterestedUsersSusbscribed() {
		Session sess = getSession();
		Query query = sess.createQuery("from " + getEntityClassName() );
		return query.list();
	}

	@Override
	protected Class getEntityClass() {
		return InterestedUserSubscription.class;
	}

}
