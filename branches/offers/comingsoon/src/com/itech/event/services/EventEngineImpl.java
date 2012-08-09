package com.itech.event.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.itech.event.vo.Event;

public class EventEngineImpl implements EventEngine {
	private static final Logger logger = Logger.getLogger(EventEngineImpl.class);
	private List<EventHandler> eventHandlers;

	@Override
	public void handleEvent(Event event) {
		for (EventHandler eventHandler : eventHandlers) {
			try {
				if (eventHandler.handles(event)) {
					eventHandler.handle(event);
				}
			}catch (Exception e) {
				logger.error("Error in handling event", e);
			}
		}

	}

	public void setEventHandlers(List<EventHandler> eventHandlers) {
		this.eventHandlers = eventHandlers;
	}

	public List<EventHandler> getEventHandlers() {
		return eventHandlers;
	}

}
