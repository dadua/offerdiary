package com.itech.event.vo;

import com.itech.offer.model.Offer;

public class OfferEvent extends Event{
	public enum OfferEventType {
		OFFER_CREATED, OFFER_MODIFIED, OFFER_DELETED
	}

	private OfferEventType offerEventType;
	private Offer offer;

	public OfferEvent(OfferEventType offerEventType, Offer offer) {
		super();
		this.setOfferEventType(offerEventType);
		this.setOffer(offer);
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public OfferEventType getOfferEventType() {
		return offerEventType;
	}

	public void setOfferEventType(OfferEventType offerEventType) {
		this.offerEventType = offerEventType;
	}
}
