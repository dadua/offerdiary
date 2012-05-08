package com.itech.offer.fetchers;

import java.util.ArrayList;
import java.util.List;

import com.itech.common.util.UtilHttp;
import com.itech.offer.fetchers.parser.CitiOfferParser;
import com.itech.parser.offer.model.Offer;

public class CitiOfferFetcher implements OfferFetcher {

	private static final String CITIBANK_CARD_SPECIAL_OFFERS_URL = "http://www.online.citibank.co.in/card-offers/special-offers.htm";
	private static final String CITIBANK_URL = "http://www.online.citibank.co.in";

	@Override
	public List<Offer> fetchAllOffers() {
		String respHtml = UtilHttp.fetchHttpResponse(CITIBANK_CARD_SPECIAL_OFFERS_URL);
		CitiOfferParser offerParser = new CitiOfferParser(respHtml);
		List<String> cityOfferLinks = offerParser.getCityOfferLinks();
		List<Offer> allOffers = new ArrayList<Offer>();
		for (String cityOfferLink: cityOfferLinks) {
			allOffers.addAll(fetchAllOffers(cityOfferLink));
		}
		return allOffers;
	}

	public List<Offer> fetchAllOffers(String cityOfferLink) {
		String response = UtilHttp.fetchHttpResponse(CITIBANK_URL+cityOfferLink);
		CitiOfferParser offerParser = new CitiOfferParser(response);
		return offerParser.getCuisineOffers();
	}
}
