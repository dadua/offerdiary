package com.itech.flower.dao;

import java.util.List;

import org.hibernate.Query;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.flower.model.Customer;

public class CustomerDAOImpl extends HibernateCommonBaseDAO<Customer> implements CustomerDAO {

	@Override
	protected Class getEntityClass() {
		return Customer.class;
	}

	@Override
	public Customer getByUniqueId(String uniqueId) {
		String hql = "from " + getEntityClassName() + " where uniqueId = :uniqueId";
		Query query = getSession().createQuery(hql);
		query.setParameter("uniqueId", uniqueId);
		return getSingleResultFrom(query);
	}

	@Override
	public List<Customer> searchByName(String searchString) {
		String hql = "from " + getEntityClassName() + " where name like :searchString";
		Query query = getSession().createQuery(hql);
		query.setParameter("searchString", "%" + searchString + "%");
		List result = query.list();
		return result;
	}

}
