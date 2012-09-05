package com.itech.event.user;

import java.util.HashMap;
import java.util.Map;

import com.itech.email.services.EmailManager;
import com.itech.email.vo.Email;
import com.itech.email.vo.EmailContent;
import com.itech.email.vo.EmailContentParam;
import com.itech.email.vo.NewUserRegistrationEmail;
import com.itech.email.vo.NewUserSubscriptionEmail;
import com.itech.email.vo.PasswordRecoverEmail;
import com.itech.event.services.EventHandler;
import com.itech.event.vo.Event;
import com.itech.event.vo.UserEvent;
import com.itech.event.vo.UserEvent.UserEventType;

public class UserEventHandler implements EventHandler {

	private EmailManager emailManager;
	@Override
	public boolean handles(Event event) {
		if (UserEvent.class.isAssignableFrom(event.getClass()))
			return true;

		return false;
	}

	@Override
	public void handle(Event event) {
		UserEvent userEvent = (UserEvent) event;
		if (UserEventType.NEW_USER_ADDED.equals(userEvent.getUserEventType())) {
			handleUserAdded(userEvent);
		} else if (UserEventType.FORGOT_PASSWORD.equals(userEvent.getUserEventType())) {
			handleUserForgotPassword(userEvent);
		} else if (UserEventType.NEW_USER_SUBSCRIBED.equals(userEvent.getUserEventType())){
			handleNewUserSubscribed(userEvent);
		}
	}

	private void handleNewUserSubscribed(UserEvent userEvent) {
		sendNewUserSubscribedEmail(userEvent);
	}

	private void sendNewUserSubscribedEmail(UserEvent userEvent) {
		String toEmailId = userEvent.getUser().getEmailId();
		EmailContentParam contentParam = new EmailContentParam(new HashMap<String, String>());
		Email email = new NewUserSubscriptionEmail(contentParam, toEmailId);
		getEmailManager().sendEmailAsync(email);
	}

	private void handleUserForgotPassword(UserEvent userEvent) {
		sendForgotPasswordEmail(userEvent);
	}

	private void sendForgotPasswordEmail(UserEvent userEvent) {
		String toEmailId = userEvent.getUser().getEmailId();
		EmailContentParam contentParam = new EmailContentParam();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put(EmailContentParam.FORGOT_PASSWORD_PARAM_NAME, userEvent.getUser().getName());
		paramMap.put(EmailContentParam.FORGOT_PASSWORD_PARAM_PASSWORD, userEvent.getUser().getPassword());
		contentParam.setPARAM_MAP(paramMap);
		Email email = new PasswordRecoverEmail(contentParam, toEmailId);
		getEmailManager().sendEmailAsync(email);
	}

	private void handleUserAdded(UserEvent userEvent) {
		sendNewUserRegistrationEmail(userEvent);
	}

	private void sendNewUserRegistrationEmail(UserEvent userEvent) {
		String toEmailId = userEvent.getUser().getEmailId();
		EmailContentParam contentParam = new EmailContentParam();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put(EmailContentParam.NEW_USER_REG_PARAM_NAME, userEvent.getUser().getName());
		contentParam.setPARAM_MAP(paramMap);
		Email email = new NewUserRegistrationEmail(contentParam, toEmailId);
		getEmailManager().sendEmailAsync(email);
	}

	public EmailManager getEmailManager() {
		return emailManager;
	}

	public void setEmailManager(EmailManager emailManager) {
		this.emailManager = emailManager;
	}
}
