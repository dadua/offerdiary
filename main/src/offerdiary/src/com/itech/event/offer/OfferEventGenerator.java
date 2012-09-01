package com.itech.event.offer;

import com.itech.offer.model.Offer;

public interface OfferEventGenerator {

	public void offerCreated(Offer offer);

	public void offerModified(Offer offer);

	public void offerDeleted(Offer offer);

}
