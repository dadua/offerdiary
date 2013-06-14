package com.itech.flower.dao;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.flower.model.CashTransaction;

public class CashTransactionDAOImpl extends HibernateCommonBaseDAO<CashTransaction> implements
CashTransactionDAO {

	@Override
	protected Class getEntityClass() {
		return CashTransaction.class;
	}





}
