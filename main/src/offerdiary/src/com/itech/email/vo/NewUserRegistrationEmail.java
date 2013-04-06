package com.itech.email.vo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.itech.common.CommonFileUtilities;
import com.itech.user.model.User;
import com.itech.web.WebConstatnts;

public class NewUserRegistrationEmail extends Email {

	private static final String DEFAULT_REGISTRATION_SUBJECT="OfferDiary New User Registration Confirmation";
	private String registrationEmailHTML;
	private static final String EMAIL_CONTENT_FILE ="resources\\generic_html_template.html";
	private EmailContent defaultEmailContent;
	private Document docHTML = null;
	private final User user;

	public NewUserRegistrationEmail(User user){
		super();
		this.user = user;
	}


	public NewUserRegistrationEmail(User user, EmailContentParam contentParam, String toEmailId){
		super(contentParam, toEmailId);
		this.user = user;
	}

	@Override
	public String getMailContent() {
		String emailVarificationLink = WebConstatnts.getVarifyEmailPageBaseURL() + user.getEmailVarficationAccessCode();
		//TODO to activate your account please varify your email address

		String registrationEmailHTML2 = getRegistrationEmailHTML();
		Document document = Jsoup.parse(registrationEmailHTML2);
		document.select("#messageParagraph3").html("To start off Please <a href=\"" + emailVarificationLink + "\" >Verify this email address</a>");
		return document.toString();
	}

	@Override
	protected void generateEmailHTMLTemplate() {
		setDocHTML(Jsoup.parse(CommonFileUtilities.getResourceFileAsString(EMAIL_CONTENT_FILE)));
		createDefaultEmailContent();
	}

	private void createDefaultEmailContent() {
		EmailContent content = new EmailContent();
		content.setSubHeading("Welcome to OfferDiary !");
		content.setSalutation("Dear");
		content.setName("User");
		content.setPara1("Thank you for signing up for OfferDiary. With offerdiary you'd be able to add offers and receive " +
		"timely notifications. You can share your offers with your friends and spread the word.");
		//You can import you facebook offers " +
		//"to OfferDiary for easy management");
		content.setPara2("With OfferDiary you'd be able to do so much more with your offers. keep exploring !");
		setDefaultEmailContent(content);
	}

	@Override
	public void setContentInMessageHTML(EmailContentParam content) {
		setContentParams(content);
		setParamsInHtml();
		setRegistrationEmailHTML(getDocHTML().toString());
	}

	private void setParamsInHtml() {
		Document doc = getDocHTML();
		doc.select(EmailContent.SELECT_SUBHEADING).first().append(getDefaultEmailContent().getSubHeading());
		doc.select(EmailContent.SELECT_NAME).first().append(getDefaultEmailContent().getName());
		doc.select(EmailContent.SELECT_SALUTATION).first().append(getDefaultEmailContent().getSalutation());
		doc.select(EmailContent.SELECT_PARA1).first().append(getDefaultEmailContent().getPara1());
		doc.select(EmailContent.SELECT_PARA2).first().append(getDefaultEmailContent().getPara2());
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
		subject = DEFAULT_REGISTRATION_SUBJECT;
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

	public void setDefaultEmailContent(EmailContent defaultEmailContent) {
		this.defaultEmailContent = defaultEmailContent;
	}

}
