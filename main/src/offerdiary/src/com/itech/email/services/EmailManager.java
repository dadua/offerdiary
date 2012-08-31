package com.itech.email.services;

import java.util.List;

import com.itech.email.model.EmailMessages;
import com.itech.email.vo.Email;
import com.itech.email.vo.Email.EmailType;

public interface EmailManager {

	public boolean addOrUpdateEmailMessages(List<EmailMessages> emailMessages);
	public boolean deleteEmailMessages(List<EmailMessages> emailMessages);
	public boolean deleteEmailMessages(EmailMessages emailMessages);
	public List<EmailMessages> getAllPendingEmailMessages();
	public List<EmailMessages> getPendingEmailMessagesByType(EmailType type);
	public boolean sendEmailSync(Email email);
	public boolean sendEmailAsync(Email email);

}
