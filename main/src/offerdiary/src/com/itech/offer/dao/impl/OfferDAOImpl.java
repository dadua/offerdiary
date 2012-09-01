package com.itech.offer.dao.impl;

import java.sql.Date;
import java.util.List;

import org.hibernate.Query;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.offer.dao.OfferDAO;
import com.itech.offer.model.Offer;
import com.itech.offer.model.enums.OfferType;
import com.itech.user.model.User;

public class OfferDAOImpl extends HibernateCommonBaseDAO<Offer> implements OfferDAO{

	@Override
	protected Class getEntityClass() {
		return Offer.class;
	}

	@Override
	public List<Offer> getAllOfferForUser(User user) {
		String hql = "select o from " + getEntityClassName() + " o, OfferUserAssoc oua  where oua.offer=o and oua.user=:user";
		Query query = getSession().createQuery(hql);
		query.setParameter("user", user);
		List list = query.list();
		return list;
	}

	@Override
	public List<Offer> getAllUnexpiredOffersForUser(User user) {
		String hql = "select o from " + getEntityClassName() + " o, OfferUserAssoc oua  where oua.offer=o and oua.user=:user and o.expiry < :now";
		Query query = getSession().createQuery(hql);
		query.setParameter("user", user);
		query.setParameter("now", new Date(System.currentTimeMillis()));
		List list = query.list();
		return list;
	}

	@Override
	public List<Offer> getAllOfferForUser(User user, OfferType offerType) {
		String hql = "select o from " + getEntityClassName() + " o, OfferUserAssoc oua  where oua.offer=o and oua.user=:user and offer.offerType=:offerType";
		Query query = getSession().createQuery(hql);
		query.setParameter("user", user);
		query.setParameter("offerType", offerType);
		List list = query.list();
		return list;
	}

}
