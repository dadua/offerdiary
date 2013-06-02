package com.itech.event.user;

import com.itech.event.services.EventHandler;
import com.itech.event.vo.Event;
import com.itech.event.vo.UserEvent;
import com.itech.event.vo.UserEvent.UserEventType;

public class UserEventHandler implements EventHandler {

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
		} else if (UserEventType.NEW_USER_SUBSCRIBED.equals(userEvent.getUserEventType())){
			handleNewUserSubscribed(userEvent);
		}
	}

	private void handleNewUserSubscribed(UserEvent userEvent) {
	}


	private void handleUserForgotPassword(UserEvent userEvent) {
	}

	private void handleUserAdded(UserEvent userEvent) {
	}

}