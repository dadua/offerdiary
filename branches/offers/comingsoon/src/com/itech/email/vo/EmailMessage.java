package com.itech.email.vo;

import java.util.List;

import com.itech.coupon.model.User;

public abstract class EmailMessage {

	String senderAddress;
	String toAddress;
	String subject;
	String mailContent;
	List<String> attachments;

	public EmailMessage(){
		generateEmailHTMLTemplate();
	}

	public EmailMessage(String messageContent, String toEmailId){
		generateEmailHTMLTemplate();
		setContentInMessageHTML(messageContent);
		setToAddress(toEmailId);
		setSenderAddress("test.coupoxo@gmail.com");
		setSubject();
	}

	public EmailMessage(String messageContent, String toEmailId,  List<String> fileAttachementList){
		this(messageContent, toEmailId);
		setAttachments(fileAttachementList);
	}

	public abstract String getMailContent() ;
	protected abstract void generateEmailHTMLTemplate();
	public abstract void setContentInMessageHTML(String message);
	public abstract void setSubject();

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

}
