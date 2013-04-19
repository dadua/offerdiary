package com.itech.redwine.parser;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.itech.common.CommonFileUtilities;
import com.itech.common.CommonUtilities;
import com.itech.offer.job.BaseItechJob;
import com.itech.offer.job.JobEventListener;
import com.itech.offer.manager.OfferCardManager;
import com.itech.offer.manager.OfferManager;
import com.itech.offer.model.Offer;
import com.itech.offer.model.OfferCard;
import com.itech.offer.model.OfferCard.CardSource;
import com.itech.offer.model.Vendor;
import com.itech.offer.model.Vendor.SourceType;
import com.itech.offer.model.enums.VendorType;

@Component
@Scope("prototype")
public class RedWineSyncJob  extends BaseItechJob{

	private static final String DEFAULT_BANK_IMAGE_URL = "DEFAULT_BANK.jpg";
	private static final String REDANAR_CARDDATA_JSON_FILE_OUT = "resources\\redanar\\cards-minimal.json";
	private static final String ALL_REDANAR_CARDDATA_JSON_FILE_OUT = "resources\\redanar\\all-cards-minimal.json";
	private static final String REDANAR_CARDS_WITH_OFFERS_BASE_DIR = "resources\\redanar\\offers";
	private static final String CARD_PROVIDER_MAPPINGS = "resources\\redanar\\card-providers.properties";

	@Autowired
	private OfferCardManager offerCardManager;

	@Autowired
	private OfferManager offerManager;

	private final Map<String, String[]> cardProviderKeyToMatchingStringsMap = new HashMap<String, String[]>();

	private static final Logger logger = Logger.getLogger(RedWineSyncJob.class);

	private boolean syncCards = true;
	private boolean syncOfferOnCards = true;

	public void setFullSync() {
		this.syncCards = true;
		this.syncOfferOnCards = true;
	}

	private void syncRedWineData() {
		logger.info("initializing redwine data");
		String sourceFileName = REDANAR_CARDDATA_JSON_FILE_OUT;
		loadInitialData();
		List<RedWineCard> redWineCards = RedWineCardsParser.readRedWineRecordsFromFile(sourceFileName, false);
		Map<String, OfferCard> redwineCardToODCardMap =  new HashMap<String, OfferCard>();
		for (RedWineCard redWineCard : redWineCards) {
			logger.info("Processing card: " + redWineCard);
			synchronized (this) {
				if (stopped) {
					break;
				}
			}

			synchronized (this) {
				if (paused) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						logger.error("error in wait", e);
					}
				}
			}
			OfferCard offerCard = getOfferCardFrom(redWineCard);
			updateProviderInformation(offerCard);
			OfferCard existingCardInDb = getOfferCardManager().getOfferCardFor(offerCard.getName());
			if (existingCardInDb != null) {
				existingCardInDb.setProvider(offerCard.getProvider());
				existingCardInDb.setProviderImgUrl(offerCard.getProviderImgUrl());
				existingCardInDb.setCardType(offerCard.getCardType());
				existingCardInDb.setPaymentChannel(offerCard.getPaymentChannel());
				getOfferCardManager().saveOrUpdateOfferCard(existingCardInDb);
				redwineCardToODCardMap.put(redWineCard.getCardId(), existingCardInDb);
				logger.warn("Offer card already exists for name - " + offerCard.getName());
				continue;
			}
			getOfferCardManager().saveOrUpdateOfferCard(offerCard);
			redwineCardToODCardMap.put(redWineCard.getCardId(), offerCard);
		}

		getHibernateSessionFactory().commitCurrentTransaction();

		if (syncOfferOnCards) {
			for (RedWineCard redWineCard : redWineCards) {
				RedWineCard redWineCardWithOffers = RedWineCardsParser.readRedWineRecordsForCardId(redWineCard.getCardId(), REDANAR_CARDS_WITH_OFFERS_BASE_DIR, false);
				if (redWineCardWithOffers == null) {
					continue;
				}
				OfferCard offerCard = redwineCardToODCardMap.get(redWineCardWithOffers.getCardId());
				List<Offer> offers = getOffersFrom(redWineCardWithOffers.getOffers());
				getOfferManager().addOffersForCard(offers, offerCard);
				getHibernateSessionFactory().commitCurrentTransaction();
			}
		}
	}

	private void updateProviderInformation(OfferCard offerCard) {
		String cardName = offerCard.getName().toLowerCase();
		for (Entry<String, String[]> cardProviderEntry : cardProviderKeyToMatchingStringsMap.entrySet()) {
			String providerKey = cardProviderEntry.getKey();
			String[] matchStrings = cardProviderEntry.getValue();
			for (String matchString : matchStrings) {
				if (cardName.startsWith(matchString + " ") || cardName.contains(" " + matchString + " ")) {
					offerCard.setProvider(providerKey);
					offerCard.setProviderImgUrl(providerKey + ".jpg");
					return;
				}
			}
		}
		if (CommonUtilities.isNullOrEmpty(offerCard.getProvider())) {
			offerCard.setProviderImgUrl(DEFAULT_BANK_IMAGE_URL);
		}
	}

	private void loadInitialData() {
		InputStream providerKeyToMatchingStringIS = null;
		try {
			providerKeyToMatchingStringIS = CommonFileUtilities.class.getClassLoader().getResourceAsStream(CARD_PROVIDER_MAPPINGS);
			Properties providerKeyToMatchingStringProperties = new Properties();
			providerKeyToMatchingStringProperties.load(providerKeyToMatchingStringIS);
			Set<Entry<Object, Object>> entrySet = providerKeyToMatchingStringProperties.entrySet();
			for (Entry<Object, Object> entry : entrySet) {
				String matchStrings = (String) entry.getValue();
				String[] matchStringsArray = matchStrings.toLowerCase().split(",");
				cardProviderKeyToMatchingStringsMap.put((String)entry.getKey(), matchStringsArray);
			}

		} catch (Exception e) {
			logger.error("error while processing file for provider keys", e);
		} finally {
			if (providerKeyToMatchingStringIS != null) {
				try {
					providerKeyToMatchingStringIS.close();
				} catch (IOException e) {
					logger.error("error in closing file for vendor url to affiliate url mappings", e);
				}
			}
		}

	}

	private List<Offer> getOffersFrom(List<RedWineOffer> redWineOffers) {
		List<Offer> offers = new ArrayList<Offer>();
		for (RedWineOffer redWineOffer : redWineOffers) {
			Offer offer = new Offer();
			offer.setDescription(redWineOffer.getDescription());
			Vendor vendor = getVendorFrom(redWineOffer);
			offer.setTargetVendor(vendor);
			Date expiry = getExpiryFrom(redWineOffer.getExpiryDate());
			offer.setExpiry(expiry);
			offers.add(offer);
		}
		return offers;
	}

	/**
	 * Converts into date from string having the following format:
	 * Expiry: Mar 31, 2013
	 * @param expiryDateStr
	 * @return
	 */
	private Date getExpiryFrom(String expiryDateStr) {

		if ((expiryDateStr == null) || (expiryDateStr.indexOf("Expiry: ") == -1)) {
			return null;
		}
		String expiryDate = expiryDateStr.substring("Expiry: ".length());
		DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
		try {
			java.util.Date parsedDate = dateFormat.parse(expiryDate);
			long epochtimeUTCInMillis = parsedDate.getTime();
			Date date = new Date(epochtimeUTCInMillis);
			return date;

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		RedWineSyncJob redWineSyncJob = new RedWineSyncJob();
		Date expiryFrom = redWineSyncJob.getExpiryFrom("Expiry: Apr 31, 2013");
		System.out.println(expiryFrom);
		expiryFrom = redWineSyncJob.getExpiryFrom(null);
		expiryFrom = redWineSyncJob.getExpiryFrom("");
		System.out.println(expiryFrom);
	}

	private Vendor getVendorFrom(RedWineOffer redWineOffer) {
		String merchantName = redWineOffer.getMerchantName();
		String vendorUrl = null;
		String vendorName = merchantName;
		if (CommonUtilities.isNullOrEmpty(merchantName)) {
			return null;
		}

		if (merchantName.endsWith(".com") || merchantName.endsWith(".in") || merchantName.endsWith(".org") || merchantName.endsWith(".co.in")) {
			vendorUrl = "http://" + merchantName.toLowerCase();
			String[] strings = merchantName.split("\\.");
			vendorName = strings[0];
		}

		Vendor vendor = new Vendor();
		vendor.setName(vendorName);
		vendor.setSiteUrl(vendorUrl);
		vendor.setVendorType(VendorType.GLOBAL);
		vendor.setSourceType(SourceType.REDWINE);
		return vendor;
	}

	private OfferCard getOfferCardFrom(RedWineCard redWineCard) {
		OfferCard offerCard = new OfferCard();
		offerCard.setName(redWineCard.getCardName());
		String cardId = RedWineParserUtil.getIdFromCard(redWineCard.getCardUrl());
		offerCard.setCardSource(CardSource.REDWINE);
		offerCard.setCardSourceIdentifier(cardId);
		offerCard.setCardType(RedWineParserUtil.getCardTypeFor(redWineCard.getCardType(), redWineCard.getCardName()));
		offerCard.setPaymentChannel(RedWineParserUtil.getPaymentChannelFor(redWineCard.getCardName()));
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
			stopped = true;
			this.notifyAll();
		}
	}

	@Override
	public void pause() {
		synchronized (this) {
			paused = true;
			this.notifyAll();
		}
	}

	@Override
	public void resume() {
		synchronized (this) {
			paused = false;
			this.notifyAll();
		}
	}

	@Override
	public void addJobEventListener(JobEventListener eventListener) {
		// TODO Auto-generated method stub

	}

	public boolean isSyncCards() {
		return syncCards;
	}

	public void setSyncCards(boolean syncCards) {
		this.syncCards = syncCards;
	}

	public boolean isSyncOfferOnCards() {
		return syncOfferOnCards;
	}

	public void setSyncOfferOnCards(boolean syncOfferOnCards) {
		this.syncOfferOnCards = syncOfferOnCards;
	}



}
