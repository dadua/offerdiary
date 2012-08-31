package com.itech.event.services;

import com.itech.event.vo.Event;

public interface EventEngine {

	public void handleEvent(Event event);

}
