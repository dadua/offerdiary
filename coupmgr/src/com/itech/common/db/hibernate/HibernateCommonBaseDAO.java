package com.itech.common.db.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.itech.common.db.CommonBaseDAO;

public abstract class HibernateCommonBaseDAO <T> implements CommonBaseDAO<T> {

	private HibernateSessionFactory hibernateSessionFactory;

	@Override
	public boolean addOrUpdate(T object) {
		getSession().save(object);
		return true;
	}

	@Override
	public boolean addOrUpdate(List<T> objects) {
		for (Object object : objects) {
			getSession().saveOrUpdate(object);
		}
		return true;
	}

	@Override
	public boolean delete(long uniqueId) {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public boolean delete(List<T> objects) {
		for (Object object : objects) {
			getSession().delete(object);
		}
		return true;
	}

	@Override
	public List<T> getAll() {
		String hql = "from " + getClassName();
		Query query = getSession().createQuery(hql);
		return query.list();
	}

	protected abstract String getClassName();

	@Override
	public T getById(Long id) {
		return (T) getSession().load(getClass(), id);
	}

	protected Session getSession() {
		return getHibernateSessionFactory().getCurrentSession();
	}

	public void setHibernateSessionFactory(HibernateSessionFactory hibernateSessionFactory) {
		this.hibernateSessionFactory = hibernateSessionFactory;
	}

	public HibernateSessionFactory getHibernateSessionFactory() {
		return hibernateSessionFactory;
	}

}
