package com.itech.redwine.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.itech.common.db.hibernate.HibernateSessionFactory;
import com.itech.common.services.Initialize;
import com.itech.config.ProjectConfigs;
import com.itech.offer.manager.OfferCardManager;
import com.itech.offer.manager.OfferManager;
import com.itech.offer.model.Offer;
import com.itech.offer.model.OfferCard;
import com.itech.offer.model.Vendor;
import com.itech.offer.model.OfferCard.CardSource;
import com.itech.offer.model.enums.VendorType;

public class RedWineOfferEngine implements Initialize{
	private OfferCardManager offerCardManager;
	private OfferManager offerManager;
	private HibernateSessionFactory hibernateSessionFactory;
	private static final Logger logger = Logger.getLogger(RedWineOfferEngine.class);

	@Override
	public void init() {
		if (!ProjectConfigs.initializeRedWineData.get()) {
			logger.info("skipping redwine data initialization.");
			return;
		}
		logger.info("initializing redwine data");
		String sourceFileName = "resources\\redanar\\cards-minimal.json";
		String cardsWithOffersSourceFileName = "resources\\redanar\\cards-with-offers.json";
		List<RedWineCard> redWineCards = RedWineCardsParser.readRedWineRecordsFromFile(sourceFileName);
		List<RedWineCard> redWineCardsWithOffers = RedWineCardsParser.readRedWineCardsWithOfferFile(cardsWithOffersSourceFileName);
		Map<String, OfferCard> redwineCardToODCardMap =  new HashMap<String, OfferCard>();
		for (RedWineCard redWineCard : redWineCards) {
			OfferCard offerCard = getOfferCardFrom(redWineCard);
			OfferCard existingCardInDb = getOfferCardManager().getOfferCardFor(offerCard.getName());
			if (existingCardInDb != null) {
				redwineCardToODCardMap.put(redWineCard.getCardId(), existingCardInDb);
				logger.warn("Offer card already exists for name - " + offerCard.getName());
				continue;
			}
			getOfferCardManager().saveOrUpdateOfferCard(offerCard);
			redwineCardToODCardMap.put(redWineCard.getCardId(), offerCard);
		}

		getHibernateSessionFactory().commitCurrentTransaction();

		for (RedWineCard redWineCard : redWineCardsWithOffers) {
			OfferCard offerCard = redwineCardToODCardMap.get(redWineCard.getCardId());
			List<Offer> offers = getOffersFrom(redWineCard.getOffers());
			getOfferManager().addOffersForCard(offers, offerCard);
			getHibernateSessionFactory().commitCurrentTransaction();
		}



	}

	private List<Offer> getOffersFrom(List<RedWineOffer> redWineOffers) {
		List<Offer> offers = new ArrayList<Offer>();
		for (RedWineOffer redWineOffer : redWineOffers) {
			Offer offer = new Offer();
			offer.setDescription(redWineOffer.getDescription());
			Vendor vendor = new Vendor();
			vendor.setName(redWineOffer.getMerchantName());
			vendor.setVendorType(VendorType.GLOBAL);
			offer.setTargetVendor(vendor);
			offers.add(offer);
		}
		return offers;
	}

	private OfferCard getOfferCardFrom(RedWineCard redWineCard) {
		OfferCard offerCard = new OfferCard();
		offerCard.setName(redWineCard.getCardName());
		String cardId = RedWineParserUtil.getIdFromCard(redWineCard.getCardUrl());
		offerCard.setCardSource(CardSource.REDWINE);
		offerCard.setCardSourceIdentifier(cardId);
		return offerCard;
	}

	public void setOfferCardManager(OfferCardManager offerCardManager) {
		this.offerCardManager = offerCardManager;
	}

	public OfferCardManager getOfferCardManager() {
		return offerCardManager;
	}

	public void setHibernateSessionFactory(
			HibernateSessionFactory hibernateSessionFactory) {
		this.hibernateSessionFactory = hibernateSessionFactory;
	}

	public HibernateSessionFactory getHibernateSessionFactory() {
		return hibernateSessionFactory;
	}

	public void setOfferManager(OfferManager offerManager) {
		this.offerManager = offerManager;
	}

	public OfferManager getOfferManager() {
		return offerManager;
	}

}
