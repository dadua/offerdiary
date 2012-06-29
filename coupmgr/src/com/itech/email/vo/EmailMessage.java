package com.itech.email.vo;

import java.util.List;

public abstract class EmailMessage {

	String senderAddress;
	String toAddress;
	String subject;
	String mailContent;
	List<String> attachments;

	public EmailMessage(){
		generateEmailHTMLTemplate();
	}

	public abstract String getMailContent() ;
	protected abstract void generateEmailHTMLTemplate();
	public abstract void setContentInMessageHTML(String message);

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
	public void setSubject(String subject) {
		this.subject = subject;
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
