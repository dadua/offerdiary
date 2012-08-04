package com.itech.email.vo;

import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.itech.common.CommonFileUtilities;

public class NewUserSubscriptionEmail extends EmailMessage{

	private final Logger logger = Logger.getLogger(NewUserRegistrationEmail.class);
	private static final String DEFAULT_SUBSCRIPTION_SUBJECT="OfferDo Subscription !";
	private static final String DEFAULT_NEW_USER_ADDED_SUBSCRIPTION_MESSAGE="Hear More about OfferDo pretty shortly ! ";
	private String SUBSCRIPTION_EMAIL_HTML;
	private static final String EMAIL_CONTENT_FILE ="index.html";
	private Document docHTML = null;

	public NewUserSubscriptionEmail(String messageContent, String toEmailId,  List<String> fileAttachementList){
		this(messageContent, toEmailId);
		setAttachments(fileAttachementList);
	}

	public NewUserSubscriptionEmail(String messageContent, String toEmailId){
		super(messageContent, toEmailId);
	}

	public NewUserSubscriptionEmail(){
		super();
	}

	@Override
	public String getMailContent() {
		return SUBSCRIPTION_EMAIL_HTML;
	}

	@Override
	protected void generateEmailHTMLTemplate() {
		docHTML = Jsoup.parse(CommonFileUtilities.getResourceFileAsString(EMAIL_CONTENT_FILE));
		SUBSCRIPTION_EMAIL_HTML = docHTML.toString();
	}

	@Override
	public void setContentInMessageHTML(String message) {
		if(null == message || message.isEmpty()) {
			message = DEFAULT_NEW_USER_ADDED_SUBSCRIPTION_MESSAGE;
		}
		if(null != docHTML){
			Element para = docHTML.select("#mail-message").first();
			para.html("<singleline label=\"Title\">"+message+"</singleline>");
			SUBSCRIPTION_EMAIL_HTML = docHTML.toString();
		}

	}

	@Override
	public void setSubject() {
		this.subject = DEFAULT_SUBSCRIPTION_SUBJECT;
	}

}
