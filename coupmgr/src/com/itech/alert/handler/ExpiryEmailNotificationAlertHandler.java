package com.itech.alert.handler;

import org.apache.log4j.Logger;

import com.itech.alert.model.Alert;
import com.itech.coupon.manager.UserManager;
import com.itech.coupon.model.User;
import com.itech.email.services.EmailManager;
import com.itech.email.vo.EmailMessage;
import com.itech.email.vo.OfferExpiryNotificationEmail;

public class ExpiryEmailNotificationAlertHandler implements AlertHandler{

	private EmailManager emailManager;
	private UserManager userManager;

	private static final String DEFAULT_NOTIFICATION_SUBJECT="Coupoxo Coupon Expiry Notification";
	private static final String DEFAULT_TEST_COUPOXO_MAIL_ADDRESS= "test.coupoxo@gmail.com";
	private final Logger logger = Logger.getLogger(ExpiryEmailNotificationAlertHandler.class);


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
		EmailMessage email = new OfferExpiryNotificationEmail();
		email.setContentInMessageHTML(message);
		email.setMailContent(message);
		email.setToAddress(toEmailId);
		email.setSubject(DEFAULT_NOTIFICATION_SUBJECT);
		emailManager.sendEmail(email);
	}

	@Override
	public boolean handles(Alert alert) {
		return true;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
}
