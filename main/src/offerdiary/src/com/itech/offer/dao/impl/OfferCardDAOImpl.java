package com.itech.offer.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.itech.common.db.SearchCriteria;
import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.offer.dao.OfferCardDAO;
import com.itech.offer.model.OfferCard;
import com.itech.user.model.User;

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
	public List<OfferCard> getOfferCardsFor(String searchString,
			int maxResults, boolean excludeAssociatedCard) {
		String hql = "select oc from " + getEntityClassName() + " oc " +
		" where  oc.name like :cardName ";
		if (excludeAssociatedCard) {
			hql += " and oc not in (select ocua.offerCard from OfferCardUserAssoc ocua where ocua.user=:user)";
		}
		hql += " order by name";
		Query query = getSession().createQuery(hql);
		query.setParameter("cardName", "%" + searchString + "%");
		if (excludeAssociatedCard) {
			query.setParameter("user", getLoggedInUser());
		}
		if (maxResults != 0) {
			query.setMaxResults(maxResults);
		}
		List<OfferCard> list = query.list();
		return list;
	}


	@Override
	public Long getTotalOfferCardCount(SearchCriteria searchCriteria, boolean excludeAssociatedCard) {

		String whereClause = getWhereClauseForOfferCards(excludeAssociatedCard);
		Query countQuery = getSession().createQuery("select count(*) " + whereClause);
		countQuery.setParameter("cardName", "%" + searchCriteria.getSearchString() + "%");
		if (excludeAssociatedCard) {
			countQuery.setParameter("user", getLoggedInUser());
		}
		Object singleResult = getSingleResult(countQuery);
		return (Long) singleResult;
	}

	@Override
	public List<OfferCard> getOfferCardsFor(SearchCriteria searchCriteria, boolean excludeAssociatedCard) {
		String searchString = searchCriteria.getSearchString();
		String whereClause = getWhereClauseForOfferCards(excludeAssociatedCard);

		String hql = "select oc " + whereClause;

		Query query = getSession().createQuery(hql);
		query.setParameter("cardName", "%" + searchString + "%");
		if (excludeAssociatedCard) {
			query.setParameter("user", getLoggedInUser());
		}

		List<OfferCard> list = getPaginatedResultFor(query, searchCriteria);
		return list;
	}

	private String getWhereClauseForOfferCards(boolean excludeAssociatedCard) {
		String whereClause = " from " + getEntityClassName() + " oc " +
		" where  oc.name like :cardName ";
		if (excludeAssociatedCard) {
			whereClause += " and oc not in (select ocua.offerCard from OfferCardUserAssoc ocua where ocua.user=:user)";
		}
		whereClause += " order by name";
		return whereClause;
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
		String hql = "select oc from " + getEntityClassName() + " oc, OfferCardUserAssoc ocua  where ocua.offerCard=oc and ocua.user=:user";
		Query query = getSession().createQuery(hql);
		query.setParameter("user", user);
		List list = query.list();
		return list;
	}




}
