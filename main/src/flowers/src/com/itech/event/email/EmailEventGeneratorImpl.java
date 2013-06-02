package com.itech.event.email;

import com.itech.event.services.EventEngine;
import com.itech.event.vo.EmailEvent;
import com.itech.event.vo.EmailEvent.EmailEventType;
import com.itech.event.vo.Event;

public class EmailEventGeneratorImpl implements EmailEventGenerator {
	private EventEngine eventEngine;

	@Override
	public void asyncMailSent() {
		Event event = new EmailEvent(EmailEventType.ASYNC_MAIL_SENT);
		eventEngine.handleEvent(event);
	}

	public void setEventEngine(EventEngine eventEngine) {
		this.eventEngine = eventEngine;
	}

	public EventEngine getEventEngine() {
		return eventEngine;
	}
}
