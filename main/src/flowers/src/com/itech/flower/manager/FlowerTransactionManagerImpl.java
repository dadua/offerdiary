package com.itech.flower.manager;

import java.util.List;

import com.itech.common.services.CommonBaseManager;
import com.itech.flower.dao.ContactDAO;
import com.itech.flower.dao.FlowerDAO;
import com.itech.flower.dao.FlowerTransactionDAO;
import com.itech.flower.dao.FlowerTransactionEntryDAO;
import com.itech.flower.model.Contact;
import com.itech.flower.model.Customer;
import com.itech.flower.model.Flower;
import com.itech.flower.model.FlowerTransaction;
import com.itech.flower.model.FlowerTransaction.FlowerTransactionType;
import com.itech.flower.model.FlowerTransactionEntry;
import com.itech.flower.model.Supplier;

public class FlowerTransactionManagerImpl extends CommonBaseManager implements
FlowerTransactionManager {

	private FlowerDAO flowerDAO;
	private ContactDAO contactDAO;
	private FlowerTransactionDAO flowerTransactionDAO;
	private FlowerTransactionEntryDAO flowerTransactionEntryDAO;

	@Override
	public void saveFlowerTransaction(FlowerTransaction flowerTransaction) {
		if (flowerTransaction.getContact() != null) {
			Contact contact = getContactDAO().getById(flowerTransaction.getContact().getId());
			flowerTransaction.setContact(contact);
		}

		for (FlowerTransactionEntry transactionEntry : flowerTransaction.getFlowerTransactionEntries()) {
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

}
