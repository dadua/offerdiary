package com.itech.email.services;

import java.io.File;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;


public class EmailManagerImpl implements EmailManager {
	private JavaMailSenderImpl mailSender;
	Logger logger = Logger.getLogger(EmailManagerImpl.class);
	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public boolean sendMail(String from, String to, String subject, String htmlMsg) {
		try{
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			setMailContent(helper, from, to, subject, htmlMsg);
			mailSender.send(message);
		}catch(MessagingException me){
			logger.debug("Email sending failed for mail:  from ["+ from +"] ; to ["+to+"]" );
			return false;
		}
		return true;
	}


	@Override
	public boolean sendMailWithAttachement(String from, String to, String subject, String htmlMsg, List<File> attachmentFiles) {
		try{
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = null;
			helper = getAttachementMineMessageHelper(message);
			setMailContent(helper, from, to, subject, htmlMsg);
			addAttachements(helper, attachmentFiles);
			mailSender.send(message);
		}catch(MessagingException me){
			logger.debug("Email sending failed for mail:  from ["+ from +"] ; to ["+to+"]" );
			return false;
		}
		return true;
	}

	private MimeMessageHelper getAttachementMineMessageHelper (
			MimeMessage message) throws MessagingException{
		MimeMessageHelper mimeMessageHelper = null;
		mimeMessageHelper = new MimeMessageHelper(message, true);
		return mimeMessageHelper;
	}

	private void addAttachements(MimeMessageHelper helper,
			List<File> attachmentFiles) throws MessagingException{
		for(File file : attachmentFiles){
			FileSystemResource attfile = new FileSystemResource(file);
			helper.addAttachment(file.getName(), attfile);
		}
	}

	private void setMailContent(MimeMessageHelper helper, String from, String to, String subject, String htmlMsg) throws MessagingException{
		helper.setFrom(from);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(htmlMsg, true);
	}
}

