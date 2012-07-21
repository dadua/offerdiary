package com.itech.event.user;

import com.itech.coupon.model.User;
import com.itech.event.services.EventEngine;
import com.itech.event.vo.Event;
import com.itech.event.vo.UserEvent;
import com.itech.event.vo.UserEvent.UserEventType;

public class UserEventGeneratorImpl implements UserEventGenerator{
	private EventEngine eventEngine;
	
	@Override
	public void newUserAdded(User user) {
		Event event = new UserEvent(UserEventType.NEW_USER_ADDED, user);
		eventEngine.handleEvent(event);
	}

	@Override
	public void forgotPassword(User user) {
		Event event = new UserEvent(UserEventType.FORGOT_PASSWORD, user);
		eventEngine.handleEvent(event);
	}

	public EventEngine getEventEngine() {
		return eventEngine;
	}

	public void setEventEngine(EventEngine eventEngine) {
		this.eventEngine = eventEngine;
	}
}
