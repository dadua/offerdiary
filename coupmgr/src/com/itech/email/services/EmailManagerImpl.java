package com.itech.email.services;

import java.util.List;

import com.itech.email.dao.EmailMessagesDAO;
import com.itech.email.model.EmailMessages;
import com.itech.email.model.EmailMessages.EmailStatus;
import com.itech.email.vo.Email;
import com.itech.email.vo.Email.EmailType;

public class EmailManagerImpl implements EmailManager {

	private EmailSender emailSender;
	private EmailMessagesDAO emailMessagesDAO;
	
	@Override
	public boolean sendEmailSync(Email email) {
		String fromAddress = email.getSenderAddress();
		String toAddress =  email.getToAddress();
		String subject = email.getSubject();
		String messageContent = email.getMailContent();
		List<String> attachements = email.getAttachments();
		return emailSender.sendEmail(fromAddress, toAddress, subject, messageContent, attachements);
	}

	@Override
	public boolean sendEmailAsync(Email email) {
		String fromAddress = email.getSenderAddress();
		String toAddress =  email.getToAddress();
		String subject = email.getSubject();
		String messageContent = email.getMailContent();
		EmailType type = email.getEmailType();
		EmailMessages emailMessage = new EmailMessages(fromAddress, toAddress, subject, messageContent, type, EmailStatus.PENDING);
		getEmailMessagesDAO().addOrUpdate(emailMessage);
		return true;
	}

	public EmailSender getEmailSender() {
		return emailSender;
	}

	public void setEmailSender(EmailSender emailSender) {
		this.emailSender = emailSender;
	}

	public EmailMessagesDAO getEmailMessagesDAO() {
		return emailMessagesDAO;
	}

	public void setEmailMessagesDAO(EmailMessagesDAO emailMessagesDAO) {
		this.emailMessagesDAO = emailMessagesDAO;
	}

}

