package com.itech.offer.dao.impl;

import org.hibernate.Query;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.offer.dao.OfferOfferCardAssocDAO;
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

}
