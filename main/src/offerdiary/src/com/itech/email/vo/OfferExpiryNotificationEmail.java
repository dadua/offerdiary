package com.itech.email.vo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.itech.alert.model.Alert;
import com.itech.common.CommonFileUtilities;
import com.itech.common.CommonUtilities;
import com.itech.offer.model.Offer;
import com.itech.user.model.User;

public class OfferExpiryNotificationEmail extends Email{

	private static final String DEFAULT_NOTIFICATION_SUBJECT="Offer Expiry Notification for ";
	private static final String EMAIL_CONTENT_FILE ="resources\\offer_expiry_email_template.html";
	private final Offer offer;
	private final User user;
	private final Alert alert;

	public OfferExpiryNotificationEmail(Alert alert, Offer offer, User user) {
		this.alert = alert;
		this.offer = offer;
		this.user = user;
		setSubject();
	}

	@Override
	public String getMailContent() {
		Document doc = Jsoup.parse(CommonFileUtilities.getResourceFileAsString(EMAIL_CONTENT_FILE));
		doc.select(EmailContent.SELECT_SUBHEADING).first().append("Welcome to OfferDiary !");
		doc.select(EmailContent.SELECT_NAME).first().append(user.getName());
		doc.select(EmailContent.SELECT_SALUTATION).first().append("Dear ");
		String offerURL = "http://www.offerdiary.com/getOfferDetail.do?id=" + offer.getUniqueId();//TODO fix offer detail link
		doc.select("#offerDetail").first().append(offer.getDescription());
		//doc.select("#offerCode").first().append(offer.getOfferCode());
		doc.select("#vendor").first().append(offer.getTargetVendor().getName());
		doc.select("#offerLink").first().append("<a href=\"" + offerURL + "\" target=\"_blank\" style=\"color:#0088CC;\">Here is the offer</a>");
		long numberOfDays = (offer.getExpiryDateInMillis() - System.currentTimeMillis()) / CommonUtilities.MILLIS_IN_A_DAY;
		doc.select("#numberOfDays").first().append(numberOfDays + "");
		return doc.toString();
	}

	@Override
	protected void generateEmailHTMLTemplate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setContentInMessageHTML(EmailContentParam content) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSubject() {
		subject = DEFAULT_NOTIFICATION_SUBJECT + offer.getTargetVendor().getName();

	}

	@Override
	public EmailType getEmailType() {
		return EmailType.SHARE_OFFER_EMAIL;
	}

	@Override
	public String getToAddress() {
		return user.getEmailId();
	}

	@Override
	public String getSubject() {
		return DEFAULT_NOTIFICATION_SUBJECT + offer.getTargetVendor().getName();
	}
}
