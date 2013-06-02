package com.itech.event.vo;

public class EmailEvent extends Event {

	public enum EmailEventType{
		ASYNC_MAIL_SENT;
	}

	private EmailEventType type;
	public EmailEvent(EmailEventType type){
		this.type = type;
	}

}
