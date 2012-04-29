package com.itech.common.db.hibernate;

import org.hibernate.Session;

public interface HibernateSessionFactory {
	public Session getCurrentSession();
	public void openNewSession();
	public void closeCurrentSession();
	public void commitCurrentTransaction();
	public void rollbackCurrentTransaction();
}
