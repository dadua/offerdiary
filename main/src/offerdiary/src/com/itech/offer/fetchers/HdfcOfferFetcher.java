package com.itech.offer.fetchers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.itech.common.util.UtilHttp;
import com.itech.offer.hdfc.parser.HdfcOfferParser;
import com.itech.parser.offer.model.CardOfferVO;

public class HdfcOfferFetcher implements CardOfferFetcher{


	private static final Logger logger = Logger.getLogger(HdfcOfferFetcher.class);
	//	public static final String HDFC_OFFER_BASE_URL="http://www.hdfcbank.com/personal/Offer_Landing/offers-landing";
	//	public static final String HDFC_DEBIT_CARD_OFFER_URL="http://www.hdfcbanksmartbuy.com/pages/Debit-Card-Offers/pgid-35401.aspx";
	//	public static final String HDFC_CREDIT_CARD_OFFER_URL="http://www.hdfcbanksmartbuy.com/pages/Credit-Card-Offers/pgid-35449.aspx";


	public static final String HDFC_DEBIT_CARD_OFFERS_URL = "http://www.smartbuy.hdfcbank.com/pages/Debit-Card-Offers/pgid-35401.aspx";
	public static final String HDFC_MASTER_DEBIT_CARD_OFFERS_URL = "http://www.smartbuy.hdfcbank.com/pages/Mastercard-Debit-Card-Offers/pgid-37380.aspx";
	public static final String HDFC_VISA_DEBIT_CARD_OFFERS_URL = "http://www.smartbuy.hdfcbank.com/pages/Visa-Debit-Card-Offers/pgid-37419.aspx";


	//public static final String HDFC_EMI_CARD_OFFERS_URL = "http://www.smartbuy.hdfcbank.com/pages/Easy-EMI-Offers/pgid-32199.aspx";
	public static final String HDFC_MASTER_CREDIT_CARD_OFFERS_URL = "http://www.smartbuy.hdfcbank.com/pages/Master-Card-Credit-Card-Offers/pgid-35941.aspx";
	public static final String HDFC_VISA_CREDIT_CARD_OFFERS_URL = "http://www.smartbuy.hdfcbank.com/pages/Visa-Credit-Card-offers/pgid-35691.aspx";
	public static final String HDFC_CREDIT_CARD_OFFERS_URL = "http://www.smartbuy.hdfcbank.com/pages/Online-Partners/pgid-32938.aspx";


	public static Map<CardTypeKey, String> typeKeyToOfferUrls = new HashMap<CardTypeKey, String>();
	static  {
		typeKeyToOfferUrls.put(CardTypeKey.CREDIT, HDFC_CREDIT_CARD_OFFERS_URL);
		typeKeyToOfferUrls.put(CardTypeKey.CREDIT_VISA, HDFC_VISA_CREDIT_CARD_OFFERS_URL);
		typeKeyToOfferUrls.put(CardTypeKey.CREDIT_MASTER, HDFC_MASTER_CREDIT_CARD_OFFERS_URL);
		typeKeyToOfferUrls.put(CardTypeKey.DEBIT, HDFC_DEBIT_CARD_OFFERS_URL);
		typeKeyToOfferUrls.put(CardTypeKey.DEBIT_VISA, HDFC_VISA_DEBIT_CARD_OFFERS_URL);
		typeKeyToOfferUrls.put(CardTypeKey.DEBIT_MASTER, HDFC_MASTER_DEBIT_CARD_OFFERS_URL);
	}
	public enum CardTypeKey {
		CREDIT, DEBIT, CREDIT_VISA, DEBIT_VISA, CREDIT_MASTER, DEBIT_MASTER, EMI_CREDIT;

		static CardTypeKey getCardTypeKey(String cardTypeTag) {
			return CardTypeKey.valueOf(cardTypeTag);
		}
	}

	@Override
	public List<CardOfferVO> fetchAllOffers() {
		List<CardOfferVO> allOffers = new ArrayList<CardOfferVO>();
		for (Entry<CardTypeKey, String> typeToUrlEntry : typeKeyToOfferUrls.entrySet()) {
			String offerPageUrl = typeToUrlEntry.getValue();
			List<CardOfferVO> offers = getCardOffersFor(offerPageUrl);
			for( CardOfferVO cardOfferVO : offers) {
				cardOfferVO.setCardTypeTag(typeToUrlEntry.getKey().name());
			}
			allOffers.addAll(offers);
		}
		return allOffers;
	}

	private List<CardOfferVO> getCardOffersFor(String offerPageUrl) {
		String response = UtilHttp.fetchHttpResponse(offerPageUrl);
		HdfcOfferParser parser = new HdfcOfferParser(response);
		return parser.getOffersOnCard();
	}

	private void debugOffers(List<CardOfferVO> allOffers) {
		for(CardOfferVO cardOffer : allOffers){
			logger.info(cardOffer);
		}
		logger.info("total offer counts: " + allOffers.size());

	}

	public static void main(String[] args) {
		HdfcOfferFetcher hdfcOfferFetcher = new HdfcOfferFetcher();
		List<CardOfferVO> debitCardOffers = hdfcOfferFetcher.getCardOffersFor(HDFC_DEBIT_CARD_OFFERS_URL);
		hdfcOfferFetcher.debugOffers(debitCardOffers);

		List<CardOfferVO> allOffers = hdfcOfferFetcher.fetchAllOffers();
		hdfcOfferFetcher.debugOffers(allOffers);


	}
}
