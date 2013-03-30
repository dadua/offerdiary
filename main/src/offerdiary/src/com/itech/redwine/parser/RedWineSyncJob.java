package com.itech.redwine.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.itech.offer.job.BaseItechJob;
import com.itech.offer.job.JobEventListener;
import com.itech.offer.job.JobStatus;
import com.itech.offer.job.JobStatus.JobStatusEnum;
import com.itech.offer.manager.OfferCardManager;
import com.itech.offer.manager.OfferManager;
import com.itech.offer.model.Offer;
import com.itech.offer.model.OfferCard;
import com.itech.offer.model.OfferCard.CardSource;
import com.itech.offer.model.Vendor;
import com.itech.offer.model.enums.VendorType;

@Component
@Scope("prototype")
public class RedWineSyncJob  extends BaseItechJob{

	@Autowired
	private OfferCardManager offerCardManager;

	@Autowired
	private OfferManager offerManager;

	private static final Logger logger = Logger.getLogger(RedWineSyncJob.class);


	private void syncRedWineData() {
		logger.info("initializing redwine data");
		String sourceFileName = "resources\\redanar\\cards-minimal.json";
		String cardsWithOffersSourceFileName = "resources\\redanar\\cards-with-offers.json";
		List<RedWineCard> redWineCards = RedWineCardsParser.readRedWineRecordsFromFile(sourceFileName);
		List<RedWineCard> redWineCardsWithOffers = RedWineCardsParser.readRedWineCardsWithOfferFile(cardsWithOffersSourceFileName);
		Map<String, OfferCard> redwineCardToODCardMap =  new HashMap<String, OfferCard>();
		for (RedWineCard redWineCard : redWineCards) {
			synchronized (this) {
				if (this.stopped) {
					break;
				}
			}

			synchronized (this) {
				if (this.paused) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						logger.error("error in wait", e);
					}
				}
			}
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


	public void setOfferManager(OfferManager offerManager) {
		this.offerManager = offerManager;
	}

	public OfferManager getOfferManager() {
		return offerManager;
	}

	@Override
	public void doJobTask() {
		syncRedWineData();
	}

	@Override
	public void stop() {
		synchronized (this) {
			this.stopped = true;
			this.notifyAll();
		}
	}

	@Override
	public void pause() {
		synchronized (this) {
			this.paused = true;
			this.notifyAll();
		}
	}

	@Override
	public void resume() {
		synchronized (this) {
			this.paused = false;
			this.notifyAll();
		}
	}

	@Override
	public JobStatus getJobStatus() {
		if (stopped) {
			return new JobStatus(JobStatusEnum.STOPPED);
		}

		if (paused) {
			return new JobStatus(JobStatusEnum.PAUSED);
		}
		return new JobStatus(JobStatusEnum.RUNNING);
	}

	@Override
	public void addJobEventListener(JobEventListener eventListener) {
		// TODO Auto-generated method stub

	}

}
