package com.itech.email.vo;

import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.itech.common.CommonFileUtilities;
import com.itech.email.vo.Email.EmailType;

public class NewUserRegistrationEmail extends Email{

	private static final String DEFAULT_REGISTRATION_SUBJECT="OfferDo New User Registration Confirmation";
	private String registrationEmailHTML;
	private static final String EMAIL_CONTENT_FILE ="resources\\generic_html_template.html";
	private final EmailContent defaultEmailContent = new EmailContent();
	private Document docHTML = null;

	public NewUserRegistrationEmail(){
		super();
	}
	
	public NewUserRegistrationEmail(EmailContentParam contentParam, String toEmailId,List<String> fileAttachementList){
		this(contentParam, toEmailId);
		setAttachments(fileAttachementList);
	}

	public NewUserRegistrationEmail(EmailContentParam contentParam, String toEmailId){
		super(contentParam, toEmailId);
	}

	@Override
	public String getMailContent() {
		return getRegistrationEmailHTML();
	}

	@Override
	protected void generateEmailHTMLTemplate() {
		setDocHTML(Jsoup.parse(CommonFileUtilities.getResourceFileAsString(EMAIL_CONTENT_FILE)));
		createDefaultEmailContent();
	}

	private void createDefaultEmailContent() {
		EmailContent content = getDefaultEmailContent();
		content.setSubHeading("Welcome to OfferDiary !");
		content.setSalutation("Dear");
		content.setName("User");
		content.setPara1("Thank you for signing up for OfferDiary. With offerdiary you'd be able to add offers and reveieve" +
				"timely notifications. You can share your offers with your friends and spread the word. You can import you facebook offers+" +
				"to OfferDiary for easy management");
		content.setPara2("With OfferDiary you'd be able to do so much more with your offers. keep exploring !");
	}

	@Override
	public void setContentInMessageHTML(EmailContentParam content) {
		setContentParams(content);
		setParamsInHtml();
		setRegistrationEmailHTML(getDocHTML().toString());		
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
		if(null != getDocHTML()){
			String name = content.getPARAM_MAP().get(EmailContentParam.NEW_USER_REG_PARAM_NAME); 
			if( name != null){
					getDefaultEmailContent().setName(name);
			}
		}
	}

	@Override
	public void setSubject() {
		this.subject = DEFAULT_REGISTRATION_SUBJECT;
	}

	@Override
	public EmailType getEmailType() {
		return EmailType.NEW_USER_REGISTRATION_EMAIL;
	}

	
	public static String getDefaultRegistrationSubject() {
		return DEFAULT_REGISTRATION_SUBJECT;
	}

	public static String getEmailContentFile() {
		return EMAIL_CONTENT_FILE;
	}

	public String getRegistrationEmailHTML() {
		return registrationEmailHTML;
	}

	public void setRegistrationEmailHTML(String registrationEmailHTML) {
		this.registrationEmailHTML = registrationEmailHTML;
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
