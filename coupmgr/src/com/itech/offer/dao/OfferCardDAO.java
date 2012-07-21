package com.itech.offer.dao;

import java.util.List;

import com.itech.common.db.CommonBaseDAO;
import com.itech.offer.model.OfferCard;

public interface OfferCardDAO extends CommonBaseDAO<OfferCard>{

	List<OfferCard> getOfferCardsFor(String searchString, int maxResults);

	OfferCard getByName(String cardName);

}
