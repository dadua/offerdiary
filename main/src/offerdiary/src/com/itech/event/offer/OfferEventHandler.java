package com.itech.event.offer;


import java.sql.Date;

import com.itech.alert.model.AlertConfig;
import com.itech.alert.model.AlertDataTypes;
import com.itech.alert.services.AlertConfigManager;
import com.itech.common.CommonUtilities;
import com.itech.config.ProjectConfigs;
import com.itech.event.services.EventHandler;
import com.itech.event.vo.Event;
import com.itech.event.vo.OfferEvent;
import com.itech.event.vo.OfferEvent.OfferEventType;
import com.itech.user.model.UserRole;

public class OfferEventHandler implements EventHandler {

	private static final String OFFER_EXPIRY_ALERT = "OFFER_EXPIRY_ALERT";
	private AlertConfigManager alertConfigManager;

	@Override
	public void handle(Event event) {
		OfferEvent offerEvent = (OfferEvent) event;
		if (OfferEventType.OFFER_CREATED.equals(offerEvent.getOfferEventType())) {
			handleOfferCreated(offerEvent);
		} else if (OfferEventType.OFFER_MODIFIED.equals(offerEvent.getOfferEventType())) {
			handleOfferModified(offerEvent);
		} else if (OfferEventType.OFFER_DELETED.equals(offerEvent.getOfferEventType())) {
			handleOfferDeleted(offerEvent);
		}
	}

	private void handleOfferCreated(OfferEvent offerEvent) {
		if (offerEvent.getUser() == null || UserRole.OD_ADMIN.equals(offerEvent.getUser().getUserRole())) {
			return;
		}
		AlertConfig alertConfig = createAlertConfigFor(offerEvent);
		alertConfig.setPersistAlertInDB(true);
		alertConfig.setAlertType(OFFER_EXPIRY_ALERT);
		if (offerEvent.getOffer().getExpiry().before(new Date(System.currentTimeMillis()))) {
			return;
		}
		alertConfigManager.save(alertConfig);
	}

	private void handleOfferDeleted(OfferEvent offerEvent) {
		// TODO Auto-generated method stub
	}

	private void handleOfferModified(OfferEvent offerEvent) {
		// TODO Auto-generated method stub
	}

	private AlertConfig createAlertConfigFor(OfferEvent offerEvent) {
		AlertConfig alertConfig = new AlertConfig();
		alertConfig.setUser(offerEvent.getUser());
		alertConfig.setCreationTime(new Date(System.currentTimeMillis()));
		alertConfig.setDataType(AlertDataTypes.OFFER.toString());
		alertConfig.setDataId(offerEvent.getOffer().getId());
		Date triggerTime = calculateTriggerTime(offerEvent);
		alertConfig.setTriggerTime(triggerTime);
		alertConfig.setStatus(AlertConfig.ActivationStatus.ACTIVE);
		//NotifyVO notifyVO = offerEvent.getOffer().getNotifyVO();
		//TODO handle on the basis of per offer notification settings.
		alertConfig.setFbNotification(true);
		alertConfig.setEmailNotification(true);
		alertConfig.setTriggerExpiryTime(offerEvent.getOffer().getExpiry());
		return alertConfig;
	}

	private Date calculateTriggerTime(OfferEvent offerEvent) {
		long triggerTimeInMillies = offerEvent.getOffer().getExpiryDateInMillis() - CommonUtilities.MILLIS_IN_A_DAY *
				ProjectConfigs.defaultOfferNotificationTriggerTimeInDays.get();
		Date triggerTime = new Date(triggerTimeInMillies);
		return triggerTime;
	}

	@Override
	public boolean handles(Event event) {
		if (OfferEvent.class.isAssignableFrom(event.getClass())) {
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
