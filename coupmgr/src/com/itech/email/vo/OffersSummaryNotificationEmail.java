package com.itech.email.vo;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.itech.common.CommonFileUtilities;

public class OffersSummaryNotificationEmail extends EmailMessage{

	private final Logger logger = Logger.getLogger(OffersSummaryNotificationEmail.class);
	private static final String DEFAULT_SUMMARY_NOTIFICATION_SUBJECT="OfferDo Offer Summary Notification";
	private String OFFER_SUMMARY_EMAIL_HTML;
	private static final String EMAIL_CONTENT_FILE ="index.html";
	private Document docHTML = null;

	public OffersSummaryNotificationEmail(String messageContent, String toEmailId, List<String> fileAttachementList){
		this(messageContent, toEmailId);
		setAttachments(fileAttachementList);
	}
	public OffersSummaryNotificationEmail(String messageContent, String toEmailId){
		super(messageContent, toEmailId);
	}

	public OffersSummaryNotificationEmail(){
		super();
	}

	@Override
	public String getMailContent() {
		return OFFER_SUMMARY_EMAIL_HTML;
	}

	@Override
	protected void generateEmailHTMLTemplate() {
		try {
			docHTML = Jsoup.parse(CommonFileUtilities.getResourceFileAsString(EMAIL_CONTENT_FILE));
		} catch (IOException e) {
			logger.debug("OffersSummaryNotificationEmail HTML could not be generated");
			e.printStackTrace();
		}
		OFFER_SUMMARY_EMAIL_HTML = docHTML.toString();
	}

	@Override
	public void setContentInMessageHTML(String message) {
		if(null != docHTML){
			Element para = docHTML.select("#mail-message").first();
			para.html("<singleline label=\"Title\">"+message+"</singleline>");
			OFFER_SUMMARY_EMAIL_HTML = docHTML.toString();
		}
	}

	@Override
	public void setSubject() {
		this.subject = DEFAULT_SUMMARY_NOTIFICATION_SUBJECT;
	}

}
