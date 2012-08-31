package com.itech.event.coupon;

import com.itech.event.services.EventEngine;
import com.itech.event.vo.Event;
import com.itech.event.vo.OfferEvent;
import com.itech.event.vo.OfferEvent.OfferEventType;
import com.itech.offer.model.Offer;

public class OfferEventGeneratorImpl implements OfferEventGenerator {
	private EventEngine eventEngine;

	@Override
	public void offerCreated(Offer offer) {
		Event event = new OfferEvent(OfferEventType.OFFER_CREATED, offer);
		eventEngine.handleEvent(event);
	}

	@Override
	public void offerModified(Offer offer) {
		Event event = new OfferEvent(OfferEventType.OFFER_MODIFIED, offer);
		eventEngine.handleEvent(event);
	}

	@Override
	public void offerDeleted(Offer offer) {
		Event event = new OfferEvent(OfferEventType.OFFER_DELETED, offer);
		eventEngine.handleEvent(event);
	}

	public void setEventEngine(EventEngine eventEngine) {
		this.eventEngine = eventEngine;
	}

	public EventEngine getEventEngine() {
		return eventEngine;
	}
}
