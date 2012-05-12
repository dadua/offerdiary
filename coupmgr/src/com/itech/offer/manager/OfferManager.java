package com.itech.offer.manager;

import java.util.List;

import com.itech.coupon.model.User;
import com.itech.offer.model.Offer;

public interface OfferManager {

	public void addOfferForUser(Offer offer, User user);

	public List<Offer> getAllOffersForUser(User user);

	public List<Offer> getAllUnexpiredOffersForUser(User user);

}
