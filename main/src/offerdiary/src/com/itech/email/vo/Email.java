package com.itech.email.vo;

import java.util.List;

public abstract class Email {

	protected String senderAddress;
	protected String toAddress;
	protected String subject;
	protected EmailContent emailContent;
	protected String mailContent;
	protected List<String> attachments;
	protected EmailType emailType;
	protected static final String defaultSenderEmailAddress="support@offerdiary.com";

	public enum EmailType{
		NEW_USER_REGISTRATION_EMAIL, NEW_USER_SUBSCRIPTION_EMAIL,  OFFER_EXPIRY_NOTIFICATION_EMAIL, OFFER_SUMMARY_NOTIFICATION_EMAIL, PASSWORD_RECOVERY_EMAIL,
		SHARE_OFFER_EMAIL, INVITE_USER_EMAIL

	}

	public Email(){
		generateEmailHTMLTemplate();
	}

	public Email(EmailContentParam emailContent, String toEmailId){
		generateEmailHTMLTemplate();
		setContentInMessageHTML(emailContent);
		setToAddress(toEmailId);
		setSenderAddress(defaultSenderEmailAddress);
		setSubject();
	}

	public Email(EmailContentParam emailContent, String toEmailId, List<String> fileAttachementList){
		this(emailContent, toEmailId);
		setAttachments(fileAttachementList);
	}

	// to be performed by the sub classes
	public abstract String getMailContent() ;
	protected abstract void generateEmailHTMLTemplate();
	public abstract void setContentInMessageHTML(EmailContentParam content);
	public abstract void setSubject();
	public abstract EmailType getEmailType();



	public String getSenderAddress() {
		return senderAddress;
	}
	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}
	public String getToAddress() {
		return toAddress;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	public String getSubject() {
		return subject;
	}

	public List<String> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<String> attachments) {
		this.attachments = attachments;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = getMailContent();
	}

	public void setEmailType(EmailType emailType) {
		this.emailType = emailType;
	}

	public EmailContent getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(EmailContent emailContent) {
		this.emailContent = emailContent;
	}
}
