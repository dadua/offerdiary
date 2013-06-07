package com.itech.customer.dao;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.customer.model.Customer;

public class CustomerDAOImpl extends HibernateCommonBaseDAO<Customer> implements CustomerDAO {

	@Override
	protected Class getEntityClass() {
		return Customer.class;
	}

}
