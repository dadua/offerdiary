package com.itech.flower.manager;

import java.util.List;

import com.itech.common.CommonUtilities;
import com.itech.common.services.CommonBaseManager;
import com.itech.flower.dao.CashTransactionDAO;
import com.itech.flower.dao.ContactDAO;
import com.itech.flower.dao.FlowerDAO;
import com.itech.flower.dao.FlowerTransactionDAO;
import com.itech.flower.dao.FlowerTransactionEntryDAO;
import com.itech.flower.model.CashTransaction;
import com.itech.flower.model.CashTransaction.CashFlowType;
import com.itech.flower.model.Contact;
import com.itech.flower.model.Customer;
import com.itech.flower.model.Flower;
import com.itech.flower.model.FlowerTransaction;
import com.itech.flower.model.FlowerTransaction.FlowerTransactionType;
import com.itech.flower.model.FlowerTransactionEntry;
import com.itech.flower.model.Supplier;

public class TransactionManagerImpl extends CommonBaseManager implements
TransactionManager {

	private FlowerDAO flowerDAO;
	private ContactDAO contactDAO;
	private FlowerTransactionDAO flowerTransactionDAO;
	private FlowerTransactionEntryDAO flowerTransactionEntryDAO;
	private CashTransactionDAO cashTransactionDAO;

	@Override
	public void addOrUpdateFlowerTransaction(FlowerTransaction flowerTransaction) {
		if (flowerTransaction.getContact() != null) {
			Contact contact = getContactDAO().getById(flowerTransaction.getContact().getId());
			flowerTransaction.setContact(contact);
		}

		float totalCost = 0.0f;
		for (FlowerTransactionEntry transactionEntry : flowerTransaction.getFlowerTransactionEntries()) {
			if (!transactionEntry.isTransient()) {
				continue;
			}
			totalCost += transactionEntry.getTotalCost();
			getFlowerTransactionEntryDAO().addOrUpdate(transactionEntry);
			transactionEntry.setUniqueId(CommonUtilities.getUniqueId(transactionEntry));
			getFlowerTransactionEntryDAO().addOrUpdate(transactionEntry);
			Flower flower = getFlowerDAO().getById(transactionEntry.getFlower().getId());
			transactionEntry.setFlower(flower);
			if (FlowerTransactionType.IN.equals(flowerTransaction.getTransactionType())) {
				flower.setQuantityInStock(flower.getQuantityInStock() + transactionEntry.getQuantity());
			} else if (FlowerTransactionType.OUT.equals(flowerTransaction.getTransactionType())) {
				flower.setQuantityInStock(flower.getQuantityInStock() - transactionEntry.getQuantity());
			}
			getFlowerDAO().addOrUpdate(flower);
		}

		flowerTransaction.setTotalCost(totalCost);

		Contact contact = flowerTransaction.getContact();
		float balance = contact.getBalance();
		if (FlowerTransactionType.IN.equals(flowerTransaction.getTransactionType())) {
			balance = balance - flowerTransaction.getTotalCost();
		} else if (FlowerTransactionType.IN.equals(flowerTransaction.getTransactionType())) {
			balance = balance + flowerTransaction.getTotalCost();
		}

		getContactDAO().addOrUpdate(contact);

		getFlowerTransactionDAO().addOrUpdate(flowerTransaction);

		flowerTransaction.setUniqueId(CommonUtilities.getUniqueId(flowerTransaction));
		getFlowerTransactionDAO().addOrUpdate(flowerTransaction);

	}

	@Override
	public List<FlowerTransaction> getFlowerTransactionsFor(Customer customer) {
		return getFlowerTransactionDAO().getFlowerTransactionsFor(customer);
	}

	@Override
	public List<FlowerTransaction> getFlowerTransactionsFor(Supplier supplier) {
		return getFlowerTransactionDAO().getFlowerTransactionsFor(supplier);
	}

	@Override
	public List<FlowerTransaction> getFlowerTransactionsFor(Flower flower) {
		return getFlowerTransactionDAO().getFlowerTransactionsFor(flower);
	}


	@Override
	public FlowerTransaction getFlowerTransactionById(long txId) {
		return getFlowerTransactionDAO().getById(txId);
	}

	@Override
	public List<FlowerTransaction> getFlowerTransactionsFor(String searchString) {
		return getFlowerTransactionDAO().getFlowerTransactionsFor(searchString);
	}


	@Override
	public void addOrUpdateCashTransaction(CashTransaction cashTransaction) {
		if (cashTransaction.getContact() != null) {
			Contact contact = getContactDAO().getById(cashTransaction.getContact().getId());
			cashTransaction.setContact(contact);
		}
		getCashTransactionDAO().addOrUpdate(cashTransaction);

		Contact contact = cashTransaction.getContact();
		float balance = contact.getBalance();

		if (CashFlowType.IN.equals(cashTransaction.getFlowType())) {
			balance = balance - cashTransaction.getAmount();
		} else if (CashFlowType.OUT.equals(cashTransaction.getFlowType())) {
			balance = balance + cashTransaction.getAmount();
		}

		contact.setBalance(balance);

		getContactDAO().addOrUpdate(contact);
	}

	@Override
	public List<CashTransaction> getCashTransactionsFor(Customer customer) {
		return getCashTransactionDAO().getCashTransactionsFor(customer);
	}

	@Override
	public List<CashTransaction> getCashTransactionsFor(Supplier supplier) {
		return getCashTransactionDAO().getCashTransactionsFor(supplier);
	}

	@Override
	public CashTransaction getCashTransactionById(long txId) {
		return getCashTransactionDAO().getById(txId);
	}

	@Override
	public List<CashTransaction> getCashTransactionsFor(String searchString) {
		return getCashTransactionDAO().getCashTransactionsFor(searchString);
	}


	public FlowerDAO getFlowerDAO() {
		return flowerDAO;
	}

	public void setFlowerDAO(FlowerDAO flowerDAO) {
		this.flowerDAO = flowerDAO;
	}

	public ContactDAO getContactDAO() {
		return contactDAO;
	}

	public void setContactDAO(ContactDAO contactDAO) {
		this.contactDAO = contactDAO;
	}

	public FlowerTransactionDAO getFlowerTransactionDAO() {
		return flowerTransactionDAO;
	}

	public void setFlowerTransactionDAO(FlowerTransactionDAO flowerTransactionDAO) {
		this.flowerTransactionDAO = flowerTransactionDAO;
	}

	public FlowerTransactionEntryDAO getFlowerTransactionEntryDAO() {
		return flowerTransactionEntryDAO;
	}

	public void setFlowerTransactionEntryDAO(FlowerTransactionEntryDAO flowerTransactionEntryDAO) {
		this.flowerTransactionEntryDAO = flowerTransactionEntryDAO;
	}

	public CashTransactionDAO getCashTransactionDAO() {
		return cashTransactionDAO;
	}

	public void setCashTransactionDAO(CashTransactionDAO cashTransactionDAO) {
		this.cashTransactionDAO = cashTransactionDAO;
	}

}
