package com.itech.test;

import com.itech.offer.fetchers.CitiOfferFetcher;
import com.itech.offer.fetchers.HsbcDiningOfferFetcher;

public class TestCitiOfferFetcher {


	public static void main(String[] args) {

		CitiOfferFetcher fetcher = new CitiOfferFetcher();

		//List<Offer> fetchAllOffers = fetcher.fetchAllOffers();

		HsbcDiningOfferFetcher hsbcDiningOfferFetcher = new HsbcDiningOfferFetcher();

		hsbcDiningOfferFetcher.fetchAllOffers();

	}

}
