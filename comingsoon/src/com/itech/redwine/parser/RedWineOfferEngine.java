package com.itech.redwine.parser;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itech.common.CommonFileUtilities;
import com.itech.common.db.hibernate.HibernateSessionFactory;
import com.itech.common.services.Initialize;
import com.itech.offer.manager.OfferCardManager;
import com.itech.offer.model.OfferCard;

public class RedWineOfferEngine implements Initialize{
	private OfferCardManager offerCardManager;
	private HibernateSessionFactory hibernateSessionFactory;
	private static final Logger logger = Logger.getLogger(RedWineOfferEngine.class);

	@Override
	public void init() {

		try {
			getHibernateSessionFactory().openNewSession();
			String cardDataJson = CommonFileUtilities.getResourceFileAsString("resources\\redanar\\cards-minimal.json");
			Gson gson = new Gson();
			Type offerCardJsonType = new TypeToken<List<RedWineCard>>() { }.getType();
			List<RedWineCard> redWineCards = gson.fromJson(cardDataJson, offerCardJsonType);
			for (RedWineCard redWineCard : redWineCards) {
				OfferCard offerCard = getOfferCardFrom(redWineCard);
				OfferCard existingCardInDb = getOfferCardManager().getOfferCardFor(offerCard.getName());
				if (existingCardInDb != null) {
					logger.warn("Offer card already exists for name - " + offerCard.getName());
					continue;
				}
				getOfferCardManager().saveOrUpdateOfferCard(offerCard);
			}
		} finally {
			getHibernateSessionFactory().commitCurrentTransaction();
			getHibernateSessionFactory().closeCurrentSession();
		}


	}

	private OfferCard getOfferCardFrom(RedWineCard redWineCard) {
		OfferCard offerCard = new OfferCard();
		offerCard.setName(redWineCard.getCardName());
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

}
