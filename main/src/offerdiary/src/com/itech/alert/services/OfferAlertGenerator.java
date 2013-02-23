package com.itech.alert.services;

import java.sql.Date;

import com.itech.alert.model.Alert;
import com.itech.alert.model.Alert.AlertStatus;
import com.itech.alert.model.AlertConfig;
import com.itech.alert.model.AlertDataTypes;
import com.itech.alert.model.NullAlert;
import com.itech.alert.model.NullAlert.NullALertReason;
import com.itech.offer.manager.OfferManager;
import com.itech.offer.model.Offer;
import com.itech.user.manager.UserManager;
import com.itech.user.model.UserNotificationConfig;

public class OfferAlertGenerator implements AlertGenerator {
	private OfferManager offerManager;
	private UserManager userManager;

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
		UserNotificationConfig userNotificationConfig = getUserManager().getUserNotificationConfigFor(alertConfig.getUser());
		if (!userNotificationConfig.getOfferExpiryAlert()) {
			return getNULLOffer(NullALertReason.TRIGGER_NEXT_TIME);
		}
		Offer offer = getOfferManager().getById(alertConfig.getDataId());
		if (offer == null) {
			return getNULLOffer(NullALertReason.NO_OBJECT_FOR_ALERT);
		}
		String alertMessage = "Your offer is expiring on: " + offer.getExpiry()  + "\n" +
				"offer Detail:\n " + offer.getDescription();
		Alert alert = new Alert(getOfferManager().getOfferOwner(offer), AlertDataTypes.OFFER.toString(),offer.getId(), AlertStatus.NEW, new Date(System.currentTimeMillis()), alertMessage, alertMessage);
		alert.setUser(alertConfig.getUser());
		return alert;
	}

	private Alert getNULLOffer(NullALertReason nullALertReason) {
		return new NullAlert(nullALertReason);
	}

	public OfferManager getOfferManager() {
		return offerManager;
	}

	public void setOfferManager(OfferManager offerManager) {
		this.offerManager = offerManager;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
}
