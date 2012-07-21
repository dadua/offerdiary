package com.itech.offer.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.coupon.model.User;
import com.itech.offer.dao.OfferCardDAO;
import com.itech.offer.model.OfferCard;

public class OfferCardDAOImpl extends HibernateCommonBaseDAO<OfferCard> implements OfferCardDAO{

	@Override
	protected Class getEntityClass() {
		return OfferCard.class;
	}

	@Override
	public List<OfferCard> getOfferCardsFor(String searchString, int maxResults) {
		String hql = "from " + getEntityClassName() + " where  name like :cardName order by name";
		Query query = getSession().createQuery(hql);
		query.setParameter("cardName", "%" + searchString + "%");
		if (maxResults != 0) {
			query.setMaxResults(maxResults);
		}
		List<OfferCard> list = query.list();
		return list;
	}

	@Override
	public OfferCard getByName(String cardName) {
		String hql = "from " + getEntityClassName() + " where  name=:cardName ";
		Query query = getSession().createQuery(hql);
		query.setParameter("cardName", cardName);
		List<OfferCard> list = query.list();
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public List<OfferCard> getAllAssociatedCardsForUser(User user) {
		String hql = "select oc from " + getEntityClassName() + " o, OfferCardUserAssoc ocua  where ocua.offer=oc and ocua.user=:user";
		Query query = getSession().createQuery(hql);
		query.setParameter("user", user);
		List list = query.list();
		return list;
	}


}
