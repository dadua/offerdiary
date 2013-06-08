package com.itech.flower.dao;

import java.util.List;

import org.hibernate.Query;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.flower.model.Supplier;

public class SupplierDAOImpl extends HibernateCommonBaseDAO<Supplier> implements SupplierDAO{

	@Override
	protected Class getEntityClass() {
		return Supplier.class;
	}

	@Override
	public Supplier getByUniqueId(String uniqueId) {
		String hql = "from " + getEntityClassName() + " where uniqueId = :uniqueId";
		Query query = getSession().createQuery(hql);
		query.setParameter("uniqueId", uniqueId);
		return getSingleResultFrom(query);
	}

	@Override
	public List<Supplier> searchByName(String searchString) {
		String hql = "from " + getEntityClassName() + " where name like :searchString";
		Query query = getSession().createQuery(hql);
		query.setParameter("searchString", "%" + searchString + "%");
		List result = query.list();
		return result;
	}

}
