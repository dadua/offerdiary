package com.itech.common.db.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.itech.common.db.CommonBaseDAO;
import com.itech.common.security.SecurityManager;
import com.itech.coupon.model.User;

public abstract class HibernateCommonBaseDAO <T> implements CommonBaseDAO<T> {

	private HibernateSessionFactory hibernateSessionFactory;
	private SecurityManager securityManager;


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
		String hql = "delete from "+getEntityClassName()+" where id = :id";
		Query query = getSession().createQuery(hql);
		query.setParameter("id", uniqueId);
		query.executeUpdate();
		return true;
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
		String hql = "from " + getEntityClassName();
		Query query = getSession().createQuery(hql);
		return query.list();
	}

	protected abstract Class getEntityClass();

	protected String getEntityClassName(){
		return getEntityClass().getName();
	}

	@Override
	public T getById(Long id) {
		return (T) getSession().load(getEntityClass(), id);
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

	public User getLoggedInUser() {
		return getSecurityManager().getLoggedInUser();
	}

	public void setSecurityManager(SecurityManager securityManager) {
		this.securityManager = securityManager;
	}

	public SecurityManager getSecurityManager() {
		return securityManager;
	}

}
