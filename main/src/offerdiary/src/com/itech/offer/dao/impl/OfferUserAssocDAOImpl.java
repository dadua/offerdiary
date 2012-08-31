package com.itech.offer.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.coupon.model.User;
import com.itech.offer.dao.OfferUserAssocDAO;
import com.itech.offer.model.Offer;
import com.itech.offer.model.OfferUserAssoc;

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
		String hql="from "+getEntityClassName()+" where offer =:offer ";
		Query query = getSession().createQuery(hql);
		query.setParameter("offer", offer);
		List<OfferUserAssoc> offerUserAssocList = query.list();
		if(!offerUserAssocList.isEmpty()){
			return offerUserAssocList.get(0).getUser();
		}
		return null;
	}
}
