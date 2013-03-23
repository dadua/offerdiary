package com.itech.offer.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public Long getTotalOfferCardCount(SearchCriteria searchCriteria, boolean excludeAssociatedCard) {

		String searchString = searchCriteria.getSearchString();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String whereClauseForOfferCards = getWhereClauseForOfferCards(excludeAssociatedCard, searchCriteria, paramMap);
		String countHql = "select count(*) " + whereClauseForOfferCards;

		Query countQuery = getSession().createQuery(countHql);
		setParamsToQuery(countQuery, paramMap);

		Object singleResult = getSingleResult(countQuery);
		return (Long) singleResult;
	}

	@Override
	public List<OfferCard> getOfferCardsFor(SearchCriteria searchCriteria, boolean excludeAssociatedCard) {
		String searchString = searchCriteria.getSearchString();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String whereClauseForOfferCards = getWhereClauseForOfferCards(excludeAssociatedCard, searchCriteria, paramMap);

		String hql = "select oc " + whereClauseForOfferCards;

		Query query = getSession().createQuery(hql);
		setParamsToQuery(query, paramMap);

		List<OfferCard> list = getPaginatedResultFor(query, searchCriteria);
		return list;
	}

	private String getWhereClauseForOfferCards(boolean excludeAssociatedCard, SearchCriteria searchCriteria, Map<String, Object> paramMap) {
		String tokenizedCardSearchWhereClause = getTokenizedSearchWhereClause("oc.name", searchCriteria.getSearchString(), paramMap);
		String whereClause = " from " + getEntityClassName() + " oc " +
				" where " + tokenizedCardSearchWhereClause;
		if (excludeAssociatedCard) {
			whereClause += " and oc not in (select ocua.offerCard from OfferCardUserAssoc ocua where ocua.user=:user)";
			paramMap.put("user", getLoggedInUser());
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
