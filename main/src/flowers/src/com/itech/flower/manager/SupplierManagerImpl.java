package com.itech.flower.manager;

import java.util.List;

import com.itech.common.CommonUtilities;
import com.itech.common.services.CommonBaseManager;
import com.itech.flower.dao.SupplierDAO;
import com.itech.flower.dao.SupplierFlowerAssocDAO;
import com.itech.flower.model.Supplier;

public class SupplierManagerImpl extends CommonBaseManager implements SupplierManager{
	private SupplierDAO supplierDAO;
	private TransactionManager transactionManager;
	private SupplierFlowerAssocDAO supplierFlowerAssocDAO;

	@Override
	public void addOrUpdate(Supplier supplier) {
		getSupplierDAO().addOrUpdate(supplier);
		supplier.setUniqueId(CommonUtilities.getUniqueId(supplier));
		getSupplierDAO().addOrUpdate(supplier);
	}

	@Override
	public void delete(Supplier supplier) {
		getSupplierDAO().delete(supplier);
		getTransactionManager().deleteAllFlowerTransactionsFor(supplier);
		getTransactionManager().deleteAllCashTransactionsFor(supplier);
		getSupplierFlowerAssocDAO().deleteAssoscsFor(supplier);
	}

	@Override
	public void delete(Long id) {
		Supplier supplier = getSupplierDAO().getById(id);
		delete(supplier);
	}

	@Override
	public void delete(List<Long> ids) {
		for (Long id : ids) {
			Supplier supplier = getSupplierDAO().getById(id);
			delete(supplier);
		}
	}

	@Override
	public Supplier getByUniqueId(String uniqueId) {
		return getSupplierDAO().getByUniqueId(uniqueId);
	}

	@Override
	public Supplier getById(Long id) {
		return getSupplierDAO().getById(id);
	}

	@Override
	public List<Supplier> searchByName(String searchString) {
		if (searchString == null) {
			searchString = "";
		}
		return getSupplierDAO().searchByName(searchString);
	}

	public SupplierDAO getSupplierDAO() {
		return supplierDAO;
	}

	public void setSupplierDAO(SupplierDAO supplierDAO) {
		this.supplierDAO = supplierDAO;
	}

	public TransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public SupplierFlowerAssocDAO getSupplierFlowerAssocDAO() {
		return supplierFlowerAssocDAO;
	}

	public void setSupplierFlowerAssocDAO(SupplierFlowerAssocDAO supplierFlowerAssocDAO) {
		this.supplierFlowerAssocDAO = supplierFlowerAssocDAO;
	}
}
