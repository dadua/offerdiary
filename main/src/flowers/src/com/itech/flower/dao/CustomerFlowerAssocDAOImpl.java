package com.itech.flower.dao;

import java.util.List;

import org.hibernate.Query;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.flower.model.Customer;
import com.itech.flower.model.CustomerFlowerAssoc;
import com.itech.flower.model.Flower;

public class CustomerFlowerAssocDAOImpl extends HibernateCommonBaseDAO<CustomerFlowerAssoc> implements
CustomerFlowerAssocDAO {

	@Override
	public CustomerFlowerAssoc getAssocFor(
			Customer customer, Flower flower) {
		String hql = "from " + getEntityClassName() + " where customer = :customer and flower=:flower";
		Query query = getSession().createQuery(hql);
		query.setParameter("customer", customer);
		query.setParameter("flower", flower);
		return getSingleResultFrom(query);
	}

	@Override
	public List<Flower> getFlowersFor(Customer customer) {
		String hql = "select flower from " + getEntityClassName() + " where customer = :customer ";
		Query query = getSession().createQuery(hql);
		query.setParameter("customer", customer);
		List result = query.list();
		return result;
	}

	@Override
	public List<Customer> getCustomersFor(Flower flower) {
		String hql = "select customer from " + getEntityClassName() + " where flower = :flower ";
		Query query = getSession().createQuery(hql);
		query.setParameter("flower", flower);
		List result = query.list();
		return result;
	}

	@Override
	protected Class getEntityClass() {
		return CustomerFlowerAssoc.class;
	}

	@Override
	public void deleteAssoscsFor(Customer customer) {
		String hql = "delete from  " + getEntityClassName() + " where customer = :customer ";
		Query query = getSession().createQuery(hql);
		query.setParameter("customer", customer);
		query.executeUpdate();
	}
}
