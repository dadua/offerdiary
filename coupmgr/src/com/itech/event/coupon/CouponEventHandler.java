package com.itech.event.coupon;


import java.sql.Date;

import com.itech.alert.model.AlertConfig;
import com.itech.alert.model.AlertDataTypes;
import com.itech.alert.services.AlertConfigManager;
import com.itech.event.services.EventHandler;
import com.itech.event.vo.CouponEvent;
import com.itech.event.vo.Event;
import com.itech.event.vo.CouponEvent.CouponEventType;

public class CouponEventHandler implements EventHandler {
	private static final int DEFAULT_TRIGGER_TIME = 30000;
	private AlertConfigManager alertConfigManager;

	@Override
	public void handle(Event event) {
		CouponEvent couponEvent = (CouponEvent) event;
		if (CouponEventType.COUPON_CREATED.equals(couponEvent.getCouponEventType())) {
			handleCouponCreated(couponEvent);
		} else if (CouponEventType.COUPON_MODIFIED.equals(couponEvent.getCouponEventType())) {
			handleCouponModified(couponEvent);
		} else if (CouponEventType.COUPON_DELETED.equals(couponEvent.getCouponEventType())) {
			handleCouponDeleted(couponEvent);
		}

	}

	private void handleCouponCreated(CouponEvent couponEvent) {
		AlertConfig alertConfig = createAlertConfigFor(couponEvent);
		if (alertConfig.getTriggerTime().before(new Date(System.currentTimeMillis()))) {
			return;
		}
		alertConfigManager.save(alertConfig);
	}

	private void handleCouponDeleted(CouponEvent couponEvent) {
		// TODO Auto-generated method stub

	}

	private void handleCouponModified(CouponEvent couponEvent) {
		// TODO Auto-generated method stub

	}

	private AlertConfig createAlertConfigFor(CouponEvent couponEvent) {
		AlertConfig alertConfig = new AlertConfig();
		alertConfig.setCreationTime(new Date(System.currentTimeMillis()));
		alertConfig.setDataType(AlertDataTypes.COUPON.toString());
		alertConfig.setId(couponEvent.getCoupon().getId());
		Date triggerTime = calculateTriggerTime(couponEvent);
		alertConfig.setTriggerTime(triggerTime);
		alertConfig.setStatus(AlertConfig.ActivationStatus.ACTIVE);
		return alertConfig;
	}

	private Date calculateTriggerTime(CouponEvent couponEvent) {
		long triggerTimeInMillies = couponEvent.getCoupon().getExpiryDate().getTime() - DEFAULT_TRIGGER_TIME;
		Date triggerTime = new Date(triggerTimeInMillies);
		return triggerTime;
	}

	@Override
	public boolean handles(Event event) {
		if (CouponEvent.class.isAssignableFrom(event.getClass())) {
			return true;
		}

		return false;
	}

	public void setAlertConfigManager(AlertConfigManager alertConfigManager) {
		this.alertConfigManager = alertConfigManager;
	}

	public AlertConfigManager getAlertConfigManager() {
		return alertConfigManager;
	}

}
