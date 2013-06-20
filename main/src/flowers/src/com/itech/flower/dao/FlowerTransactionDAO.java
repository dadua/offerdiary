package com.itech.flower.dao;

import java.util.List;

import com.itech.common.db.CommonBaseDAO;
import com.itech.flower.model.Contact;
import com.itech.flower.model.Customer;
import com.itech.flower.model.Flower;
import com.itech.flower.model.FlowerTransaction;
import com.itech.flower.model.Supplier;

public interface FlowerTransactionDAO extends  CommonBaseDAO<FlowerTransaction>{

	public List<FlowerTransaction> getFlowerTransactionsFor(Customer customer);

	public List<FlowerTransaction> getFlowerTransactionsFor(Supplier supplier);

	public List<FlowerTransaction> getFlowerTransactionsFor(Flower flower);

	public List<FlowerTransaction> getFlowerTransactionsFor(String searchString);

	public void deleteTransactionsFor(Contact contact);
}
