package com.itech.flower.dao;

import java.util.List;

import org.hibernate.Query;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.flower.model.Customer;
import com.itech.flower.model.Flower;
import com.itech.flower.model.FlowerTransaction;
import com.itech.flower.model.Supplier;

public class FlowerTransactionDAOImpl extends
HibernateCommonBaseDAO<FlowerTransaction> implements FlowerTransactionDAO {

	@Override
	public List<FlowerTransaction> getFlowerTransactionsFor(Customer customer) {
		String hql = "select ft from " + getEntityClassName() + " ft where contact.id = :id";
		Query query = getSession().createQuery(hql);
		query.setParameter("id", customer.getId());
		List result = query.list();
		return result;
	}

	@Override
	public List<FlowerTransaction> getFlowerTransactionsFor(Supplier supplier) {
		String hql = "select ft from " + getEntityClassName() + " ft where contact.id = :id";
		Query query = getSession().createQuery(hql);
		query.setParameter("id", supplier.getId());
		List result = query.list();
		return result;
	}

	@Override
	public List<FlowerTransaction> getFlowerTransactionsFor(Flower flower) {
		String hql = "select ft from " + getEntityClassName() + " ft inner join flower where contact.id = :id";
		Query query = getSession().createQuery(hql);
		query.setParameter("id", supplier.getId());
		List result = query.list();
		return result;
	}

	@Override
	protected Class getEntityClass() {
		return FlowerTransaction.class;
	}



}
