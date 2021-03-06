package com.itech.email.vo;

import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.itech.common.CommonFileUtilities;
import com.itech.email.vo.Email.EmailType;

public class OfferExpiryNotificationEmail extends Email{

	private final Logger logger = Logger.getLogger(OfferExpiryNotificationEmail.class);
	private static final String DEFAULT_NOTIFICATION_SUBJECT="OfferDo Offer Expiry Notification";
	private String NOTIFICATION_EMAIL_HTML;
	private static final String EMAIL_CONTENT_FILE ="index.html";
	private Document docHTML = null;


	public OfferExpiryNotificationEmail(String messageContent, String toEmailId,  List<String> fileAttachementList){
		this(messageContent, toEmailId);
		setAttachments(fileAttachementList);
	}

	public OfferExpiryNotificationEmail(String messageContent, String toEmailId){
		super(messageContent, toEmailId);
	}

	public OfferExpiryNotificationEmail(){
		super();
	}

	@Override
	public String getMailContent() {
		return NOTIFICATION_EMAIL_HTML;
	}

	@Override
	protected void generateEmailHTMLTemplate() {
		docHTML = Jsoup.parse(CommonFileUtilities.getResourceFileAsString(EMAIL_CONTENT_FILE));

		NOTIFICATION_EMAIL_HTML = docHTML.toString();
	}

	@Override
	public void setContentInMessageHTML(String message) {
		if(null != docHTML){
			Element para = docHTML.select("#mail-message").first();
			para.html("<singleline label=\"Title\">"+message+"</singleline>");
			NOTIFICATION_EMAIL_HTML = docHTML.toString();
		}
	}

	@Override
	public void setSubject() {
		this.subject = DEFAULT_NOTIFICATION_SUBJECT;
	}
	
	@Override
	public EmailType getEmailType() {
		return EmailType.OFFER_EXPIRY_NOTIFICATION_EMAIL;
	}


}
