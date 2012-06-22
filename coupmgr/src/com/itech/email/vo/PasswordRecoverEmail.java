package com.itech.email.vo;

public class PasswordRecoverEmail extends EmailVO{

	private String PASSWORD_RECOVERY_EMAIL_HTML ;

	@Override
	public void setHtmlContent(String htmlContent) {
		generateNotificationEmailContent();
		setHtmlContent(PASSWORD_RECOVERY_EMAIL_HTML);
	}

	private void generateNotificationEmailContent() {
		// TODO Auto-generated method stub

	}

	public String getPASSWORD_RECOVERY_EMAIL_HTML() {
		return PASSWORD_RECOVERY_EMAIL_HTML;
	}
	public void setPASSWORD_RECOVERY_EMAIL_HTML(String PASSWORD_RECOVERY_EMAIL_HTML) {
		this.PASSWORD_RECOVERY_EMAIL_HTML = PASSWORD_RECOVERY_EMAIL_HTML;
	}

}
