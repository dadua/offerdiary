package com.itech.email.services;

import java.io.File;
import java.util.List;

public interface EmailManager {
	/**
	 * Send Email ; config taken from beans.xml for now
	 * @param from from email address
	 * @param to to email address
	 * @param subject
	 * @param msg
	 * @return
	 */
	public boolean sendMail(String from, String to, String subject, String msg);
	/**
	 * end Email with attachements; config taken from beans.xml for now
	 * @param from from email address
	 * @param to to email address
	 * @param subject subject
	 * @param htmlMsg
	 * @param attachmentFiles list of Files() to be attached
	 * @return
	 */
	public boolean sendMailWithAttachement(String from, String to, String subject, String htmlMsg, List<File> attachmentFiles);
}
