package com.itech.flower.dao;

import java.util.List;

import org.hibernate.Query;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.flower.model.Contact;
import com.itech.flower.model.Customer;
import com.itech.flower.model.Flower;
import com.itech.flower.model.FlowerTransaction;
import com.itech.flower.model.Supplier;

public class FlowerTransactionDAOImpl extends
HibernateCommonBaseDAO<FlowerTransaction> implements FlowerTransactionDAO {
	@Override
	protected Class getEntityClass() {
		return FlowerTransaction.class;
	}

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
		return null;
	}


	@Override
	public List<FlowerTransaction> getFlowerTransactionsFor(String searchString) {
		if (searchString == null) {
			searchString = "";
		}
		String hql = "select ft from " + getEntityClassName() + " ft where ft.uniqueId like :searchString or ft.contact.name like :searchString";
		Query query = getSession().createQuery(hql);
		query.setParameter("searchString", '%' + searchString + '%');
		List result = query.list();
		return result;
	}

	@Override
	public void deleteTransactionsFor(Contact contact) {
		String hql = "delete from  " + getEntityClassName() + " where contact = :contact ";
		Query query = getSession().createQuery(hql);
		query.setParameter("contact", contact);
		query.executeUpdate();
	}



}
