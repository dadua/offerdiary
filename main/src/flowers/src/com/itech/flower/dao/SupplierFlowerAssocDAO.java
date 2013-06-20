package com.itech.flower.dao;

import java.util.List;

import com.itech.common.db.CommonBaseDAO;
import com.itech.flower.model.Flower;
import com.itech.flower.model.Supplier;
import com.itech.flower.model.SupplierFlowerAssoc;

public interface SupplierFlowerAssocDAO extends CommonBaseDAO<SupplierFlowerAssoc>{
	public SupplierFlowerAssoc getAssocFor(Supplier supplier, Flower flower);
	public List<Flower> getFlowersFor(Supplier supplier);
	public List<Supplier> getSuppliersFor(Flower flower);
	void deleteAssoscsFor(Supplier supplier);
}
