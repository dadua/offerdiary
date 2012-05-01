package com.itech.test;

import java.util.List;

import com.itech.offer.fetchers.CitiOfferFetcher;
import com.itech.offer.model.Offer;

public class TestCitiOfferFetcher {


	public static void main(String[] args) {

		CitiOfferFetcher fetcher = new CitiOfferFetcher();

		List<Offer> fetchAllOffers = fetcher.fetchAllOffers();

	}

}
