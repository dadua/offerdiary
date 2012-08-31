package com.itech.email.services;

import java.util.List;

import com.itech.email.dao.EmailMessagesDAO;
import com.itech.email.model.EmailMessages;
import com.itech.email.model.EmailMessages.EmailStatus;
import com.itech.email.vo.Email;
import com.itech.email.vo.Email.EmailType;
import com.itech.event.email.EmailEventGenerator;

public class EmailManagerImpl implements EmailManager {

	private EmailSender emailSender;
	private EmailMessagesDAO emailMessagesDAO;
	private EmailEventGenerator emailEventGenerator;
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
		getEmailEventGenerator().asyncMailSent();
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

	@Override
	public List<EmailMessages> getAllPendingEmailMessages() {
		return getEmailMessagesDAO().getAllPendingEmailMessages();
	}

	@Override
	public List<EmailMessages> getPendingEmailMessagesByType(EmailType type) {
		return getEmailMessagesDAO().getPendingEmailMessagesByType(type);
	}

	@Override
	public boolean addOrUpdateEmailMessages(List<EmailMessages> emailMessages) {
		return getEmailMessagesDAO().addOrUpdate(emailMessages);
	}

	@Override
	public boolean deleteEmailMessages(List<EmailMessages> emailMessages) {
		return getEmailMessagesDAO().delete(emailMessages);
	}

	@Override
	public boolean deleteEmailMessages(EmailMessages emailMessages) {
		return getEmailMessagesDAO().delete(emailMessages);
	}

	public EmailEventGenerator getEmailEventGenerator() {
		return emailEventGenerator;
	}

	public void setEmailEventGenerator(EmailEventGenerator emailEventGenerator) {
		this.emailEventGenerator = emailEventGenerator;
	}

}

