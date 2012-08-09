package com.itech.common.test;

import org.hibernate.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.itech.common.db.hibernate.HibernateSessionFactory;
import com.itech.common.services.ServiceLocator;

public class HibernateCommonTestDAO extends CommonTestBase {
	private final ApplicationContext context;
	private final HibernateSessionFactory hibernateSessionFactory;

	public HibernateCommonTestDAO() {
		context = new ClassPathXmlApplicationContext("test-beans.xml");
		hibernateSessionFactory = ServiceLocator.getInstance().getBean(HibernateSessionFactory.class);
	}

	@Override
	protected void setUp() throws Exception {
		hibernateSessionFactory.openNewSession();

	}

	@Override
	protected void tearDown() throws Exception {
		hibernateSessionFactory.rollbackCurrentTransaction();
		hibernateSessionFactory.closeCurrentSession();
		
	}

	public Session getSession() {
		return hibernateSessionFactory.getCurrentSession();
	}

}
