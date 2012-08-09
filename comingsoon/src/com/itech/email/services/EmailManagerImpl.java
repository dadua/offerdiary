package com.itech.email.services;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.itech.common.CommonFileUtilities;
import com.itech.email.vo.EmailMessage;


public class EmailManagerImpl implements EmailManager {

	private JavaMailSenderImpl mailSender;
	Logger logger = Logger.getLogger(EmailManagerImpl.class);

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public boolean sendEmail(EmailMessage email) {
		String from = email.getSenderAddress();
		String to = email.getToAddress();
		String subject = email.getSubject();
		String htmlMsg = email.getMailContent();
		List<String> attachements = email.getAttachments();
		try{
			MimeMessageHelper helper;
			MimeMessage message = mailSender.createMimeMessage();
			if(null != attachements && !attachements.isEmpty()){
				helper = getAttachementMineMessageHelper(message);
				addAttachements(helper, attachements);
			}else{
				helper = new MimeMessageHelper(message);
			}
			setMailContent(helper, from, to, subject, htmlMsg);
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
			try {
				files.add(CommonFileUtilities.getResourceFileAsFile(fileName));
			} catch (URISyntaxException e) {
				logger.debug("File could not be obtained from attachement name "+ fileName);
				e.printStackTrace();
			}
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

