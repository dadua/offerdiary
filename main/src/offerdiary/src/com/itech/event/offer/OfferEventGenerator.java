package com.itech.event.offer;

import com.itech.offer.model.Offer;
import com.itech.user.model.User;

public interface OfferEventGenerator {

	public void offerCreated(Offer offer, User user);

	public void offerModified(Offer offer, User user);

	public void offerDeleted(Offer offer, User user);

}
