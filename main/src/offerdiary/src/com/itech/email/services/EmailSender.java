package com.itech.email.services;

import java.util.List;

public interface EmailSender {
	public boolean sendEmail(String fromAddress, String toAddress, String subject, String messageContent, List<String> attachements);

}
