package com.itech.alert.handler;

import org.apache.log4j.Logger;

import com.itech.alert.model.Alert;
import com.itech.alert.model.AlertDataTypes;
import com.itech.coupon.manager.UserManager;
import com.itech.coupon.model.User;
import com.itech.email.services.EmailManager;
import com.itech.email.vo.EmailMessage;
import com.itech.email.vo.OfferExpiryNotificationEmail;

public class OfferAlertHandler implements AlertHandler{

	private EmailManager emailManager;
	private UserManager userManager;
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
		User user = alert.getUser();
		String toEmailId= user.getEmailId();
		String message = alert.getMessage();
		EmailMessage email = new OfferExpiryNotificationEmail(message, toEmailId);
		emailManager.sendEmail(email);
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
}
