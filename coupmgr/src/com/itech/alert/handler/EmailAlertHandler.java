package com.itech.alert.handler;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.itech.alert.model.Alert;
import com.itech.common.CommonFileUtilities;
import com.itech.coupon.manager.UserManager;
import com.itech.coupon.model.User;
import com.itech.email.services.EmailManager;

public class EmailAlertHandler implements AlertHandler{

	private EmailManager emailManager;
	private UserManager userManager;
	private static final String DEFAULT_NOTIFICATION_SUBJECT="Coupoxo Coupon Expiry Notification";
	private static final String DEFAULT_TEST_COUPOXO_MAIL_ADDRESS= "test.coupoxo@gmail.com";
	private final Logger logger = Logger.getLogger(EmailAlertHandler.class);


	public EmailManager getEmailManager() {
		return emailManager;
	}

	public void setEmailManager(EmailManager emailManager) {
		this.emailManager = emailManager;
	}

	@Override
	public void handle(Alert alert) {

		// this userID is the auto-generated id (User.id)of User DAO not the User.user_id
		long userId = alert.getUserId();
		User user = userManager.getById(userId);
		String toEmailId= user.getEmailId();
		String message = alert.getMessage();
		try {
			Document doc = Jsoup.parse(CommonFileUtilities.getResourceFileAsString("index.html"));
			Element para = doc.select("#mail-message").first();
			para.html("<singleline label=\"Title\">"+message+"</singleline>");
			message = doc.toString();
		} catch (IOException e) {
			logger.debug("Email HTML could not be generated ; toAdress" + toEmailId);
			e.printStackTrace();
		}
		emailManager.sendMail(DEFAULT_TEST_COUPOXO_MAIL_ADDRESS, toEmailId, DEFAULT_NOTIFICATION_SUBJECT, message);
	}

	@Override
	public boolean handles(Alert alert) {
		return true;
	}

}
