package com.itech.user.dao;

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
		return getSingleResultFrom(query);
	}

	@Override
	public User getByEmail(String email) {
		String hql = "from " + getEntityClassName() + " where emailId = :emailId";
		Query query = getSession().createQuery(hql);
		query.setParameter("emailId", email);
		return getSingleResultFrom(query);
	}

	@Override
	public User getUserForEmailVarificationCode(String emailVarificationCode) {
		String hql = "from " + getEntityClassName() + " where emailVarficationAccessCode = :emailVarficationAccessCode";
		Query query = getSession().createQuery(hql);
		query.setParameter("emailVarficationAccessCode", emailVarificationCode);
		return getSingleResultFrom(query);
	}
}
