package com.itech.flower.dao;

import java.util.List;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.flower.model.Customer;
import com.itech.flower.model.Flower;
import com.itech.flower.model.FlowerTransaction;
import com.itech.flower.model.Supplier;

public class FlowerTransactionDAOImpl extends
HibernateCommonBaseDAO<FlowerTransaction> implements FlowerTransactionDAO {

	@Override
	public List<FlowerTransaction> getFlowerTransactionsFor(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FlowerTransaction> getFlowerTransactionsFor(Supplier supplier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FlowerTransaction> getFlowerTransactionsFor(Flower flower) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class getEntityClass() {
		return FlowerTransaction.class;
	}



}
