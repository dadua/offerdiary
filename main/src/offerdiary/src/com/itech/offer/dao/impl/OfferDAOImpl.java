package com.itech.offer.dao.impl;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;

import com.itech.common.CommonUtilities;
import com.itech.common.db.SearchCriteria;
import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.offer.dao.OfferDAO;
import com.itech.offer.model.Offer;
import com.itech.offer.model.OfferCard;
import com.itech.offer.model.enums.OfferType;
import com.itech.offer.vo.OfferSearchResultVO;
import com.itech.user.model.User;

public class OfferDAOImpl extends HibernateCommonBaseDAO<Offer> implements OfferDAO{

	@Override
	protected Class getEntityClass() {
		return Offer.class;
	}

	@Override
	public List<Offer> getAllOfferForUser(User user) {
		String hql = "select o from " + getEntityClassName() + " o, OfferUserAssoc oua  where oua.offer=o and oua.user=:user";
		Query query = getSession().createQuery(hql);
		query.setParameter("user", user);
		List list = query.list();
		return list;
	}

	@Override
	public List<Offer> getAllUnexpiredOffersForUser(User user) {
		String hql = "select o from " + getEntityClassName() + " o, OfferUserAssoc oua  where oua.offer=o and oua.user=:user and o.expiry < :now";
		Query query = getSession().createQuery(hql);
		query.setParameter("user", user);
		query.setParameter("now", new Date(System.currentTimeMillis()));
		List list = query.list();
		return list;
	}

	@Override
	public List<Offer> getAllOfferForUser(User user, OfferType offerType) {
		String hql = "select o from " + getEntityClassName() + " o, OfferUserAssoc oua  where oua.offer=o and oua.user=:user and offer.offerType=:offerType";
		Query query = getSession().createQuery(hql);
		query.setParameter("user", user);
		query.setParameter("offerType", offerType);
		List list = query.list();
		return list;
	}

	@Override
	public void removeOffersForCard(OfferCard offerCard) {
		String hql = "delete from " + getEntityClassName() + " o where exists " +
				" (select 1 from OfferOfferCardAssoc oca where oca.offer=o and oca.offerCard=:offerCard";
		Query query = getSession().createQuery(hql);
		query.setParameter("offerCard", offerCard);
		int rowsAffected = query.executeUpdate();
	}

	@Override
	public List<Offer> getAllOffersOnCardsForUser(User user) {
		String hql = "select o from " + getEntityClassName() + " o, OfferOfferCardAssoc oca, OfferCardUserAssoc ocua " +
				" where oca.offer=o and oca.offerCard=ocua.offerCard and ocua.user=:user";
		Query query = getSession().createQuery(hql);
		query.setParameter("user", user);
		List list = query.list();
		return list;
	}

	@Override
	public List<Offer> getAllOffersForCard(OfferCard offerCard) {
		String hql = "select o from " + getEntityClassName() + " o, OfferOfferCardAssoc oca " +
				" where oca.offer=o and oca.offerCard=:offerCard";
		Query query = getSession().createQuery(hql);
		query.setParameter("offerCard", offerCard);
		List list = query.list();
		return list;
	}

	@Override
	public OfferSearchResultVO searchOffersFor(SearchCriteria searchCriteria) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		String fromHql = "from " + getEntityClassName() + " o, OfferUserAssoc oua  where oua.offer=o and oua.user=:user ";
		parameterMap.put("user", getLoggedInUser());
		String whereClauseForFilter = getWhereClauseForFilterCriteria(searchCriteria.getUniqueFilter(), parameterMap);
		String whereClauseForSearch = getWhereClauseForFilterCriteria(searchCriteria.getSearchString(), searchCriteria.getSearchType(), searchCriteria.getQ(), parameterMap);
		fromHql += "and" + whereClauseForFilter;
		fromHql += "and" + whereClauseForSearch;

		String resultHQL = "select o " + fromHql;
		String resultCountHQL = "select count(*) " + fromHql;
		Query resultQuery = getSession().createQuery(resultHQL);
		Query resultCountQuery = getSession().createQuery(resultCountHQL);

		for (Entry<String, Object> param : parameterMap.entrySet()) {
			resultQuery.setParameter(param.getKey(), param.getValue());
			resultCountQuery.setParameter(param.getKey(), param.getValue());
		}


		List<Offer> offers = getPaginatedResultFor(resultQuery, searchCriteria);
		Long resultCount = (Long) getSingleResult(resultCountQuery);

		OfferSearchResultVO offerSearchResult = new OfferSearchResultVO();
		offerSearchResult.setOffers(offers);
		offerSearchResult.setTotalCount(resultCount);
		offerSearchResult.setPerPageCount(searchCriteria.getResultsPerPage());
		return offerSearchResult;
	}

	private String getWhereClauseForFilterCriteria(String searchString,
			String searchType, String q, Map<String, Object> parameterMap) {


		String finalSearchString = CommonUtilities.isNotEmpty(q)? q : searchString;
		if (CommonUtilities.isNullOrEmpty(finalSearchString)) {
			return " 1=1 ";
		}

		String whereClause = " o.description like :desc ";
		parameterMap.put("desc", "%" + finalSearchString + "%");
		return whereClause;
	}

	private String getWhereClauseForFilterCriteria(String uniqueFilter, Map<String, Object> parameterMap) {
		if ("expired".equalsIgnoreCase(uniqueFilter)) {
			parameterMap.put("now", new Date(System.currentTimeMillis()));
			return  " o.expiry < :now ";
		}

		if ("all".equalsIgnoreCase(uniqueFilter)) {
			return " 1=1 ";
		}

		if ("valid".equalsIgnoreCase(uniqueFilter)) {
			parameterMap.put("now", new Date(System.currentTimeMillis()));
			return  " o.expiry > :now ";
		}


		if ("expiringIn7Days".equalsIgnoreCase(uniqueFilter)) {
			parameterMap.put("now", new Date(System.currentTimeMillis()));
			parameterMap.put("next7Days", new Date(System.currentTimeMillis() + 24*60*60*1000*7));
			return  " (o.expiry > :now and o.expiry < :next7Days)";
		}

		if ("addedInLast7Days".equalsIgnoreCase(uniqueFilter)) {
			parameterMap.put("now", new Date(System.currentTimeMillis()));
			parameterMap.put("next7Days", new Date(System.currentTimeMillis() - 24*60*60*1000*7));
			return  " (o.createdOn <= :now and o.createdOn > :next7Days)";
		}
		return " 1=1 ";
	}

}
