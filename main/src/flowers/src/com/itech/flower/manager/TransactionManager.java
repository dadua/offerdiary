package com.itech.flower.manager;

import java.util.List;

import com.itech.flower.model.CashTransaction;
import com.itech.flower.model.Contact;
import com.itech.flower.model.Customer;
import com.itech.flower.model.Flower;
import com.itech.flower.model.FlowerTransaction;
import com.itech.flower.model.Supplier;

public interface TransactionManager {
	public void addOrUpdateFlowerTransaction(FlowerTransaction flowerTransaction);
	public List<FlowerTransaction> getFlowerTransactionsFor(Customer customer);
	public List<FlowerTransaction> getFlowerTransactionsFor(Supplier supplier);
	public List<FlowerTransaction> getFlowerTransactionsFor(Flower flower);
	public FlowerTransaction getFlowerTransactionById(long txId);
	public List<FlowerTransaction> getFlowerTransactionsFor(String searchString);


	public void addOrUpdateCashTransaction(CashTransaction cashTransaction);
	public List<CashTransaction> getCashTransactionsFor(Customer customer);
	public List<CashTransaction> getCashTransactionsFor(Supplier supplier);
	public CashTransaction getCashTransactionById(long txId);
	public List<CashTransaction> getCashTransactionsFor(String searchString);



	public void deleteAllFlowerTransactionsFor(Contact contact);
	public void deleteAllCashTransactionsFor(Contact contact);
}
