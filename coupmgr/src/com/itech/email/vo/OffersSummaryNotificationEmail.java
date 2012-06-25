package com.itech.email.vo;

public class OffersSummaryNotificationEmail extends EmailMessage{

	private String OFFER_SUMMARY_EMAIL_HTML ;

	public String getOFFER_SUMMARY_EMAIL_HTML() {
		return OFFER_SUMMARY_EMAIL_HTML;
	}
	public void setOFFER_SUMMARY_EMAIL_HTML(String OFFER_SUMMARY_EMAIL_HTML) {
		this.OFFER_SUMMARY_EMAIL_HTML = OFFER_SUMMARY_EMAIL_HTML;
	}

	@Override
	public String getMessageContent() {
		// TODO Auto-generated method stub
		return null;
	}
}
