package com.itech.email.vo;

public class OfferExpiryNotificationEmail extends EmailMessage{

	private String NOTIFICATION_EMAIL_HTML ;



	public String getNOTIFICATION_EMAIL_HTML() {
		return NOTIFICATION_EMAIL_HTML;
	}
	public void setNOTIFICATION_EMAIL_HTML(String nOTIFICATION_EMAIL_HTML) {
		NOTIFICATION_EMAIL_HTML = nOTIFICATION_EMAIL_HTML;
	}

	@Override
	public String getMessageContent() {
		// TODO Auto-generated method stub
		return null;
	}

}
