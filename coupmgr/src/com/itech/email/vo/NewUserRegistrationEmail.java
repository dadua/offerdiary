package com.itech.email.vo;

public class NewUserRegistrationEmail extends EmailMessage{

	private String NEW_USER_REG_EMAIL_HTML ;

	public String getNEW_USER_REG_EMAIL_HTML() {
		return NEW_USER_REG_EMAIL_HTML;
	}

	public void setNEW_USER_REG_EMAIL_HTML(String NEW_USER_REG_EMAIL_HTML) {
		this.NEW_USER_REG_EMAIL_HTML = NEW_USER_REG_EMAIL_HTML;
	}

	@Override
	public String getMessageContent() {
		return null;
	}
}
