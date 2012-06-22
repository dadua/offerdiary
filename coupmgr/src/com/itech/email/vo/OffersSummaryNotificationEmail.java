package com.itech.email.vo;

public class OffersSummaryNotificationEmail extends EmailVO{

	private String OFFER_SUMMARY_EMAIL_HTML ;

	@Override
	public void setHtmlContent(String htmlContent) {
		generateNotificationEmailContent();
		setHtmlContent(OFFER_SUMMARY_EMAIL_HTML);
	}

	private void generateNotificationEmailContent() {
		// TODO Auto-generated method stub

	}

	public String getOFFER_SUMMARY_EMAIL_HTML() {
		return OFFER_SUMMARY_EMAIL_HTML;
	}
	public void setOFFER_SUMMARY_EMAIL_HTML(String OFFER_SUMMARY_EMAIL_HTML) {
		this.OFFER_SUMMARY_EMAIL_HTML = OFFER_SUMMARY_EMAIL_HTML;
	}
}
