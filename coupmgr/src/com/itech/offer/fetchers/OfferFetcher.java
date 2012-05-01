package com.itech.offer.fetchers;

import java.util.List;

import com.itech.offer.model.Offer;

public interface OfferFetcher {

	public List<Offer> fetchAllOffers();

	//	public List<Offer> fetchAllOffers(String place);

}
