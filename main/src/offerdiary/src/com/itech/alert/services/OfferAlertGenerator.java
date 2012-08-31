package com.itech.alert.services;

import java.sql.Date;

import com.itech.alert.model.Alert;
import com.itech.alert.model.AlertConfig;
import com.itech.alert.model.AlertDataTypes;
import com.itech.alert.model.Alert.AlertStatus;
import com.itech.offer.manager.OfferManager;
import com.itech.offer.model.Offer;

public class OfferAlertGenerator implements AlertGenerator {
	private OfferManager offerManager;

	@Override
	public boolean handles(AlertConfig alertConfig) {
		if (AlertDataTypes.OFFER.toString().equalsIgnoreCase(alertConfig.getDataType())) {
			return true;
		}
		return false;
	}

	@Override
	public Alert createAlert(AlertConfig alertConfig) {
		Alert alert = createAlertFor(alertConfig);
		return alert;
	}

	private Alert createAlertFor(AlertConfig alertConfig) {
		Offer offer = getOfferManager().getById(alertConfig.getDataId());
		String alertMessage = "Your offer is expiring on: " + offer.getExpiry()  + "\n" +
		"offer Detail:\n " + offer.getDescription();
		Alert alert = new Alert(getOfferManager().getOfferOwner(offer), AlertDataTypes.OFFER.toString(),offer.getId(), AlertStatus.NEW, new Date(System.currentTimeMillis()), alertMessage, alertMessage);
		return alert;
	}

	public OfferManager getOfferManager() {
		return offerManager;
	}

	public void setOfferManager(OfferManager offerManager) {
		this.offerManager = offerManager;
	}
}
