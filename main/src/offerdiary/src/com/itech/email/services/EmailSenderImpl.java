package com.itech.email.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.itech.common.CommonFileUtilities;
import com.itech.common.CommonUtilities;

public class EmailSenderImpl implements EmailSender {

	private JavaMailSenderImpl mailSender;
	Logger logger = Logger.getLogger(EmailManagerImpl.class);

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}


	public boolean sendEmail(String fromAddress, String toAddress, String subject, String messageContent, List<String> attachements) {
		try{
			if (CommonUtilities.isNullOrEmpty(toAddress)) {
				logger.warn("trying to send email to blank email id.");
				logger.debug("trying to send email to blank email id. subject: " + subject);
			}
			MimeMessageHelper helper;
			MimeMessage message = mailSender.createMimeMessage();
			if(null != attachements && !attachements.isEmpty()){
				helper = getAttachementMineMessageHelper(message);
				addAttachements(helper, attachements);
			}else{
				helper = new MimeMessageHelper(message);
			}
			setMailContent(helper, fromAddress, toAddress, subject, messageContent);
			mailSender.send(message);
		}catch(MessagingException me){
			logger.debug("Email sending failed for mail:  from ["+ fromAddress +"] ; to ["+toAddress+"]" );
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
			List<String> attachmentFileNames) throws MessagingException{
		List<File> attachmentFiles = getAttachementsAsFile(attachmentFileNames);
		for(File file : attachmentFiles){
			FileSystemResource attfile = new FileSystemResource(file);
			helper.addAttachment(file.getName(), attfile);
		}
	}

	private List<File> getAttachementsAsFile(List<String> attachmentFileNames) {
		List<File> files = new ArrayList<File>();
		for(String fileName: attachmentFileNames){
			files.add(CommonFileUtilities.getResourceFileAsFile(fileName, false));
		}
		return files;
	}

	private void setMailContent(MimeMessageHelper helper, String from, String to, String subject, String htmlMsg) throws MessagingException{
		helper.setFrom(from);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(htmlMsg, true);
	}
}
