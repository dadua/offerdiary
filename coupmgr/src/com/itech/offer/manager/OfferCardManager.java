package com.itech.offer.manager;

import java.util.List;

import com.itech.offer.model.OfferCard;

public interface OfferCardManager {
	public List<OfferCard> getOfferCardsFor(String searchString, int maxResults);
	public OfferCard saveOrUpdateOfferCard(OfferCard offerCard);
	public OfferCard getOfferCardFor(Long cardId);
	public OfferCard getOfferCardFor(String cardName);

}
