package com.itech.flower.manager;

import java.util.List;

import com.itech.flower.model.Customer;
import com.itech.flower.model.Flower;
import com.itech.flower.model.FlowerTransaction;
import com.itech.flower.model.Supplier;

public interface FlowerTransactionManager {
	public void saveFlowerTransaction(FlowerTransaction flowerTransaction);
	public List<FlowerTransaction> getFlowerTransactionsFor(Customer customer);
	public List<FlowerTransaction> getFlowerTransactionsFor(Supplier supplier);
	public List<FlowerTransaction> getFlowerTransactionsFor(Flower flower);
}
