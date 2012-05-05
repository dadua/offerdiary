package com.itech.email.services;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.itech.common.CommonFileUtilities;
import com.itech.common.test.CommonTestBase;
public class TestEmailManagerImpl extends CommonTestBase{

	private EmailManager eManager;
	private final static String fromAddress= "croceaten@gmail.com";
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
		String htmlMsg = CommonFileUtilities.getResourceFileAsString(HTML);
		mailResult = eManager.sendMail(fromAddress, toAddress, subject, htmlMsg);
		assertTrue(mailResult);
	}

	public void testSendMailWithAttachement() throws IOException, URISyntaxException {
		boolean mailResult = false;
		String htmlMsg = CommonFileUtilities.getResourceFileAsString(HTML);
		List<File> files = new ArrayList<File>();
		files.add(CommonFileUtilities.getResourceFileAsFile(attachement));
		mailResult = eManager.sendMailWithAttachement(fromAddress, toAddress, subject, htmlMsg, files);
		assertTrue(mailResult);
	}

	@Override
	protected void tearDown() throws Exception {
	}

}
