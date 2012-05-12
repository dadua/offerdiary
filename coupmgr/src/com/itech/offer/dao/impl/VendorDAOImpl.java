package com.itech.offer.dao.impl;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.offer.dao.VendorDAO;
import com.itech.offer.model.Vendor;

public class VendorDAOImpl extends HibernateCommonBaseDAO<Vendor> implements VendorDAO{

	@Override
	protected Class getEntityClass() {
		return Vendor.class;
	}

}
