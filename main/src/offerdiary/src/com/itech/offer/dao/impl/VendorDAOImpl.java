package com.itech.offer.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.itech.common.CommonUtilities;
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
		String hql = "from " + getEntityClassName() + " where (vendorType=:vendorType or owner = :owner) and name like :vendorName   order by";
		if (CommonUtilities.isNullOrEmpty(vendorName)) {
			hql += " odReputation desc ";
		}

		hql += "  name asc";
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

	@Override
	public Vendor getVendorForVendorDetail(Vendor vendor) {
		String hqlByName = "from " + getEntityClassName() + " where (vendorType=:vendorType) and ( " +
				" name =:vendorName ) order by name";
		Query queryByName = getSession().createQuery(hqlByName);

		queryByName.setParameter("vendorType", VendorType.GLOBAL);
		queryByName.setParameter("vendorName", vendor.getName());
		Vendor vendorFromDB = (Vendor) getSingleResult(queryByName, true);

		if (vendorFromDB != null) {
			return vendorFromDB;
		}
		if (CommonUtilities.isNotEmpty(vendor.getSiteUrl())) {
			String hqlBySiteUrl = "from " + getEntityClassName() + " where (vendorType=:vendorType) and ( " +
					" siteUrl like :siteUrl ) order by name";
			Query queryBySiteUrl = getSession().createQuery(hqlBySiteUrl);

			queryBySiteUrl.setParameter("vendorType", VendorType.GLOBAL);
			queryBySiteUrl.setParameter("siteUrl", "%" + vendor.getSiteUrl());
			List list = queryBySiteUrl.list();
			if(list.size() == 1) {
				list.get(0);
			}
		}
		return null;
	}



}
