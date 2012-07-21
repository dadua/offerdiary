package com.itech.event.user;

import com.itech.email.services.EmailManager;
import com.itech.email.vo.EmailMessage;
import com.itech.email.vo.NewUserRegistrationEmail;
import com.itech.event.services.EventHandler;
import com.itech.event.vo.Event;
import com.itech.event.vo.UserEvent;
import com.itech.event.vo.UserEvent.UserEventType;

public class UserEventHandler implements EventHandler {

	private EmailManager emailManager;
	@Override
	public boolean handles(Event event) {
		if (UserEvent.class.isAssignableFrom(event.getClass())) {
			return true;
		}

		return false;
	}

	@Override
	public void handle(Event event) {
		UserEvent userEvent = (UserEvent) event;
		if (UserEventType.NEW_USER_ADDED.equals(userEvent.getUserEventType())) {
			handleUserAdded(userEvent);
		} else if (UserEventType.FORGOT_PASSWORD.equals(userEvent.getUserEventType())) {
			handleUserForgotPassword(userEvent);
		}
	}

	private void handleUserForgotPassword(UserEvent userEvent) {
		sendForgotPasswordEmail(userEvent);
	}

	private void sendForgotPasswordEmail(UserEvent userEvent) {
		String newUserRegistrationMessageContent =  "Hi Your current password is";
		String toEmailId = userEvent.getUser().getEmailId();
		EmailMessage email = new NewUserRegistrationEmail(newUserRegistrationMessageContent, toEmailId);
		getEmailManager().sendEmail(email);
	}

	private void handleUserAdded(UserEvent userEvent) {
		sendNewUserRegistrationEmail(userEvent);
	}

	private void sendNewUserRegistrationEmail(UserEvent userEvent) {
		String newUserRegistrationMessageContent =  "Hi New User";
		String toEmailId = userEvent.getUser().getEmailId();
		EmailMessage email = new NewUserRegistrationEmail(newUserRegistrationMessageContent, toEmailId);
		getEmailManager().sendEmail(email);
	}

	public EmailManager getEmailManager() {
		return emailManager;
	}

	public void setEmailManager(EmailManager emailManager) {
		this.emailManager = emailManager;
	}
}
