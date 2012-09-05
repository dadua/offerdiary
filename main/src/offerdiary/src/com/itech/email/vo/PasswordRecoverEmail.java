package com.itech.email.vo;

import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.itech.common.CommonFileUtilities;
import com.itech.email.vo.Email.EmailType;

public class PasswordRecoverEmail extends Email{

	
	private static final String DEFAULT_PASSWORD_RECOVERY_SUBJECT="Your OfferDiary Credentials";
	private String PASSWORD_RECOVERY_EMAIL_HTML;
	private static final String EMAIL_CONTENT_FILE ="resources\\generic_html_template.html";
	private final EmailContent defaultEmailContent = new EmailContent(); 
	private Document docHTML = null;

	
	public PasswordRecoverEmail(){
		super();
	}

	public PasswordRecoverEmail(EmailContentParam emailContent, String toEmailId){
		super(emailContent, toEmailId);
	}

	public PasswordRecoverEmail(EmailContentParam emailContent, String toEmailId, List<String> fileAttachementList){
		this(emailContent, toEmailId);
		setAttachments(fileAttachementList);
	}

	@Override
	public String getMailContent() {
		return getPASSWORD_RECOVERY_EMAIL_HTML();
	}

	@Override
	protected void generateEmailHTMLTemplate() {
		setDocHTML(Jsoup.parse(CommonFileUtilities.getResourceFileAsString(EMAIL_CONTENT_FILE)));
		createDefaultEmailContent();
	}

	private void createDefaultEmailContent() {
		EmailContent content = getDefaultEmailContent();
		content.setSubHeading("Your OfferDiary Credentials");
		content.setSalutation("Dear");
		content.setName("User");
		content.setPara1("Your current password is ");
		content.setPara2("Access your Offerdairy account here <a title='OfferDiary' href='"+"http://www.offerdiary.com"+"'>OfferDiary</a>");
	}

	@Override
	public void setContentInMessageHTML(EmailContentParam content) {
		setContentParams(content);
		setParamsInHtml();
		setPASSWORD_RECOVERY_EMAIL_HTML(getDocHTML().toString());
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
			String name = content.getPARAM_MAP().get(EmailContentParam.FORGOT_PASSWORD_PARAM_NAME); 
			String password = (String) content.getPARAM_MAP().get(EmailContentParam.FORGOT_PASSWORD_PARAM_PASSWORD);
			if( name != null){
					getDefaultEmailContent().setName(name);
			}if(password != null){
					getDefaultEmailContent().setPara1(getDefaultEmailContent().getPara1() +password);
			}
		}
	}

	@Override
	public void setSubject() {
		this.subject = DEFAULT_PASSWORD_RECOVERY_SUBJECT;
	}
	
	@Override
	public EmailType getEmailType() {
		return EmailType.PASSWORD_RECOVERY_EMAIL;
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

	public String getPASSWORD_RECOVERY_EMAIL_HTML() {
		return PASSWORD_RECOVERY_EMAIL_HTML;
	}

	public void setPASSWORD_RECOVERY_EMAIL_HTML(
			String pASSWORD_RECOVERY_EMAIL_HTML) {
		PASSWORD_RECOVERY_EMAIL_HTML = pASSWORD_RECOVERY_EMAIL_HTML;
	}
}
