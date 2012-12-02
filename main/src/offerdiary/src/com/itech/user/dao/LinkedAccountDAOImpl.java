package com.itech.user.dao;

import java.util.List;

import org.hibernate.Query;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.user.model.LinkedAccount;
import com.itech.user.model.LinkedAccountType;
import com.itech.user.model.User;

public class LinkedAccountDAOImpl extends HibernateCommonBaseDAO<LinkedAccount> implements
LinkedAccountDAO {

	@Override
	protected Class getEntityClass() {
		return LinkedAccount.class;
	}

	@Override
	public List<LinkedAccount> getAllLinkedAccountsFor(User user) {
		String hql = "from " + getEntityClassName() + " where user=:user";
		Query query = getSession().createQuery(hql);
		query.setParameter("user", user);
		List list = query.list();
		return list;
	}

	@Override
	public LinkedAccount getPrimaryLinkedAccountFor(User user,
			LinkedAccountType linkedAccountType) {
		String hql = "from " + getEntityClassName() + " where user=:user and linkedAccountType=:linkedAccountType";
		Query query = getSession().createQuery(hql);
		query.setParameter("user", user);
		query.setParameter("linkedAccountType", linkedAccountType);
		return getSingleResultFrom(query);
	}

	@Override
	public LinkedAccount getLinkedAccountFor(String uniqueId,
			LinkedAccountType linkedAccountType) {
		String hql = "from " + getEntityClassName() + " where uniqueId=:uniqueId and linkedAccountType=:linkedAccountType";
		Query query = getSession().createQuery(hql);
		query.setParameter("uniqueId", uniqueId);
		query.setParameter("linkedAccountType", linkedAccountType);
		return getSingleResultFrom(query);
	}


}
