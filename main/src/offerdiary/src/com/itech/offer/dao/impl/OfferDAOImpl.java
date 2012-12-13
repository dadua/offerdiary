package com.itech.offer.dao.impl;

import java.sql.Date;
import java.util.List;

import org.hibernate.Query;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.offer.dao.OfferDAO;
import com.itech.offer.model.Offer;
import com.itech.offer.model.OfferCard;
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

	@Override
	public void removeOffersForCard(OfferCard offerCard) {
		String hql = "delete from " + getEntityClassName() + " o where exists " +
				" (select 1 from OfferOfferCardAssoc oca where oca.offer=o and oca.offerCard=:offerCard";
		Query query = getSession().createQuery(hql);
		query.setParameter("offerCard", offerCard);
		int rowsAffected = query.executeUpdate();
	}

	@Override
	public List<Offer> getAllOffersOnCardsForUser(User user) {
		String hql = "select o from " + getEntityClassName() + " o, OfferOfferCardAssoc oca, OfferCardUserAssoc ocua " +
				" where oca.offer=o and oca.offerCard=ocua.offerCard and ocua.user=:user";
		Query query = getSession().createQuery(hql);
		query.setParameter("user", user);
		List list = query.list();
		return list;
	}

	@Override
	public List<Offer> getAllOffersForCard(OfferCard offerCard) {
		String hql = "select o from " + getEntityClassName() + " o, OfferOfferCardAssoc oca " +
				" where oca.offer=o and oca.offerCard=:offerCard";
		Query query = getSession().createQuery(hql);
		query.setParameter("offerCard", offerCard);
		List list = query.list();
		return list;
	}

}
