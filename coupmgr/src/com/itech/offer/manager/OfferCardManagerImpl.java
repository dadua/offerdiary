package com.itech.offer.manager;

import java.util.List;

import com.itech.common.services.CommonBaseManager;
import com.itech.coupon.model.User;
import com.itech.offer.dao.OfferCardDAO;
import com.itech.offer.dao.OfferCardUserAssocDAO;
import com.itech.offer.model.OfferCard;
import com.itech.offer.model.OfferCardUserAssoc;

public class OfferCardManagerImpl extends CommonBaseManager implements
OfferCardManager {

	private OfferCardDAO offerCardDAO;
	private OfferCardUserAssocDAO offerCardUserAssocDAO;

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

	@Override
	public List<OfferCard> getAssociatedOfferCardFor(User user) {
		return getOfferCardDAO().getAllAssociatedCardsForUser(user);
	}

	@Override
	public void associateOfferCardToUser(OfferCard offerCard, User user) {
		OfferCardUserAssoc offerCardUserAssoc = new OfferCardUserAssoc();
		offerCardUserAssoc.setOfferCard(offerCard);
		offerCardUserAssoc.setUser(user);
		getOfferCardUserAssocDAO().addOrUpdate(offerCardUserAssoc);
	}

	public void setOfferCardDAO(OfferCardDAO offerCardDAO) {
		this.offerCardDAO = offerCardDAO;
	}

	public OfferCardDAO getOfferCardDAO() {
		return offerCardDAO;
	}

	public void setOfferCardUserAssocDAO(OfferCardUserAssocDAO offerCardUserAssocDAO) {
		this.offerCardUserAssocDAO = offerCardUserAssocDAO;
	}

	public OfferCardUserAssocDAO getOfferCardUserAssocDAO() {
		return offerCardUserAssocDAO;
	}


}
