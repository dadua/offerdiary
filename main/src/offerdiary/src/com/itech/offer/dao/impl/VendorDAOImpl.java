package com.itech.offer.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.offer.dao.VendorDAO;
import com.itech.offer.model.Vendor;
import com.itech.offer.model.enums.VendorType;

public class VendorDAOImpl extends HibernateCommonBaseDAO<Vendor> implements VendorDAO{

	@Override
	protected Class getEntityClass() {
		return Vendor.class;
	}

	@Override
	public List<Vendor> getVendorsFor(String vendorName, int maxResults) {
		String hql = "from " + getEntityClassName() + " where (vendorType=:vendorType or owner = :owner) and name like :vendorName order by name";
		Query query = getSession().createQuery(hql);
		query.setParameter("vendorType", VendorType.GLOBAL);
		query.setParameter("owner", getLoggedInUser());
		query.setParameter("vendorName", "%"+vendorName+"%");
		query.setMaxResults(maxResults);
		List<Vendor> list = query.list();
		return list;
	}

	@Override
	public Vendor getVendorByName(String name) {
		String hql = "from " + getEntityClassName() + " where (vendorType=:vendorType) and name  =:vendorName order by name";
		Query query = getSession().createQuery(hql);
		query.setParameter("vendorType", VendorType.GLOBAL);
		query.setParameter("vendorName", name);
		return (Vendor) getSingleResult(query, true);
	}



}
