package com.itech.email.vo;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.itech.common.CommonFileUtilities;

public class PasswordRecoverEmail extends EmailMessage{

	private final Logger logger = Logger.getLogger(PasswordRecoverEmail.class);
	private static final String DEFAULT_PASSWORD_RECOVERY_SUBJECT="OfferDo Offer Summary Notification";
	private String PASSWORD_RECOVERY_EMAIL_HTML;
	private static final String EMAIL_CONTENT_FILE ="index.html";
	private Document docHTML = null;

	public PasswordRecoverEmail(String messageContent, String toEmailId, List<String> fileAttachementList){
		this(messageContent, toEmailId);
		setAttachments(fileAttachementList);
	}

	public PasswordRecoverEmail(String messageContent, String toEmailId){
		super(messageContent, toEmailId);
	}

	public PasswordRecoverEmail(){
		super();
	}

	@Override
	public String getMailContent() {
		return PASSWORD_RECOVERY_EMAIL_HTML;
	}

	@Override
	protected void generateEmailHTMLTemplate() {
		try {
			docHTML = Jsoup.parse(CommonFileUtilities.getResourceFileAsString(EMAIL_CONTENT_FILE));
		} catch (IOException e) {
			logger.debug("PasswordRecoverEmail HTML could not be generated");
			e.printStackTrace();
		}
		PASSWORD_RECOVERY_EMAIL_HTML = docHTML.toString();
	}

	@Override
	public void setContentInMessageHTML(String message) {
		if(null != docHTML){
			Element para = docHTML.select("#mail-message").first();
			para.html("<singleline label=\"Title\">"+message+"</singleline>");
			PASSWORD_RECOVERY_EMAIL_HTML = docHTML.toString();
		}
	}

	@Override
	public void setSubject() {
		this.subject = DEFAULT_PASSWORD_RECOVERY_SUBJECT;
	}
}
