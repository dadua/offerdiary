package com.itech.user.manager;

import java.util.List;

import org.apache.log4j.Logger;

import com.itech.common.services.CommonBaseManager;
import com.itech.user.dao.LinkedAccountDAO;
import com.itech.user.model.LinkedAccount;
import com.itech.user.model.LinkedAccountType;
import com.itech.user.model.User;

public class LinkedAccountManagerImpl extends CommonBaseManager implements LinkedAccountManager{

	private static final Logger logger = Logger.getLogger(LinkedAccountManagerImpl.class);
	private LinkedAccountDAO linkedAccountDAO;


	@Override
	public void saveOrUpdate(LinkedAccount linkedAccount) {
		getLinkedAccountDAO().addOrUpdate(linkedAccount);
	}

	@Override
	public List<LinkedAccount> getAllLinkedAccountsFor(User user) {
		return getLinkedAccountDAO().getAllLinkedAccountsFor(user);
	}

	@Override
	public LinkedAccount getPrimaryLinkedAccountFor(User user,
			LinkedAccountType linkedAccountType) {
		return getLinkedAccountDAO().getPrimaryLinkedAccountFor(user, linkedAccountType);
	}

	@Override
	public LinkedAccount getLinkedAccountFor(String uniqueId,
			LinkedAccountType linkedAccountType) {
		return getLinkedAccountDAO().getLinkedAccountFor(uniqueId, linkedAccountType);
	}

	@Override
	public void deleteLinkedAccountFor(String uniqueId,
			LinkedAccountType linkedAccountType) {
		LinkedAccount linkedAccount = getLinkedAccountDAO().getLinkedAccountFor(uniqueId, linkedAccountType);
		if (linkedAccount == null) {
			logger.warn("trying to delete non-existing account for id: " + uniqueId + " , type: " + linkedAccountType);
			return;
		}

		delete(linkedAccount);
	}

	@Override
	public void delete(LinkedAccount linkedAccount) {
		getLinkedAccountDAO().delete(linkedAccount);
	}

	public LinkedAccountDAO getLinkedAccountDAO() {
		return linkedAccountDAO;
	}

	public void setLinkedAccountDAO(LinkedAccountDAO linkedAccountDAO) {
		this.linkedAccountDAO = linkedAccountDAO;
	}

}
