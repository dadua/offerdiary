package com.itech.offer.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.itech.common.db.SearchCriteria;
import com.itech.common.db.TokenizedWhereClauseResult;
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
		TokenizedWhereClauseResult tokenizedCardSearchWhereClause = getTokenizedSearchWhereClause("oc.name", searchString);
		String whereClause = getWhereClauseForOfferCards(excludeAssociatedCard, tokenizedCardSearchWhereClause.getTokenizedWhereClause());
		Query countQuery = getSession().createQuery("select count(*) " + whereClause);
		setTokenizedParamsToQuery(countQuery, tokenizedCardSearchWhereClause);
		if (excludeAssociatedCard) {
			countQuery.setParameter("user", getLoggedInUser());
		}
		Object singleResult = getSingleResult(countQuery);
		return (Long) singleResult;
	}

	@Override
	public List<OfferCard> getOfferCardsFor(SearchCriteria searchCriteria, boolean excludeAssociatedCard) {
		String searchString = searchCriteria.getSearchString();
		TokenizedWhereClauseResult tokenizedCardSearchWhereClause = getTokenizedSearchWhereClause("oc.name", searchString);
		String whereClause = getWhereClauseForOfferCards(excludeAssociatedCard, tokenizedCardSearchWhereClause.getTokenizedWhereClause());

		String hql = "select oc " + whereClause;

		Query query = getSession().createQuery(hql);
		setTokenizedParamsToQuery(query, tokenizedCardSearchWhereClause);

		if (excludeAssociatedCard) {
			query.setParameter("user", getLoggedInUser());
		}

		List<OfferCard> list = getPaginatedResultFor(query, searchCriteria);
		return list;
	}

	private String getWhereClauseForOfferCards(boolean excludeAssociatedCard, String tokenizedSearchClauseFragment) {
		String whereClause = " from " + getEntityClassName() + " oc " +
		" where " + tokenizedSearchClauseFragment;
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
