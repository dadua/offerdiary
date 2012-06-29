package com.itech.email.services;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.itech.common.CommonFileUtilities;
import com.itech.common.test.CommonTestBase;
import com.itech.email.vo.EmailMessage;
import com.itech.email.vo.OfferExpiryNotificationEmail;
public class TestEmailManagerImpl extends CommonTestBase{

	private EmailManager eManager;
	private final static String fromAddress= "test.coupoxo@gmail.com";
	private final static String toAddress= "test.coupoxo@gmail.com";
	private final static String subject = "Test Mail :"+System.currentTimeMillis();
	private final static String HTML = "index.html";
	private final String attachement = "screenshot.jpg";
	@Override
	protected void setUp() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("resources\\test-beans.xml");
		eManager = (EmailManagerImpl) context.getBean("emailManager");
	}

	public void testSendMail() throws IOException {
		boolean mailResult = false;
		EmailMessage email = new OfferExpiryNotificationEmail();
		email.setToAddress(toAddress);
		email.setSubject(subject);
		email.setSenderAddress(fromAddress);
		email.setContentInMessageHTML("your offer is expiring test");
		mailResult = eManager.sendEmail(email);
		assertTrue(mailResult);
	}

	public void testSendMailWithAttachement() throws IOException, URISyntaxException {
		boolean mailResult = false;
		String htmlMsg = CommonFileUtilities.getResourceFileAsString(HTML);
		EmailMessage email = new OfferExpiryNotificationEmail();
		email.setToAddress(htmlMsg);
		email.setSubject(htmlMsg);
		email.setSenderAddress(fromAddress);
		email.setMailContent("your offer is expiring test");
		List<String> fileList = new ArrayList<String>();
		fileList.add(attachement);
		email.setAttachments(fileList);
		mailResult = eManager.sendEmail(email);
		assertTrue(mailResult);
	}

	@Override
	protected void tearDown() throws Exception {
	}

}
