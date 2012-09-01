package com.itech.user.dao;

import java.util.List;

import org.hibernate.Query;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.user.model.User;

public class HibernateUserDAOImpl extends HibernateCommonBaseDAO<User> implements UserDAO {

	@Override
	protected Class getEntityClass() {
		return User.class;
	}

	@Override
	public User getByUserId(String userId) {
		String hql = "from " + getEntityClassName() + " where userId = :userId";
		Query query = getSession().createQuery(hql);
		query.setParameter("userId", userId);
		List list = query.list();
		if (!list.isEmpty()) {
			return (User) list.get(0);
		}
		return null;
	}



}
