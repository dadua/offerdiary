package com.itech.email.services;

import com.itech.email.vo.Email;

public interface EmailManager {

	public boolean sendEmailSync(Email email);
	public boolean sendEmailAsync(Email email);

}
