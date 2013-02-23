package com.itech.event.offer;

import com.itech.event.services.EventEngine;
import com.itech.event.vo.Event;
import com.itech.event.vo.OfferEvent;
import com.itech.event.vo.OfferEvent.OfferEventType;
import com.itech.offer.model.Offer;
import com.itech.user.model.User;

public class OfferEventGeneratorImpl implements OfferEventGenerator {
	private EventEngine eventEngine;

	@Override
	public void offerCreated(Offer offer, User user) {
		Event event = new OfferEvent(OfferEventType.OFFER_CREATED, offer, user);
		eventEngine.handleEvent(event);
	}

	@Override
	public void offerModified(Offer offer, User user) {
		Event event = new OfferEvent(OfferEventType.OFFER_MODIFIED, offer, user);
		eventEngine.handleEvent(event);
	}

	@Override
	public void offerDeleted(Offer offer, User user) {
		Event event = new OfferEvent(OfferEventType.OFFER_DELETED, offer, user);
		eventEngine.handleEvent(event);
	}

	public void setEventEngine(EventEngine eventEngine) {
		this.eventEngine = eventEngine;
	}

	public EventEngine getEventEngine() {
		return eventEngine;
	}
}
