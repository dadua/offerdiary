package com.itech.offer.dao.impl;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.offer.dao.OfferCardUserAssocDAO;
import com.itech.offer.model.OfferCardUserAssoc;

public class OfferCardUserAssocDAOImpl extends HibernateCommonBaseDAO<OfferCardUserAssoc> implements OfferCardUserAssocDAO {

	@Override
	protected Class getEntityClass() {
		return OfferCardUserAssoc.class;
	}

}
