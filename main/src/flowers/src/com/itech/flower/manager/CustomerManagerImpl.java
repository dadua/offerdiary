package com.itech.flower.manager;

import java.util.List;

import com.itech.common.CommonUtilities;
import com.itech.common.services.CommonBaseManager;
import com.itech.flower.dao.CustomerDAO;
import com.itech.flower.dao.CustomerFlowerAssocDAO;
import com.itech.flower.model.Customer;

public class CustomerManagerImpl extends CommonBaseManager implements CustomerManager{
	private CustomerDAO customerDAO;
	private TransactionManager transactionManager;
	private CustomerFlowerAssocDAO customerFlowerAssocDAO;

	@Override
	public void addOrUpdate(Customer customer) {
		getCustomerDAO().addOrUpdate(customer);
		customer.setUniqueId(CommonUtilities.getUniqueId(customer));
		getCustomerDAO().addOrUpdate(customer);
	}

	@Override
	public void delete(Customer customer) {
		getCustomerDAO().delete(customer);
		getTransactionManager().deleteAllFlowerTransactionsFor(customer);
		getTransactionManager().deleteAllCashTransactionsFor(customer);
		getCustomerFlowerAssocDAO().deleteAssoscsFor(customer);
	}

	@Override
	public void delete(Long id) {
		Customer customer = getById(id);
		delete(customer);
	}
	@Override
	public void delete(List<Long> ids) {
		for (Long id : ids) {
			Customer customer = getById(id);
			delete(customer);
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

	public TransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public CustomerFlowerAssocDAO getCustomerFlowerAssocDAO() {
		return customerFlowerAssocDAO;
	}

	public void setCustomerFlowerAssocDAO(CustomerFlowerAssocDAO customerFlowerAssocDAO) {
		this.customerFlowerAssocDAO = customerFlowerAssocDAO;
	}
}
