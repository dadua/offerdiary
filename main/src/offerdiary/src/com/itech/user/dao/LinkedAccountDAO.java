package com.itech.user.dao;

import java.util.List;

import com.itech.common.db.CommonBaseDAO;
import com.itech.user.model.LinkedAccount;
import com.itech.user.model.LinkedAccountType;
import com.itech.user.model.User;

public interface LinkedAccountDAO extends CommonBaseDAO<LinkedAccount> {

	List<LinkedAccount> getAllLinkedAccountsFor(User user);

	LinkedAccount getPrimaryLinkedAccountFor(User user,
			LinkedAccountType linkedAccountType);

	LinkedAccount getLinkedAccountFor(String uniqueId,
			LinkedAccountType linkedAccountType);

}
