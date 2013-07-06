package com.itech.offer.fetchers;

import java.util.ArrayList;
import java.util.List;

import com.itech.common.util.UtilHttp;
import com.itech.offer.fetchers.parser.HsbcDiningOfferParser;
import com.itech.parser.offer.model.CardOfferVO;

public class HsbcDiningOfferFetcher implements CardOfferFetcher {

	private static final String HSBC_DINING_GOLD_BASE_URL = "http://mail.hsbc.com.hk/in/cc_dining_gold/";
	private static final String HSBC_DINING_GOLD_CC_OFFER_URL = HSBC_DINING_GOLD_BASE_URL + "cc_dining_alliances.htm";

	@Override
	public List<CardOfferVO> fetchAllOffers() {
		String response = UtilHttp.fetchHttpResponse(HSBC_DINING_GOLD_CC_OFFER_URL);
		HsbcDiningOfferParser parser = new HsbcDiningOfferParser(response);

		List<CardOfferVO> offers = new ArrayList<CardOfferVO>();

		List<String> cityOfferLinks = parser.getCityOfferLinks();
		for (String cityOfferLink: cityOfferLinks) {
			offers.addAll(fetchOfferFromCityLink(cityOfferLink));
		}

		return offers;
	}

	private List<CardOfferVO> fetchOfferFromCityLink(
			String cityOfferLink) {
		String fullCityUrl = HSBC_DINING_GOLD_BASE_URL + cityOfferLink;
		String response = UtilHttp.fetchHttpResponse(fullCityUrl);

		HsbcDiningOfferParser diningOfferParser = new HsbcDiningOfferParser(response);
		return diningOfferParser.getOffers();

	}



}
