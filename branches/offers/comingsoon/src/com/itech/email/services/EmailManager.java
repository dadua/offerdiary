package com.itech.email.services;

import com.itech.email.vo.EmailMessage;

public interface EmailManager {

	public boolean sendEmail(EmailMessage email);

}
