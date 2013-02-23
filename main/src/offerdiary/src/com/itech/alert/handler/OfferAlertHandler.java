package com.itech.alert.handler;

import org.apache.log4j.Logger;

import com.itech.alert.model.Alert;
import com.itech.alert.model.AlertDataTypes;
import com.itech.email.services.EmailManager;
import com.itech.email.vo.Email;
import com.itech.email.vo.OfferExpiryNotificationEmail;
import com.itech.offer.manager.OfferManager;
import com.itech.offer.model.Offer;
import com.itech.user.manager.UserManager;
import com.itech.user.model.User;

public class OfferAlertHandler implements AlertHandler{

	private EmailManager emailManager;
	private UserManager userManager;
	private OfferManager offerManager;
	private final Logger logger = Logger.getLogger(OfferAlertHandler.class);

	public EmailManager getEmailManager() {
		return emailManager;
	}

	public void setEmailManager(EmailManager emailManager) {
		this.emailManager = emailManager;
	}

	@Override
	public void handle(Alert alert) {
		// this userID is the auto-generated id (User.id)of User DAO not the User.user_id
		Long offerId = alert.getDataId();
		Offer offer = getOfferManager().getOfferFor(offerId);
		User user = alert.getUser();
		Email email = new OfferExpiryNotificationEmail(alert, offer, user);
		emailManager.sendEmailAsync(email);
	}

	@Override
	public boolean handles(Alert alert) {
		if (AlertDataTypes.OFFER.toString().equalsIgnoreCase(alert.getDataType())) {
			return true;
		}
		return false;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public OfferManager getOfferManager() {
		return offerManager;
	}

	public void setOfferManager(OfferManager offerManager) {
		this.offerManager = offerManager;
	}
}
