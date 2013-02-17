package com.itech.offer.dao;

import java.util.List;

import com.itech.common.db.CommonBaseDAO;
import com.itech.common.db.SearchCriteria;
import com.itech.offer.model.Offer;
import com.itech.offer.model.OfferCard;
import com.itech.offer.model.enums.OfferType;
import com.itech.offer.vo.OfferSearchResultVO;
import com.itech.user.model.User;

public interface OfferDAO extends CommonBaseDAO<Offer>{

	List<Offer> getAllOfferForUser(User user);

	List<Offer> getAllUnexpiredOffersForUser(User user);

	List<Offer> getAllOfferForUser(User user, OfferType offerType);

	void removeOffersForCard(OfferCard offerCard);

	List<Offer> getAllOffersOnCardsForUser(User user);

	List<Offer> getAllOffersForCard(OfferCard offerCard);

	OfferSearchResultVO searchOffersFor(SearchCriteria searchCriteria);

	Offer getByUniqueId(String uniqueId);



}
