package com.itech.offer.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.coupon.model.User;
import com.itech.offer.dao.OfferCardUserAssocDAO;
import com.itech.offer.model.OfferCard;
import com.itech.offer.model.OfferCardUserAssoc;

public class OfferCardUserAssocDAOImpl extends HibernateCommonBaseDAO<OfferCardUserAssoc> implements OfferCardUserAssocDAO {

	@Override
	protected Class getEntityClass() {
		return OfferCardUserAssoc.class;
	}

	@Override
	public OfferCardUserAssoc getAssocFor(OfferCard offerCard, User user) {
		String hql="from "+getEntityClassName()+" where offerCard =:offerCard and user =:user ";
		Query query = getSession().createQuery(hql);
		query.setParameter("offerCard", offerCard);
		query.setParameter("user", user);
		List<OfferCardUserAssoc> offerCardUserAssocList = query.list();
		if(!offerCardUserAssocList.isEmpty()){
			return offerCardUserAssocList.get(0);
		}
		return null;
	}

	@Override
	public Map<Long, OfferCardUserAssoc> getAssociationsFor(
			List<OfferCard> offerCards) {
		String hql="select ocua from "+getEntityClassName()+" ocua where ocua.offerCard in (:offerCards) ";
		Query query = getSession().createQuery(hql);
		query.setParameterList("offerCards", offerCards);
		List<OfferCardUserAssoc> offerCardUserAssocList = query.list();
		Map<Long, OfferCardUserAssoc> cardAssocMap = new HashMap<Long, OfferCardUserAssoc>();
		for (OfferCardUserAssoc cardUserAssoc : offerCardUserAssocList) {
			cardAssocMap.put(cardUserAssoc.getOfferCard().getId(), cardUserAssoc);
		}
		return cardAssocMap;
	}

}
