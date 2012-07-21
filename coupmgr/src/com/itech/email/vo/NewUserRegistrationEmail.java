package com.itech.email.vo;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.itech.common.CommonFileUtilities;
import com.itech.coupon.model.User;

public class NewUserRegistrationEmail extends EmailMessage{

	private final Logger logger = Logger.getLogger(NewUserRegistrationEmail.class);
	private static final String DEFAULT_REGISTRATION_SUBJECT="OfferDo New User Registration Confirmation";
	private static final String DEFAULT_NEW_USER_ADDED_REGISTRATION_MESSAGE="Welcome to OfferDo ! ";
	private String REGISTRATIONN_EMAIL_HTML;
	private static final String EMAIL_CONTENT_FILE ="index.html";
	private Document docHTML = null;

	public NewUserRegistrationEmail(String messageContent, String toEmailId,List<String> fileAttachementList){
		this(messageContent, toEmailId);
		setAttachments(fileAttachementList);
	}

	public NewUserRegistrationEmail(String messageContent, String toEmailId){
		super(messageContent, toEmailId);
	}

	public NewUserRegistrationEmail(){
		super();
	}
	
	@Override
	public String getMailContent() {
		return REGISTRATIONN_EMAIL_HTML;
	}

	@Override
	protected void generateEmailHTMLTemplate() {
		try {
			docHTML = Jsoup.parse(CommonFileUtilities.getResourceFileAsString(EMAIL_CONTENT_FILE));
		} catch (IOException e) {
			logger.debug("NewUserRegistrationEmail HTML could not be generated");
			e.printStackTrace();
		}
		REGISTRATIONN_EMAIL_HTML = docHTML.toString();
	}

	@Override
	public void setContentInMessageHTML(String message) {
		if(null != docHTML){
			Element para = docHTML.select("#mail-message").first();
			para.html("<singleline label=\"Title\">"+message+"</singleline>");
			REGISTRATIONN_EMAIL_HTML = docHTML.toString();
		}
	}

	@Override
	public void setSubject() {
		this.subject = DEFAULT_REGISTRATION_SUBJECT;
	}

}
