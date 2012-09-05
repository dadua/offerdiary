package com.itech.email.vo;

import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.itech.common.CommonFileUtilities;

public class NewUserSubscriptionEmail extends Email{

	private static final String DEFAULT_SUBSCRIPTION_SUBJECT="OfferDo Subscription !";
	private String SUBSCRIPTION_EMAIL_HTML;
	private final EmailContent defaultEmailContent = new EmailContent(); 
	private static final String EMAIL_CONTENT_FILE ="resources\\generic_html_template.html";
	private Document docHTML = null;

	
	public NewUserSubscriptionEmail(){
		super();
	}
	
	public NewUserSubscriptionEmail(EmailContentParam contentParam,String toEmailId){
		super(contentParam, toEmailId);
	}

	public NewUserSubscriptionEmail(EmailContentParam contentParam, String toEmailId,  List<String> fileAttachementList){
		this(contentParam, toEmailId);
		setAttachments(fileAttachementList);
	}


	@Override
	public String getMailContent() {
		return getSUBSCRIPTION_EMAIL_HTML();
	}

	@Override
	protected void generateEmailHTMLTemplate() {
		setDocHTML(Jsoup.parse(CommonFileUtilities.getResourceFileAsString(EMAIL_CONTENT_FILE)));
		createDefaultEmailContent();
	}

	private void createDefaultEmailContent() {
		EmailContent content = getDefaultEmailContent();
		content.setSubHeading("Welcome to Offer Diary !!");
		content.setSalutation("Dear");
		content.setName("Subscriber");
		content.setPara1("Thank you for signing up for OfferDiary closed beta. You have taken the first step to manage your offers"+
		"and loyalty credits effectively. We are working out to bring the best offer management solution to you with OfferDiary and"+
		"will send you a sign up invitation soon!");
		content.setPara2("Wait for it... We are sure, you are going to love it! ");
	}

	@Override
	public void setContentInMessageHTML(EmailContentParam content) {
		setContentParams(content);
		setParamsInHtml();
		setSUBSCRIPTION_EMAIL_HTML(getDocHTML().toString());
	}

	private void setParamsInHtml() {
		Document doc = getDocHTML();
		doc.select(EmailContent.SELECT_SUBHEADING).first().text(getDefaultEmailContent().getSubHeading());
		doc.select(EmailContent.SELECT_NAME).first().text(getDefaultEmailContent().getName());
		doc.select(EmailContent.SELECT_SALUTATION).first().text(getDefaultEmailContent().getSalutation());
		doc.select(EmailContent.SELECT_PARA1).first().text(getDefaultEmailContent().getPara1());
		doc.select(EmailContent.SELECT_PARA2).first().text(getDefaultEmailContent().getPara2());	
	}

	private void setContentParams(EmailContentParam content) {
		
	}

	@Override
	public void setSubject() {
		this.subject = DEFAULT_SUBSCRIPTION_SUBJECT;
	}

	@Override
	public EmailType getEmailType() {
		return EmailType.NEW_USER_SUBSCRIPTION_EMAIL;
	}

	
	
	public static String getDefaultSubscriptionSubject() {
		return DEFAULT_SUBSCRIPTION_SUBJECT;
	}

	public String getSUBSCRIPTION_EMAIL_HTML() {
		return SUBSCRIPTION_EMAIL_HTML;
	}

	public void setSUBSCRIPTION_EMAIL_HTML(String sUBSCRIPTION_EMAIL_HTML) {
		SUBSCRIPTION_EMAIL_HTML = sUBSCRIPTION_EMAIL_HTML;
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

	public EmailContent getDefaultEmailContent() {
		return defaultEmailContent;
	}

}
