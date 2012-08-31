package com.itech.email.dao;

import java.util.List;

import com.itech.common.db.CommonBaseDAO;
import com.itech.email.model.EmailMessages;
import com.itech.email.vo.Email.EmailType;

public interface EmailMessagesDAO extends CommonBaseDAO<EmailMessages>{
	public List<EmailMessages> getAllPendingEmailMessages();
	public List<EmailMessages> getPendingEmailMessagesByType(EmailType type);
}
