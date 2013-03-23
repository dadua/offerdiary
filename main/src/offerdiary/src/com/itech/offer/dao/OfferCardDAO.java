package com.itech.offer.dao;

import java.util.List;

import com.itech.common.db.CommonBaseDAO;
import com.itech.common.db.SearchCriteria;
import com.itech.offer.model.OfferCard;
import com.itech.user.model.User;

public interface OfferCardDAO extends CommonBaseDAO<OfferCard>{

	OfferCard getByName(String cardName);

	List<OfferCard> getAllAssociatedCardsForUser(User user);

	List<OfferCard> getOfferCardsFor(SearchCriteria searchCriteria,
			boolean excludeAssociatedCard);

	Long getTotalOfferCardCount(SearchCriteria searchCriteria,
			boolean excludeAssociatedCard);

}
