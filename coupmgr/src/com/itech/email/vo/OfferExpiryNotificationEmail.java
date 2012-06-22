package com.itech.email.vo;

public class OfferExpiryNotificationEmail extends EmailVO{

	private String NOTIFICATION_EMAIL_HTML ;

	@Override
	public void setHtmlContent(String htmlContent) {
		generateNotificationEmailContent();
		setHtmlContent(NOTIFICATION_EMAIL_HTML);
	}

	private void generateNotificationEmailContent() {
		// TODO Auto-generated method stub

	}

	public String getNOTIFICATION_EMAIL_HTML() {
		return NOTIFICATION_EMAIL_HTML;
	}
	public void setNOTIFICATION_EMAIL_HTML(String nOTIFICATION_EMAIL_HTML) {
		NOTIFICATION_EMAIL_HTML = nOTIFICATION_EMAIL_HTML;
	}

}
