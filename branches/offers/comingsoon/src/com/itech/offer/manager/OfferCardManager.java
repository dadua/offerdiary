package com.itech.offer.manager;

import java.util.List;
import java.util.Map;

import com.itech.coupon.model.User;
import com.itech.offer.model.OfferCard;
import com.itech.offer.model.OfferCardUserAssoc;

public interface OfferCardManager {
	public List<OfferCard> getOfferCardsFor(String searchString, int maxResults);

	public List<OfferCard> getOfferCardsFor(String searchString, int maxResults, boolean excludeAssociatedCard);

	public Map<Long, OfferCardUserAssoc> getAssociationsFor(List<OfferCard> offerCards);

	public OfferCard saveOrUpdateOfferCard(OfferCard offerCard);
	public OfferCard getOfferCardFor(Long cardId);
	public OfferCard getOfferCardFor(String cardName);
	public List<OfferCard> getAssociatedOfferCardFor(User user);
	public OfferCard associateOfferCardToUser(OfferCard offerCard, User user);
	public void deAssociateOfferCardFromUser(OfferCard offerCard, User user);
}
