package com.itech.event.coupon;

import com.itech.coupon.model.Coupon;
import com.itech.event.services.EventEngine;
import com.itech.event.vo.CouponEvent;
import com.itech.event.vo.Event;
import com.itech.event.vo.CouponEvent.CouponEventType;

public class CouponEventGeneratorImpl implements CouponEventGenerator {
	private EventEngine eventEngine;

	public void couponCreated(Coupon coupon) {
		Event event = new CouponEvent(CouponEventType.COUPON_CREATED, coupon);
		eventEngine.handleEvent(event);
	}

	public void couponModified(Coupon coupon) {
		Event event = new CouponEvent(CouponEventType.COUPON_MODIFIED, coupon);
		eventEngine.handleEvent(event);
	}

	public void couponDeleted(Coupon coupon) {
		Event event = new CouponEvent(CouponEventType.COUPON_DELETED, coupon);
		eventEngine.handleEvent(event);
	}

	public void setEventEngine(EventEngine eventEngine) {
		this.eventEngine = eventEngine;
	}

	public EventEngine getEventEngine() {
		return eventEngine;
	}
}
