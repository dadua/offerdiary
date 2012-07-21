package com.itech.offer.manager;

import java.util.List;

import com.itech.common.services.CommonBaseManager;
import com.itech.offer.dao.OfferCardDAO;
import com.itech.offer.model.OfferCard;

public class OfferCardManagerImpl extends CommonBaseManager implements
OfferCardManager {

	private OfferCardDAO offerCardDAO;

	@Override
	public OfferCard getOfferCardFor(Long cardId) {
		return getOfferCardDAO().getById(cardId);
	}

	@Override
	public OfferCard getOfferCardFor(String cardName) {
		return getOfferCardDAO().getByName(cardName);
	}

	@Override
	public List<OfferCard> getOfferCardsFor(String searchString, int maxResults) {
		return getOfferCardDAO().getOfferCardsFor(searchString, maxResults);
	}

	@Override
	public OfferCard saveOrUpdateOfferCard(OfferCard offerCard) {
		getOfferCardDAO().addOrUpdate(offerCard);
		return offerCard;
	}

	public void setOfferCardDAO(OfferCardDAO offerCardDAO) {
		this.offerCardDAO = offerCardDAO;
	}

	public OfferCardDAO getOfferCardDAO() {
		return offerCardDAO;
	}

}
