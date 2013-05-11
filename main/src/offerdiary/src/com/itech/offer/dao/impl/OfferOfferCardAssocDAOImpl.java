package com.itech.offer.dao.impl;

import org.hibernate.Query;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.offer.dao.OfferOfferCardAssocDAO;
import com.itech.offer.model.Offer;
import com.itech.offer.model.OfferCard;
import com.itech.offer.model.OfferOfferCardAssoc;

public class OfferOfferCardAssocDAOImpl extends HibernateCommonBaseDAO<OfferOfferCardAssoc> implements
OfferOfferCardAssocDAO {

	@Override
	protected Class getEntityClass() {
		return OfferOfferCardAssoc.class;
	}

	@Override
	public void removeOffersForCard(OfferCard offerCard) {
		String hql = "delete from " + getEntityClassName() + " oca where oca.offerCard=:offerCard";
		Query query = getSession().createQuery(hql);
		query.setParameter("offerCard", offerCard);
		int rowsAffected = query.executeUpdate();
	}

	@Override
	public OfferOfferCardAssoc getOfferAssocFor(OfferCard offerCard, String offerDescription,
			String targetVendorName) {
		String hql = "select oca from " + getEntityClassName() + " oca where oca.offerCard=:offerCard and oca.offer.description=:description and oca.offer.targetVendor.name=:targetVendorName";
		Query query = getSession().createQuery(hql);
		query.setParameter("offerCard", offerCard);
		query.setParameter("description", offerDescription);
		query.setParameter("targetVendorName", targetVendorName);
		return getSingleResultFrom(query);

	}

	@Override
	public OfferOfferCardAssoc getOfferAssocFor(OfferCard card, Offer offer) {
		String hql = "select oca from " + getEntityClassName() + " oca where " +
				" oca.offerCard=:offerCard and oca.offer=:offer ";
		Query query = getSession().createQuery(hql);
		query.setParameter("offerCard",card);
		query.setParameter("offer", offer);
		return getSingleResultFrom(query);
	}

}
