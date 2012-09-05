package com.itech.email.vo;

import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.itech.common.CommonFileUtilities;
import com.itech.email.vo.Email.EmailType;

public class OffersSummaryNotificationEmail extends Email{

	
	private static final String DEFAULT_SUMMARY_NOTIFICATION_SUBJECT="OfferDo Offer Summary Notification";
	private String OFFER_SUMMARY_EMAIL_HTML;
	private static final String EMAIL_CONTENT_FILE ="resources\\generic_html_template.html";
	private Document docHTML = null;

	public OffersSummaryNotificationEmail(){
		super();
	}

	public OffersSummaryNotificationEmail(EmailContentParam contentParam, String toEmailId){
		super(contentParam, toEmailId);
	}

	public OffersSummaryNotificationEmail(EmailContentParam contentParam, String toEmailId, List<String> fileAttachementList){
		this(contentParam, toEmailId);
		setAttachments(fileAttachementList);
	}

	public static String getDefaultSummaryNotificationSubject() {
		return DEFAULT_SUMMARY_NOTIFICATION_SUBJECT;
	}

	public String getOFFER_SUMMARY_EMAIL_HTML() {
		return OFFER_SUMMARY_EMAIL_HTML;
	}

	public void setOFFER_SUMMARY_EMAIL_HTML(String oFFER_SUMMARY_EMAIL_HTML) {
		OFFER_SUMMARY_EMAIL_HTML = oFFER_SUMMARY_EMAIL_HTML;
	}

	public static String getEmailContentFile() {
		return EMAIL_CONTENT_FILE;
	}

	public Document getDocHTML() {
		return docHTML;
	}

	public void setDocHTML(Document docHTML) {
		this.docHTML = docHTML;
	}

	@Override
	public String getMailContent() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public EmailType getEmailType() {
		// TODO Auto-generated method stub
		return null;
	}

}
