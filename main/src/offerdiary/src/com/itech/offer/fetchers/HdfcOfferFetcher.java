package com.itech.offer.fetchers;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.itech.common.util.UtilHttp;
import com.itech.offer.fetchers.parser.HdfcOfferParser;
import com.itech.parser.offer.model.CardOfferVO;

public class HdfcOfferFetcher implements OfferFetcher{


	private static final Logger logger = Logger.getLogger(HdfcOfferFetcher.class);
	public static final String HDFC_OFFER_BASE_URL="http://www.hdfcbank.com/personal/Offer_Landing/offers-landing";
	public static final String HDFC_DEBIT_CARD_OFFER_URL="http://www.hdfcbanksmartbuy.com/pages/Debit-Card-Offers/pgid-35401.aspx";
	public static final String HDFC_CREDIT_CARD_OFFER_URL="http://www.hdfcbanksmartbuy.com/pages/Credit-Card-Offers/pgid-35449.aspx";
	@Override
	public List<CardOfferVO> fetchAllOffers() {
		List<CardOfferVO> allOffers = new ArrayList<CardOfferVO>();
		allOffers.addAll(getCreditCardOffers());
		allOffers.addAll(getDebitCardOffers());
		debugOffers(allOffers);
		return allOffers;
	}

	private void debugOffers(List<CardOfferVO> allOffers) {
		for(CardOfferVO cardOffer : allOffers){
			logger.info(cardOffer);
		}

	}

	private List<CardOfferVO> getDebitCardOffers() {
		String response = UtilHttp.fetchHttpResponse(HDFC_DEBIT_CARD_OFFER_URL);
		HdfcOfferParser parser = new HdfcOfferParser(response);
		return parser.getDebitCardOffers();
	}

	private List<CardOfferVO> getCreditCardOffers() {
		String response = UtilHttp.fetchHttpResponse(HDFC_CREDIT_CARD_OFFER_URL);
		HdfcOfferParser parser = new HdfcOfferParser(response);
		return parser.getCreditCardOffers();
	}
}
