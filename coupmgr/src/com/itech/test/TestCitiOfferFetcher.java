package com.itech.test;

import com.itech.offer.fetchers.ICICIOfferFetcher;

public class TestCitiOfferFetcher {


	public static void main(String[] args) {


		ICICIOfferFetcher fetcher = new ICICIOfferFetcher();
		fetcher.fetchAllOffers();

		//		CitiOfferFetcher fetcher = new CitiOfferFetcher();
		//
		//		List<CardOfferVO> fetchAllOffers = fetcher.fetchAllOffers();
		//
		//		System.out.println("Size is - "+fetchAllOffers.size());
		//
		//		HsbcDiningOfferFetcher hsbcDiningOfferFetcher = new HsbcDiningOfferFetcher();
		//
		//		hsbcDiningOfferFetcher.fetchAllOffers();

	}

}
