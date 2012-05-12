package com.itech.offer.dao.impl;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.offer.dao.OfferDAO;
import com.itech.offer.model.Offer;

public class OfferDAOImpl extends HibernateCommonBaseDAO<Offer> implements OfferDAO{

	@Override
	protected Class getEntityClass() {
		return Offer.class;
	}

}
