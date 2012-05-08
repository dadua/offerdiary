package com.itech.offer.fetchers;

import java.util.List;

import com.itech.parser.offer.model.CardOfferVO;

public interface OfferFetcher {

	public List<CardOfferVO> fetchAllOffers();

	//	public List<CardOfferVO> fetchAllOffers(String place);

}
