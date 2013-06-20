package com.itech.flower.dao;

import java.util.List;

import com.itech.common.db.CommonBaseDAO;
import com.itech.flower.model.CashTransaction;
import com.itech.flower.model.Contact;

public interface CashTransactionDAO extends CommonBaseDAO<CashTransaction> {

	List<CashTransaction> getCashTransactionsFor(Contact contact);

	List<CashTransaction> getCashTransactionsFor(String searchString);

	void deleteTransactionsFor(Contact contact);

}
