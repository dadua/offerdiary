package com.itech.user.dao;

import org.hibernate.Query;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.user.model.User;
import com.itech.user.model.UserNotificationConfig;

public class UserNotificationConfigDAOImpl extends HibernateCommonBaseDAO<UserNotificationConfig> implements
UserNotificationConfigDAO {

	@Override
	public UserNotificationConfig getUserNotificationConfigFor(User user) {
		String hql = "from " + getEntityClassName() + " where user=:user";
		Query query = getSession().createQuery(hql);
		query.setParameter("user", user);
		return getSingleResultFrom(query);
	}

	@Override
	protected Class getEntityClass() {
		return UserNotificationConfig.class;
	}

}
