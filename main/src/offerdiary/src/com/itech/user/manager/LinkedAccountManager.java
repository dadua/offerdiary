package com.itech.user.manager;

import java.util.List;

import com.itech.user.model.LinkedAccount;
import com.itech.user.model.LinkedAccountType;
import com.itech.user.model.User;

public interface LinkedAccountManager {
	public void saveOrUpdate(LinkedAccount linkedAccount);
	public List<LinkedAccount> getAllLinkedAccountsFor(User user);
	public LinkedAccount getPrimaryLinkedAccountFor(User user, LinkedAccountType linkedAccountType);
	public LinkedAccount getLinkedAccountFor(String uniqueId, LinkedAccountType linkedAccountType);
	public void deleteLinkedAccountFor(String uniqueId, LinkedAccountType linkedAccountType);
	public void delete(LinkedAccount linkedAccount);
}
