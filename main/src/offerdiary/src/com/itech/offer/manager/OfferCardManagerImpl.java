package com.itech.offer.manager;

import java.util.List;
import java.util.Map;

import com.itech.common.db.SearchCriteria;
import com.itech.common.services.CommonBaseManager;
import com.itech.offer.dao.OfferCardDAO;
import com.itech.offer.dao.OfferCardUserAssocDAO;
import com.itech.offer.model.OfferCard;
import com.itech.offer.model.OfferCardUserAssoc;
import com.itech.offer.vo.OfferCardVO;
import com.itech.user.model.User;

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
	public OfferCard saveOrUpdateOfferCard(OfferCard offerCard) {
		if (offerCard.getId() == null) {
			OfferCard existingCardInDb = getOfferCardDAO().getByName(offerCard.getName());
			if (existingCardInDb != null) {
				return existingCardInDb;
			}
		}
		getOfferCardDAO().addOrUpdate(offerCard);
		return offerCard;
	}

	@Override
	public List<OfferCard> getAssociatedOfferCardFor(User user) {
		List<OfferCard> allAssociatedCardsForUser = getOfferCardDAO().getAllAssociatedCardsForUser(user);
		return allAssociatedCardsForUser;
	}

	@Override
	public OfferCard associateOfferCardToUser(OfferCard offerCard, User user) {
		OfferCard cardFromDb = getOfferCardFor(offerCard.getId());
		OfferCardUserAssoc cardUserAssoc = getOfferCardUserAssocDAO().getAssocFor(cardFromDb, user);
		if (cardUserAssoc != null) {
			return null;
		}
		OfferCardUserAssoc offerCardUserAssoc = new OfferCardUserAssoc();
		offerCardUserAssoc.setOfferCard(cardFromDb);
		offerCardUserAssoc.setUser(user);
		getOfferCardUserAssocDAO().addOrUpdate(offerCardUserAssoc);
		return cardFromDb;
	}

	@Override
	public void deAssociateOfferCardFromUser(OfferCard offerCard, User user) {
		OfferCardUserAssoc cardUserAssoc = getOfferCardUserAssocDAO().getAssocFor(offerCard, user);
		if (cardUserAssoc == null) {
			throw new RuntimeException("No associated card found for user");
		}
		getOfferCardUserAssocDAO().delete(cardUserAssoc);
	}


	@Override
	public Map<Long, OfferCardUserAssoc> getAssociationsFor(
			List<OfferCard> offerCards) {
		return getOfferCardUserAssocDAO().getAssociationsFor(offerCards);
	}

	@Override
	public List<OfferCard> getOfferCardsFor(SearchCriteria searchCriteria, boolean excludeAssociatedCard) {
		return getOfferCardDAO().getOfferCardsFor(searchCriteria, excludeAssociatedCard);
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

	@Override
	public OfferCardVO getOfferCardVOsFor(SearchCriteria searchCriteria,
			boolean excludeAssociatedCard) {
		List<OfferCard> offerCardsFor = getOfferCardsFor(searchCriteria, excludeAssociatedCard);
		Long totalOfferCardCount = getOfferCardDAO().getTotalOfferCardCount(searchCriteria, excludeAssociatedCard);

		OfferCardVO offerCardVO = new OfferCardVO();
		offerCardVO.setCards(offerCardsFor);
		offerCardVO.setTotalCount(totalOfferCardCount);
		offerCardVO.setPerPageCount(searchCriteria.getResultsPerPage());
		return offerCardVO;
	}
}
