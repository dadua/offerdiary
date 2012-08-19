package com.itech.email.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.itech.common.db.hibernate.HibernateSessionFactory;
import com.itech.common.services.Initialize;
import com.itech.email.model.EmailMessages;
import com.itech.email.model.EmailMessages.EmailStatus;
import com.itech.event.services.EventHandler;
import com.itech.event.vo.EmailEvent;
import com.itech.event.vo.Event;

public class EmailDispatcher implements Initialize, Runnable, EventHandler{
	private final Logger logger = Logger.getLogger(EmailDispatcher.class);
	private HibernateSessionFactory hibernateSessionFactory;
	private EmailManager emailManager;
	private EmailSender emailSender;

	@Override
	public void run() {
		while(true){
			try{
				getHibernateSessionFactory().openNewSession();
				List<EmailMessages> emailMessages =  getEmailManager().getAllPendingEmailMessages();
				if(null != emailMessages && !emailMessages.isEmpty()){
					markEmailMessagesPickedInProgress(emailMessages);
					getHibernateSessionFactory().commitCurrentTransaction();
					sendEmail(emailMessages);
					markEmailInProgresAsPending();
					getHibernateSessionFactory().commitCurrentTransaction();
				}else{
					synchronized (this) {
						try {
							this.wait(60000l);
						} catch (InterruptedException e) {
							logger.error("Email dispatcher thread interuptted ", e);
						}
					}
				}
			}finally{
				getHibernateSessionFactory().closeCurrentSession();
			}
		}
	}

	private void markEmailMessagesPickedInProgress(
			List<EmailMessages> emailMessages) {
		for(EmailMessages emailMessage: emailMessages){
			emailMessage.setStatus(EmailStatus.INPROGRESS);
		}
		getEmailManager().addOrUpdateEmailMessages(emailMessages);
	}

	private void markEmailInProgresAsPending() {

	}

	private void sendEmail(List<EmailMessages> emailMessages) {
		for(EmailMessages emailMessage: emailMessages){
			String fromAddress = emailMessage.getFromAddress();
			String toAddress = emailMessage.getToAddress();
			String subject = emailMessage.getSubject();
			String messageContent = emailMessage.getMessageContent();
			List<String> attachements=null;
			emailMessage.updateTryCount();
			boolean emailSend  =getEmailSender().sendEmail(fromAddress, toAddress, subject, messageContent, attachements);
			if(emailSend){
				getEmailManager().deleteEmailMessages(emailMessage);
				getHibernateSessionFactory().commitCurrentTransaction();
			}
		}
	}

	@Override
	public void init() {
		Thread emailDispatcherThread = new Thread(this);
		emailDispatcherThread.start();
	}

	@Override
	public boolean handles(Event event) {
		if (EmailEvent.class.isAssignableFrom(event.getClass()))
			return true;
		return false;
	}

	@Override
	public void handle(Event event) {
		synchronized (this) {
			this.notifyAll();
		}
	}

	public HibernateSessionFactory getHibernateSessionFactory() {
		return hibernateSessionFactory;
	}

	public void setHibernateSessionFactory(HibernateSessionFactory hibernateSessionFactory) {
		this.hibernateSessionFactory = hibernateSessionFactory;
	}

	public EmailSender getEmailSender() {
		return emailSender;
	}

	public void setEmailSender(EmailSender emailSender) {
		this.emailSender = emailSender;
	}

	public EmailManager getEmailManager() {
		return emailManager;
	}

	public void setEmailManager(EmailManager emailManager) {
		this.emailManager = emailManager;
	}
}
