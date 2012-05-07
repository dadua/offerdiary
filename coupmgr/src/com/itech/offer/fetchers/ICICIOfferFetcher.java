package com.itech.offer.fetchers;

import java.util.ArrayList;
import java.util.List;

import com.itech.common.util.UtilHttp;
import com.itech.offer.fetchers.parser.ICICIOfferParser;
import com.itech.offer.model.Offer;

public class ICICIOfferFetcher implements OfferFetcher{


	public static final String ICICI_OFFERS_BASE_URL="http://www.icicibank.com/Personal-Banking/cards/Consumer-Cards/Credit-Card/special-offers/";
	public static final String SHOPPING_OFFERS_URL= ICICI_OFFERS_BASE_URL + "Shopping.html";
	public static final String DINING_OFFERS_URL=ICICI_OFFERS_BASE_URL +"Dinning.html";
	public static final String ENTERTAINMENT_OFFERS_URL=ICICI_OFFERS_BASE_URL +"Entertainment.html";

	@Override
	public List<Offer> fetchAllOffers() {
		List<Offer> shoppingOffers = getShoppingOffers();
		List<Offer> entertainmentOffers= getEntertainmentOffers();
		List<Offer> diningOffers= getDiningOffers();
		List<Offer> allICICIOffers = new ArrayList<Offer>();
		allICICIOffers.addAll(shoppingOffers);
		allICICIOffers.addAll(diningOffers);
		allICICIOffers.addAll(entertainmentOffers);
		return allICICIOffers;
	}

	private List<Offer> getDiningOffers() {
		String response = UtilHttp.fetchHttpResponse(DINING_OFFERS_URL);
		ICICIOfferParser parser = new ICICIOfferParser(response);
		return parser.getAllOffers();
	}

	private List<Offer> getEntertainmentOffers() {
		String response = UtilHttp.fetchHttpResponse(ENTERTAINMENT_OFFERS_URL);
		ICICIOfferParser parser = new ICICIOfferParser(response);
		return parser.getAllOffers();
	}

	private List<Offer> getShoppingOffers() {
		String response = UtilHttp.fetchHttpResponse(SHOPPING_OFFERS_URL);
		ICICIOfferParser parser = new ICICIOfferParser(response);
		return parser.getAllOffers();
	}

}
