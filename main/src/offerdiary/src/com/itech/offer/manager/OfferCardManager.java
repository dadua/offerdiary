package com.itech.offer.manager;

import java.util.List;
import java.util.Map;

import com.itech.common.db.SearchCriteria;
import com.itech.offer.model.OfferCard;
import com.itech.offer.model.OfferCardUserAssoc;
import com.itech.offer.vo.OfferCardVO;
import com.itech.user.model.User;

public interface OfferCardManager {

	public Map<Long, OfferCardUserAssoc> getAssociationsFor(List<OfferCard> offerCards);

	public OfferCard saveOrUpdateOfferCard(OfferCard offerCard);
	public OfferCard getOfferCardFor(Long cardId);
	public OfferCard getOfferCardFor(String cardName);
	public List<OfferCard> getAssociatedOfferCardFor(User user);
	public OfferCard associateOfferCardToUser(OfferCard offerCard, User user);
	public void deAssociateOfferCardFromUser(OfferCard offerCard, User user);

	public List<OfferCard> getOfferCardsFor(SearchCriteria searchCriteria,
			boolean excludeAssociatedCard);


	public OfferCardVO getOfferCardVOsFor(SearchCriteria searchCriteria, boolean excludeAssociatedCard);

	public List<OfferCard> getOfferCardsByProvider(String providerName);
}
