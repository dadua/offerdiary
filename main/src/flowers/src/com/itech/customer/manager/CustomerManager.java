package com.itech.customer.manager;

import java.util.List;

import com.itech.flower.model.Customer;

public interface CustomerManager {
	public void addOrUpdate(Customer customer);
	public void delete(Customer customer);
	public void delete(Long id);
	public Customer getByUniqueId(String uniqueId);
	public Customer getById(Long id);
	public List<Customer> searchByName(String searchString);
}
