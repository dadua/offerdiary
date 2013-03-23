package com.itech.offer.dao.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.itech.common.CommonUtilities;
import com.itech.common.db.OfferSearchCriteria;
import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.offer.OfferSearchFilterUtil;
import com.itech.offer.dao.OfferDAO;
import com.itech.offer.model.Offer;
import com.itech.offer.model.OfferCard;
import com.itech.offer.model.OfferUserAssoc;
import com.itech.offer.model.enums.OfferType;
import com.itech.offer.vo.OfferSearchResultVO;
import com.itech.offer.vo.OfferVO;
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
	public Offer getByUniqueId(String uniqueId) {
		String hql = "select o from " + getEntityClassName() + " o where o.uniqueId=:uniqueId";
		Query query = getSession().createQuery(hql);
		query.setParameter("uniqueId", uniqueId);
		return getSingleResultFrom(query);
	}

	@Override
	public OfferSearchResultVO searchOffersFor(OfferSearchCriteria searchCriteria) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		StringBuilder fromHql = new StringBuilder().append("from " + getEntityClassName() + " o ");

		if (searchCriteria.getPrivateSearchOnly()) {
			fromHql.append(", OfferUserAssoc oua where oua.offer=o and oua.user=:user ");
			parameterMap.put("user", getLoggedInUser());
		} else {
			fromHql.append( " where 1=1 ");
		}

		String whereClauseForFilter = getWhereClauseForFilterCriteria(searchCriteria.getUniqueFilter(), parameterMap);
		String whereClauseForSearch = getWhereClauseForFilterCriteria(searchCriteria.getSearchString(), searchCriteria.getSearchType(), searchCriteria.getQ(), parameterMap);
		String whereCluseForOfferCardSpecificSearch = getWhereCluseForOfferCardSpecificSearch(searchCriteria, parameterMap);

		fromHql.append(" and" + whereClauseForFilter);
		fromHql.append(" and" + whereClauseForSearch);
		fromHql.append(" and" + whereCluseForOfferCardSpecificSearch);

		String resultHQL = "select o " + fromHql.toString();
		String resultCountHQL = "select count(*) " + fromHql.toString();
		Query resultQuery = getSession().createQuery(resultHQL);
		Query resultCountQuery = getSession().createQuery(resultCountHQL);

		setParamsToQuery(resultQuery, parameterMap);
		setParamsToQuery(resultCountQuery, parameterMap);

		List<Offer> offers = getPaginatedResultFor(resultQuery, searchCriteria);
		fetchAndFillOfferRelationshipWithUser(offers, getLoggedInUser());
		Long resultCount = (Long) getSingleResult(resultCountQuery);

		List<OfferVO> offerVOs = new ArrayList<OfferVO>();
		for (Offer offer : offers) {
			OfferVO offerVO = OfferVO.getOfferVOFor(offer);
			offerVOs.add(offerVO);
		}
		OfferSearchResultVO offerSearchResult = new OfferSearchResultVO();
		offerSearchResult.setOffers(offerVOs);
		offerSearchResult.setTotalCount(resultCount);
		offerSearchResult.setResultsPerPage(searchCriteria.getResultsPerPage());
		offerSearchResult.setPageNumber(searchCriteria.getPageNumber());
		return offerSearchResult;
	}

	private String getWhereCluseForOfferCardSpecificSearch(
			OfferSearchCriteria searchCriteria, Map<String, Object> parameterMap) {
		String whereClause = "";
		if (searchCriteria.isCardOffersOnly()) {
			whereClause = " exists (select 1 from OfferOfferCardAssoc ooca ";
			if (searchCriteria.isOffersOnMyCardsOnly()) {
				whereClause += ", OfferCardUserAssoc ocua where ocua.user=:user and ooca.offerCard = ocua.offerCard " +
						" and ooca.offer = o";
				parameterMap.put("user", getLoggedInUser());
			} else {
				whereClause += " where ooca.offer = o ";
			}


			if (CommonUtilities.isNotEmpty(searchCriteria.getCardId())) {
				Long cardId = Long.parseLong(searchCriteria.getCardId());
				parameterMap.put("cardId", cardId);
				whereClause += " and ooca.offerCard.id =:cardId";
			}
			whereClause += " )";

			return whereClause;
		} else {
			return " 1=1 ";
		}

	}

	private String getWhereClauseForFilterCriteria(String searchString,
			String searchType, String q, Map<String, Object> parameterMap) {


		String finalSearchString = CommonUtilities.isNotEmpty(q)? q : searchString;
		if (CommonUtilities.isNullOrEmpty(finalSearchString)) {
			return " 1=1 ";
		}

		String whereClause = " (o.description like :desc or (o.targetVendor.name like :vendorName))  ";
		parameterMap.put("desc", "%" + finalSearchString + "%");
		parameterMap.put("vendorName", "%" + finalSearchString + "%");
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
			return  " (o.expiry > :now or o.expiry is null) ";
		}


		if (OfferSearchFilterUtil.isExpiryFilter(uniqueFilter)) {
			int numberOfDays = OfferSearchFilterUtil.getNumberOfDays(uniqueFilter);
			java.util.Date today = new java.util.Date(System.currentTimeMillis());
			today.setHours(0);
			today.setMinutes(0);
			Date expiryLimit = new Date(System.currentTimeMillis() + CommonUtilities.MILLIS_IN_A_DAY*numberOfDays);
			parameterMap.put("now", new Date(today.getTime()));
			parameterMap.put("expiryDate", expiryLimit);
			return  " (o.expiry >= :now and o.expiry < :expiryDate)";
		}

		if (OfferSearchFilterUtil.isCreationFilter(uniqueFilter)) {
			int numberOfDays = OfferSearchFilterUtil.getNumberOfDays(uniqueFilter);
			java.util.Date today = new java.util.Date(System.currentTimeMillis());
			today.setHours(24);
			today.setMinutes(0);
			parameterMap.put("now", new Date(today.getTime()));
			Date addedOn = new Date(System.currentTimeMillis() - CommonUtilities.MILLIS_IN_A_DAY*numberOfDays);
			parameterMap.put("creationDate", addedOn);
			return  " (oua.createdOn <= :now and oua.createdOn > :creationDate)";
		}
		return " 1=1 ";
	}

	public void fetchAndFillOfferRelationshipWithUser(List<Offer> offers, User user) {
		List<OfferUserAssoc> filteredOffers = getOffersAssociatedWithUser(offers, user);
		for (OfferUserAssoc offerUserAssoc : filteredOffers) {
			Offer offerFromAssoc = offerUserAssoc.getOffer();
			offerUserAssoc.getOffer().setAssociatedWithLoggedInUser(true);
			int positionOfOffer = offers.indexOf(offerFromAssoc);
			offers.remove(positionOfOffer);
			offers.add(positionOfOffer, offerFromAssoc);
		}
	}


	@Override
	public List<OfferUserAssoc> getOffersAssociatedWithUser(List<Offer> offers,
			User user) {
		if (offers.size() == 0) {
			return new ArrayList<OfferUserAssoc>();
		}
		String hql = "select distinct oua from " + getEntityClassName() + " o, OfferUserAssoc oua  where oua.offer=o and oua.user=:user and o in (:offers)";
		Query query = getSession().createQuery(hql);
		query.setParameter("user", user);
		query.setParameterList("offers", offers);
		List list = query.list();
		return list;
	}

	@Override
	public List<Offer> getByUniqueId(List<String> offerUniqueIds) {
		String hql = "select o from " + getEntityClassName() + " o where o.uniqueId in (:uniqueIds)";
		Query query = getSession().createQuery(hql);
		query.setParameterList("uniqueIds", offerUniqueIds);
		return query.list();
	}




}
