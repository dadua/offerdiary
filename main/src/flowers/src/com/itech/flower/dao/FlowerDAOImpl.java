package com.itech.flower.dao;

import java.util.List;

import org.hibernate.Query;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.flower.model.Flower;

public class FlowerDAOImpl extends HibernateCommonBaseDAO<Flower> implements FlowerDAO {

	@Override
	protected Class getEntityClass() {
		return Flower.class;
	}

	@Override
	public Flower getFlowerFor(String name) {
		String hql = "select f from " + getEntityClassName() + " f where name=:name";
		Query query = getSession().createQuery(hql);
		query.setParameter("name", name);
		return getSingleResultFrom(query);
	}

	@Override
	public List<Flower> searchFlowerBy(String searchString) {
		String hql = "select f from " + getEntityClassName() + " f where name like :searchString";
		Query query = getSession().createQuery(hql);
		query.setParameter("searchString", '%' + searchString + '%');
		List result = query.list();
		return result;
	}

}
