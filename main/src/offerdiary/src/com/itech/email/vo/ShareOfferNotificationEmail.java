package com.itech.email.vo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.itech.common.CommonFileUtilities;
import com.itech.offer.model.OfferShare;
import com.itech.user.model.User;
import com.itech.user.vos.ShareOfferActionVO;

public class ShareOfferNotificationEmail extends Email{
	private static final String DEFAULT_SHARE_SUBJECT="Your friend shared a offer for you via Offer Diary";
	private static final String EMAIL_CONTENT_FILE ="resources\\share_offer_email_template.html";

	private final ShareOfferActionVO shareOfferActionVO;
	private final OfferShare offerShare;
	private final User user;

	public ShareOfferNotificationEmail(ShareOfferActionVO shareOfferActionVO, OfferShare offerShare, User user) {
		this.shareOfferActionVO = shareOfferActionVO;
		this.offerShare = offerShare;
		this.user = user;
	}


	@Override
	public String getMailContent() {
		Document doc = Jsoup.parse(CommonFileUtilities.getResourceFileAsString(EMAIL_CONTENT_FILE));
		doc.select(EmailContent.SELECT_SUBHEADING).first().append("Welcome to OfferDiary !");
		doc.select(EmailContent.SELECT_NAME).first().append("User ");
		doc.select(EmailContent.SELECT_SALUTATION).first().append("Dear ");
		String offerDescription = offerShare.getOffer().getDescription();
		String offerShareLink = shareOfferActionVO.getShareOfferURL();

		//TODO add html in share_offer_email_template.html and replace data here
		doc.select("#friendName").first().append(user.getName());
		doc.select("#offerDetail").first().append(offerDescription);
		doc.select("#vendor").first().append(offerShare.getOffer().getSourceVendor().getName());
		doc.select("#offerLink").first().append("<a href=\"" + offerShareLink + "\" target=\"_blank\" style=\"color:#0088CC;\">Here is the offer</a>");

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
		subject = DEFAULT_SHARE_SUBJECT;

	}

	@Override
	public EmailType getEmailType() {
		return EmailType.SHARE_OFFER_EMAIL;
	}

	@Override
	public String getToAddress() {
		return shareOfferActionVO.getEmailIds();
	}

	@Override
	public String getSubject() {
		return DEFAULT_SHARE_SUBJECT;
	}

}
