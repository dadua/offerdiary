package com.itech.offer.fetchers;

import java.util.ArrayList;
import java.util.List;

import com.itech.common.util.UtilHttp;
import com.itech.offer.fetchers.parser.ICICIOfferParser;
import com.itech.parser.offer.model.CardOfferVO;

public class ICICIOfferFetcher implements OfferFetcher{

	public static final String ICICI_BANK_OFFER_ZONE = "http://www.icicibank.com/online-services/offer-zone/";
	public static final String ICICI_OFFERS_BASE_URL="http://www.icicibank.com/Personal-Banking/cards/Consumer-Cards/Credit-Card/special-offers/";
	public static final String SHOPPING_OFFERS_URL= ICICI_OFFERS_BASE_URL + "Shopping.html";
	public static final String DINING_OFFERS_URL=ICICI_OFFERS_BASE_URL +"Dinning.html";
	public static final String ENTERTAINMENT_OFFERS_URL=ICICI_OFFERS_BASE_URL +"Entertainment.html";

	@Override
	public List<CardOfferVO> fetchAllOffers() {
		List<CardOfferVO> shoppingOffers = getShoppingOffers();
		List<CardOfferVO> entertainmentOffers= getEntertainmentOffers();
		List<CardOfferVO> diningOffers= getDiningOffers();
		List<CardOfferVO> allICICIOffers = new ArrayList<CardOfferVO>();
		allICICIOffers.addAll(shoppingOffers);
		allICICIOffers.addAll(diningOffers);
		allICICIOffers.addAll(entertainmentOffers);
		return allICICIOffers;
	}

	private List<CardOfferVO> getDiningOffers() {
		String response = UtilHttp.fetchHttpResponse(DINING_OFFERS_URL);
		ICICIOfferParser parser = new ICICIOfferParser(response);
		return parser.getAllOffers();
	}

	private List<CardOfferVO> getEntertainmentOffers() {
		String response = UtilHttp.fetchHttpResponse(ENTERTAINMENT_OFFERS_URL);
		ICICIOfferParser parser = new ICICIOfferParser(response);
		return parser.getAllOffers();
	}

	private List<CardOfferVO> getShoppingOffers() {
		String response = UtilHttp.fetchHttpResponse(SHOPPING_OFFERS_URL);
		ICICIOfferParser parser = new ICICIOfferParser(response);
		return parser.getAllOffers();
	}

}
