package com.itech.supplier.manager;

import java.util.List;

import com.itech.common.CommonUtilities;
import com.itech.common.services.CommonBaseManager;
import com.itech.supplier.dao.SupplierDAO;
import com.itech.supplier.model.Supplier;

public class SupplierManagerImpl extends CommonBaseManager implements SupplierManager{
	private SupplierDAO supplierDAO;

	@Override
	public void addOrUpdate(Supplier supplier) {
		getSupplierDAO().addOrUpdate(supplier);
		supplier.setUniqueId(CommonUtilities.getUniqueId(supplier));
		getSupplierDAO().addOrUpdate(supplier);
	}

	@Override
	public void delete(Supplier supplier) {
		getSupplierDAO().delete(supplier);
	}

	@Override
	public void delete(Long id) {
		getSupplierDAO().delete(id);
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
}
