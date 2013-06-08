package com.itech.flower.dao;

import java.util.List;

import com.itech.common.db.CommonBaseDAO;
import com.itech.flower.model.Customer;
import com.itech.flower.model.CustomerFlowerAssoc;
import com.itech.flower.model.Flower;

public interface CustomerFlowerAssocDAO extends CommonBaseDAO<CustomerFlowerAssoc>{
	public CustomerFlowerAssoc getAssocFor(Customer customer, Flower flower);
	public List<Flower> getFlowersFor(Customer customer);
	public List<Customer> getCustomersFor(Flower flower);
}
