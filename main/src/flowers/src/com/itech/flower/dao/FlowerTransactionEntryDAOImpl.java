package com.itech.flower.dao;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.flower.model.FlowerTransactionEntry;

public class FlowerTransactionEntryDAOImpl extends HibernateCommonBaseDAO<FlowerTransactionEntry>
implements FlowerTransactionEntryDAO {

	@Override
	protected Class getEntityClass() {
		return FlowerTransactionEntry.class;
	}


}
