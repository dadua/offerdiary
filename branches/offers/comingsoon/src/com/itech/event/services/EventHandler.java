package com.itech.event.services;

import com.itech.event.vo.Event;

public interface EventHandler {
	public boolean handles(Event event);
	public void handle(Event event);
}
