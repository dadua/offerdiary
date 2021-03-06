package com.itech.offer.manager;

import java.util.List;

import com.itech.coupon.model.User;
import com.itech.offer.model.Offer;
import com.itech.offer.model.OfferShare;

public interface OfferManager {

	public void addOfferForUser(Offer offer, User user);

	public void addOffersForUser(List<Offer> offers, User user);

	public List<Offer> getAllOffersForUser(User user);

	public List<Offer> getAllUnexpiredOffersForUser(User user);

	public void deleteByIds(List<Long> offerIds);

	public User getOfferOwner(Offer offer);

	public Offer getById(long dataId);

	public OfferShare createOfferShare(Offer offer);

	public OfferShare getOfferShareFor(String accessToken);

	public Offer copySharedOffer(String accessToken);

	public Offer getOfferFor(Long offerId);

}
