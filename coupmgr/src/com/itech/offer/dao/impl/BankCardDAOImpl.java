package com.itech.offer.dao.impl;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.offer.model.BankCard;

public class BankCardDAOImpl extends HibernateCommonBaseDAO<BankCard>{

	@Override
	protected Class getEntityClass() {
		return BankCard.class;
	}

}
