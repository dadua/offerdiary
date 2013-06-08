package com.itech.customer.dao;

import java.util.List;

import com.itech.common.db.CommonBaseDAO;
import com.itech.customer.model.Customer;

public interface CustomerDAO  extends CommonBaseDAO<Customer>{
	Customer getByUniqueId(String uniqueId);

	List<Customer> searchByName(String searchString);
}
