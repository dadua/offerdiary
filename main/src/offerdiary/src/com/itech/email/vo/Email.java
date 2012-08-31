package com.itech.email.vo;

import java.util.List;

public abstract class Email {

	String senderAddress;
	String toAddress;
	String subject;
	String mailContent;
	List<String> attachments;
	private EmailType emailType;
	private static final String defaultSenderEmailAddress="support@offerdiary.com";
	
	public enum EmailType{
		NEW_USER_REGISTRATION_EMAIL, NEW_USER_SUBSCRIPTION_EMAIL,  OFFER_EXPIRY_NOTIFICATION_EMAIL, OFFER_SUMMARY_NOTIFICATION_EMAIL, PASSWORD_RECOVERY_EMAIL
	}
	
	public Email(){
		generateEmailHTMLTemplate();
	}

	public Email(String messageContent, String toEmailId){
		generateEmailHTMLTemplate();
		setContentInMessageHTML(messageContent);
		setToAddress(toEmailId);
		setSenderAddress(defaultSenderEmailAddress);
		setSubject();
	}

	public Email(String messageContent, String toEmailId,  List<String> fileAttachementList){
		this(messageContent, toEmailId);
		setAttachments(fileAttachementList);
	}

	public abstract String getMailContent() ;
	protected abstract void generateEmailHTMLTemplate();
	public abstract void setContentInMessageHTML(String message);
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
}
