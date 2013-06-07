package com.itech.supplier.dao;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.supplier.model.Supplier;

public class SupplierDAOImpl extends HibernateCommonBaseDAO<Supplier> implements SupplierDAO{

	@Override
	protected Class getEntityClass() {
		return Supplier.class;
	}

}
