package com.itech.common.db.hibernate;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateSessionFactoryImpl  implements HibernateSessionFactory {
	private static final Logger logger = Logger.getLogger(HibernateSessionFactoryImpl.class);
	private static final ThreadLocal<Session> threadLocalSession = new ThreadLocal<Session>();

	private final SessionFactory sessionFactory;

	public HibernateSessionFactoryImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	@Override
	public Session getCurrentSession() {
		return threadLocalSession.get();
	}

	@Override
	public void closeCurrentSession() {
		Session session = threadLocalSession.get();
		if (session == null) {
			logger.error("No session object present in thread local store.");
			return;
		}
		threadLocalSession.set(null);
		try {

			if (session.isOpen()) {
				session.clear();
				session.close();
			}
		} catch (HibernateException ex) {
			logger.error("Error occoured while closing Hibernate session.", ex);
		}

	}

	@Override
	public void commitCurrentTransaction() {
		try {
			getCurrentSession().flush();
		} catch (HibernateException ex) {
			logger.error("Error occoured while commiting Hibernate transaction.", ex);
			throw new RuntimeException("Error occoured while commiting Hibernate transaction.", ex);
		}
	}


	@Override
	public void openNewSession() {
		try {
			Session session = threadLocalSession.get();
			if (session != null && !session.isOpen()) {
				logger.error("A previously opened connection found in thread local.");
				closeCurrentSession();
			}
			session = getSessionFactory().openSession();
			if (session == null) {
				logger.error("Unable to get connection from database, null session provided by database.");
				throw new RuntimeException("Unable to get session from hibernate, null session provided by database.");
			}
			threadLocalSession.set(session);
		} catch (HibernateException e) {
			logger.error("Unable to get session from hibernate", e);
			throw new RuntimeException("Unable to get session from hibernate", e);
		}
	}

	@Override
	public void rollbackCurrentTransaction() {
		try {
			Session session = threadLocalSession.get();
			session.clear();
		} catch (HibernateException ex) {
			logger.error("Error occoured while rollbacking Hibernate transaction.", ex);
			throw new RuntimeException("Error occoured while rollbacking Hibernate transaction.", ex);
		}
	}


	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
