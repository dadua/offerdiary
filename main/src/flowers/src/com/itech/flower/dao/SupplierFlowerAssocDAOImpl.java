package com.itech.flower.dao;

import java.util.List;

import org.hibernate.Query;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.flower.model.Flower;
import com.itech.flower.model.Supplier;
import com.itech.flower.model.SupplierFlowerAssoc;

public class SupplierFlowerAssocDAOImpl extends HibernateCommonBaseDAO<SupplierFlowerAssoc>
implements SupplierFlowerAssocDAO {

	@Override
	public SupplierFlowerAssoc getAssocFor(
			Supplier supplier, Flower flower) {
		String hql = "from " + getEntityClassName() + " where supplier = :supplier and flower=:flower";
		Query query = getSession().createQuery(hql);
		query.setParameter("supplier", supplier);
		query.setParameter("flower", flower);
		return getSingleResultFrom(query);
	}

	@Override
	public List<Flower> getFlowersFor(Supplier supplier) {
		String hql = "select flower from " + getEntityClassName() + " where supplier = :supplier ";
		Query query = getSession().createQuery(hql);
		query.setParameter("supplier", supplier);
		List result = query.list();
		return result;
	}

	@Override
	public List<Supplier> getSuppliersFor(Flower flower) {
		String hql = "select supplier from " + getEntityClassName() + " where flower = :flower ";
		Query query = getSession().createQuery(hql);
		query.setParameter("flower", flower);
		List result = query.list();
		return result;
	}

	@Override
	protected Class getEntityClass() {
		return SupplierFlowerAssoc.class;
	}


}
