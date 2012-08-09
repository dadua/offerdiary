package com.itech.offer.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.offer.dao.OfferShareDAO;
import com.itech.offer.model.OfferShare;

public class OfferShareDAOImpl extends HibernateCommonBaseDAO<OfferShare> implements
OfferShareDAO {

	@Override
	protected Class getEntityClass() {
		return OfferShare.class;
	}

	@Override
	public OfferShare getOfferShareFor(String accessToken) {
		String hql = "select os from " + getEntityClassName() + " os where os.accessToken=:accessToken";
		Query query = getSession().createQuery(hql);
		query.setParameter("accessToken", accessToken);
		List<OfferShare> offerShares = query.list();
		return (offerShares.size() == 0? null : offerShares.get(0));
	}



}
