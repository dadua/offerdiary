package com.itech.common.db.hibernate;

import java.util.List;

import org.hibernate.Query;

import com.itech.common.db.CommonUtilDAO;
import com.itech.common.db.PersistableEntity;

public class HibernateCommonUtilDAO extends HibernateCommonBaseDAO<PersistableEntity> implements
CommonUtilDAO {

	@Override
	protected Class getEntityClass() {
		return PersistableEntity.class;
	}

	@Override
	public boolean delete(Class clazz, long uniqueId) {
		String hql = "delete from "+clazz.getName()+" where id = :id";
		Query query = getSession().createQuery(hql);
		query.setParameter("id", uniqueId);
		query.executeUpdate();
		return true;

	}

	@Override
	public <T> T getById(Class<T> clazz, Long id) {
		return (T) getSession().load(clazz, id);
	}

	@Override
	public <T> List<T> getAll(Class<T> clazz) {
		String hql = "from " + clazz;
		Query query = getSession().createQuery(hql);
		return query.list();
	}

	@Override
	public boolean delete(long uniqueId) {
		throw new RuntimeException("Method not supported");
	}

	@Override
	public boolean delete(List<PersistableEntity> objects) {
		throw new RuntimeException("Method not supported");
	}

	@Override
	public List<PersistableEntity> getAll() {
		throw new RuntimeException("Method not supported");
	}



}
