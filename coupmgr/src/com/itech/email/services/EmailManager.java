package com.itech.email.services;

public interface EmailManager {
	/**
	 * Send Email ; config taken from beans.xml for now
	 * @param from from email address
	 * @param to to email address
	 * @param subject
	 * @param msg
	 */
	public void sendMail(String from, String to, String subject, String msg);
}
