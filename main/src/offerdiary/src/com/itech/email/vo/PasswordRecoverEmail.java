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
	private EmailContent defaultEmailContent ; 
	private Document docHTML ;

	
	public PasswordRecoverEmail(){
		super();
	}

	public PasswordRecoverEmail(EmailContentParam emailContentParam, String toEmailId){
		super(emailContentParam, toEmailId);
	}

	public PasswordRecoverEmail(EmailContentParam emailContentParam, String toEmailId, List<String> fileAttachementList){
		this(emailContentParam, toEmailId);
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
		EmailContent content = new EmailContent();
		content.setSubHeading("Your OfferDiary Credentials");
		content.setSalutation("Dear");
		content.setName("User");
		content.setPara1("Your current password is ");
		content.setPara2("Access your Offerdairy account here <a title='OfferDiary' href='"+"http://www.offerdiary.com"+"'>OfferDiary</a>");
		setDefaultEmailContent(content);
	}

	@Override
	public void setContentInMessageHTML(EmailContentParam content) {
		setContentParams(content);
		setParamsInHtml();
		setPASSWORD_RECOVERY_EMAIL_HTML(getDocHTML().toString());
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

	public void setDefaultEmailContent(EmailContent defaultEmailContent) {
		this.defaultEmailContent = defaultEmailContent;
	}
}
