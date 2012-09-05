package com.itech.email.vo;

import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.itech.common.CommonFileUtilities;
import com.itech.email.vo.Email.EmailType;

public class OfferExpiryNotificationEmail extends Email{

	private static final String DEFAULT_NOTIFICATION_SUBJECT="OfferDo Offer Expiry Notification";
	private String NOTIFICATION_EMAIL_HTML;
	private static final String EMAIL_CONTENT_FILE ="resources\\generic_html_template.html";
	private Document docHTML = null;


	public OfferExpiryNotificationEmail(){
		super();
	}

	public OfferExpiryNotificationEmail(EmailContentParam contentParam, String toEmailId){
		super(contentParam, toEmailId);
	}
	
	public OfferExpiryNotificationEmail(EmailContentParam contentParam, String toEmailId,  List<String> fileAttachementList){
		this(contentParam, toEmailId);
		setAttachments(fileAttachementList);
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

	public static String getDefaultNotificationSubject() {
		return DEFAULT_NOTIFICATION_SUBJECT;
	}

	public String getNOTIFICATION_EMAIL_HTML() {
		return NOTIFICATION_EMAIL_HTML;
	}

	public void setNOTIFICATION_EMAIL_HTML(String nOTIFICATION_EMAIL_HTML) {
		NOTIFICATION_EMAIL_HTML = nOTIFICATION_EMAIL_HTML;
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

}
