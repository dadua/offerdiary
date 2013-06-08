package com.itech.flower.dao;

import java.util.List;

import com.itech.common.db.CommonBaseDAO;
import com.itech.flower.model.Supplier;

public interface SupplierDAO  extends CommonBaseDAO<Supplier>{

	Supplier getByUniqueId(String uniqueId);

	List<Supplier> searchByName(String searchString);

}
