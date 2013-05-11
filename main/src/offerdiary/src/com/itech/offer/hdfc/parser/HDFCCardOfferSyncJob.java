package com.itech.offer.hdfc.parser;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.itech.offer.FeederConstants;
import com.itech.offer.fetchers.HdfcOfferFetcher;
import com.itech.offer.fetchers.HdfcOfferFetcher.CardTypeKey;
import com.itech.offer.job.BaseItechJob;
import com.itech.offer.job.JobEventListener;
import com.itech.offer.manager.OfferCardManager;
import com.itech.offer.manager.OfferManager;
import com.itech.offer.model.Offer;
import com.itech.offer.model.OfferCard;
import com.itech.offer.model.OfferCard.CardType;
import com.itech.offer.model.OfferCard.PaymentChannel;
import com.itech.parser.offer.model.CardOfferVO;

public class HDFCCardOfferSyncJob  extends BaseItechJob{

	private static final String PROVIDER_HDFC_BANK = "HDFC_BANK";

	private OfferCardManager offerCardManager;

	private OfferManager offerManager;

	private static final Logger logger = Logger.getLogger(HDFCCardOfferSyncJob.class);

	private void syncHDFCData() {
		logger.info("initializing hdfc data");
		HdfcOfferFetcher hdfcOfferFetcher = new HdfcOfferFetcher();
		List<CardOfferVO> allOffers = hdfcOfferFetcher.fetchAllOffers();
		Map<CardTypeKey, List<Offer>> offerMap = getOfferMapFor(allOffers);
		String providerName = PROVIDER_HDFC_BANK;
		List<OfferCard> allCardsForHDFC = offerCardManager.getOfferCardsByProvider(providerName);
		for (Entry<CardTypeKey, List<Offer>> entry : offerMap.entrySet()) {
			CardTypeKey typeKey = entry.getKey();
			List<Offer> offers = entry.getValue();
			for (Offer offer :  offers) {
				offer.setSourceTag(getSourceTag(typeKey));
			}
			List<OfferCard> cards = filterCardsFor(allCardsForHDFC, typeKey);
			getOfferManager().addOffersForCards(offers, cards);
			getHibernateSessionFactory().commitCurrentTransaction();
		}
	}



	private List<OfferCard> filterCardsFor(List<OfferCard> allCardsForHDFC,
			CardTypeKey typeKey) {
		List<OfferCard> filteredCards = new ArrayList<OfferCard>();
		for (OfferCard card : allCardsForHDFC) {
			boolean qualified = false;
			switch (typeKey) {
			case CREDIT:
				if (CardType.CREDIT.equals(card.getCardType())) {
					qualified = true;
				}
				break;
			case CREDIT_VISA:
				if (CardType.CREDIT.equals(card.getCardType()) && PaymentChannel.VISA.equals(card.getPaymentChannel())) {
					qualified = true;
				}
				break;
			case CREDIT_MASTER:
				if (CardType.CREDIT.equals(card.getCardType()) && PaymentChannel.MASTER_CARD.equals(card.getPaymentChannel())) {
					qualified = true;
				}
				break;
			case DEBIT:
				if (CardType.DEBIT.equals(card.getCardType())) {
					qualified = true;
				}
				break;
			case DEBIT_MASTER:
				if (CardType.DEBIT.equals(card.getCardType()) && PaymentChannel.MASTER_CARD.equals(card.getPaymentChannel())) {
					qualified = true;
				}
				break;
			case DEBIT_VISA:
				if (CardType.DEBIT.equals(card.getCardType()) && PaymentChannel.VISA.equals(card.getPaymentChannel())) {
					qualified = true;
				}
				break;

			case EMI_CREDIT:
				if (CardType.CREDIT.equals(card.getCardType())) {
					qualified = true;
				}
				break;
			default:
				break;
			}

			if (qualified) {
				filteredCards.add(card);
			}
		}
		return filteredCards;
	}

	public String getSourceTag(CardTypeKey typeKey) {
		String providerPrefix = "HDFC Bank ";
		switch (typeKey) {
		case CREDIT:
			return providerPrefix + " Credit Cards";
		case CREDIT_VISA:
			return providerPrefix + " VISA Credit Cards";
		case CREDIT_MASTER:
			return providerPrefix + " Master Credit Cards";
		case DEBIT:
			return providerPrefix + " Debit Cards";
		case DEBIT_MASTER:
			return providerPrefix + " Matser Debit Cards";
		case DEBIT_VISA:
			return providerPrefix + " Visa Debit Cards";

		case EMI_CREDIT:
			return providerPrefix + " Credit Card EMIs";
		default:
			return providerPrefix;
		}
	}



	private Map<CardTypeKey, List<Offer>> getOfferMapFor(List<CardOfferVO> allOffers) {
		Map<CardTypeKey, List<Offer>> offerMap = new HashMap<CardTypeKey, List<Offer>>();
		for (CardOfferVO cardOfferVO : allOffers) {
			CardTypeKey type = CardTypeKey.valueOf(cardOfferVO.getCardTypeTag());
			List<Offer> offerList = offerMap.get(type);
			if (offerList == null) {
				offerList = new ArrayList<Offer>();
				offerMap.put(type, offerList);
			}
			Offer offer = new Offer();
			CardOfferVO.fillOfferFromVO(offer, cardOfferVO);
			if (offer.getTargetVendor() == null) {
				logger.error("Target vendor is null for offer: " + cardOfferVO);
				continue;
			}
			offer.setFeeder(FeederConstants.HDFC_CARD_OFFER_FEEDER);
			offer.setFeederReputation(FeederConstants.OD_BANK_CARD_OFFER_FEEDER_REPUTATION);
			offerList.add(offer);
		}
		return offerMap;
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

	}

	@Override
	public void doJobTask() {
		syncHDFCData();
	}



	@Override
	public void addJobEventListener(JobEventListener eventListener) {
		// TODO Auto-generated method stub

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


}
