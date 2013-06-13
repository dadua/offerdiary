package com.itech.flower.manager;

import java.util.List;

import com.itech.common.CommonUtilities;
import com.itech.common.services.CommonBaseManager;
import com.itech.flower.dao.CustomerDAO;
import com.itech.flower.model.Customer;

public class CustomerManagerImpl extends CommonBaseManager implements CustomerManager{
	private CustomerDAO customerDAO;

	@Override
	public void addOrUpdate(Customer customer) {
		getCustomerDAO().addOrUpdate(customer);
		customer.setUniqueId(CommonUtilities.getUniqueId(customer));
		getCustomerDAO().addOrUpdate(customer);
	}

	@Override
	public void delete(Customer customer) {
		getCustomerDAO().delete(customer);
	}

	@Override
	public void delete(Long id) {
		getCustomerDAO().delete(id);
	}
	@Override
	public void delete(List<Long> ids) {
		for (Long id : ids) {
			getCustomerDAO().delete(id);
		}
	}

	@Override
	public Customer getByUniqueId(String uniqueId) {
		return getCustomerDAO().getByUniqueId(uniqueId);
	}

	@Override
	public Customer getById(Long id) {
		return getCustomerDAO().getById(id);
	}

	@Override
	public List<Customer> searchByName(String searchString) {
		if (searchString == null) {
			searchString = "";
		}
		return getCustomerDAO().searchByName(searchString);
	}

	public CustomerDAO getCustomerDAO() {
		return customerDAO;
	}

	public void setCustomerDAO(CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}
}
