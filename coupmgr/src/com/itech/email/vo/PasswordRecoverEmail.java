package com.itech.email.vo;

public class PasswordRecoverEmail extends EmailMessage{

	private String PASSWORD_RECOVERY_EMAIL_HTML ;

	public String getPASSWORD_RECOVERY_EMAIL_HTML() {
		return PASSWORD_RECOVERY_EMAIL_HTML;
	}
	public void setPASSWORD_RECOVERY_EMAIL_HTML(String PASSWORD_RECOVERY_EMAIL_HTML) {
		this.PASSWORD_RECOVERY_EMAIL_HTML = PASSWORD_RECOVERY_EMAIL_HTML;
	}
	@Override
	public String getMessageContent() {
		// TODO Auto-generated method stub
		return null;
	}

}
