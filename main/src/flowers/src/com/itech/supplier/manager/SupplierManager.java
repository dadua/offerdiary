package com.itech.supplier.manager;

import java.util.List;

import com.itech.supplier.model.Supplier;

public interface SupplierManager {
	public void addOrUpdate(Supplier supplier);
	public void delete(Supplier supplier);
	public void delete(Long id);
	public Supplier getByUniqueId(String uniqueId);
	public Supplier getById(Long id);
	public List<Supplier> searchByName(String searchString);
}
