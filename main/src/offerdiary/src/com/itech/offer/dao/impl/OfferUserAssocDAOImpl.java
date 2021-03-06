package com.itech.offer.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.offer.dao.OfferUserAssocDAO;
import com.itech.offer.model.Offer;
import com.itech.offer.model.OfferUserAssoc;
import com.itech.offer.model.enums.OfferOwnershipType;
import com.itech.user.model.User;

public class OfferUserAssocDAOImpl extends HibernateCommonBaseDAO<OfferUserAssoc> implements OfferUserAssocDAO{

	@Override
	protected Class getEntityClass() {
		// TODO Auto-generated method stub
		return OfferUserAssoc.class;
	}

	@Override
	public List<OfferUserAssoc> getOfferUserAssocForOffer(Long offerId) {
		String hql = "select oua from " + getEntityClassName() + " oua where oua.offer.id = :offerId";
		Query query = getSession().createQuery(hql);
		query.setParameter("offerId", offerId);
		return query.list();
	}

	@Override
	public User getOfferOwner(Offer offer) {
		String hql="from "+getEntityClassName()+" where offer =:offer and ownershipType=:ownerShipType ";
		Query query = getSession().createQuery(hql);
		query.setParameter("offer", offer);
		query.setParameter("ownerShipType", OfferOwnershipType.CREATOR);
		OfferUserAssoc assoc = (OfferUserAssoc) getSingleResult(query);
		User owner = assoc.getUser();
		return owner;
	}

	@Override
	public OfferUserAssoc getAssocFor(Offer offer, User user) {
		String hql="from "+getEntityClassName()+" where offer =:offer and user=:user ";
		Query query = getSession().createQuery(hql);
		query.setParameter("offer", offer);
		query.setParameter("user", user);
		OfferUserAssoc assoc = (OfferUserAssoc) getSingleResult(query);
		return assoc;

	}
}
