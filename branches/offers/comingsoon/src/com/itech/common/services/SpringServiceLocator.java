package com.itech.common.services;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.itech.common.db.ConnectionUtil;

public class SpringServiceLocator extends ServiceLocator implements  ApplicationContextAware, InitializingBean{
	private static final Logger logger = Logger.getLogger(SpringServiceLocator.class);
	private ApplicationContext applicationContext;
	private ConnectionUtil connectionUtil;
	public SpringServiceLocator() {

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		setInstance(this);

		try {
			//getConnectionUtil().createNewConnection();
			logger.info("Initializing beans");
			Map<String, Initialize> beansToBeInitialized =  applicationContext.getBeansOfType(Initialize.class);
			for (Entry<String, Initialize> initializeEntry : beansToBeInitialized.entrySet()) {
				logger.info("Initializing bean : " + initializeEntry.getKey());
				initializeEntry.getValue().init();
				//getConnectionUtil().commitCurrentConnection();
			}
			logger.info("All beans initialized successfully");
		} catch (Exception e) {
			logger.error("Failed to initialize beans.", e);
			throw e;
		} finally {
			//getConnectionUtil().releaseCurrentConnection();
		}
	}

	private static void setInstance(ServiceLocator serviceLocator) {
		ServiceLocator.instance = serviceLocator;
	}

	public static ServiceLocator getInstance() {
		return  instance;
	}

	@Override
	public Object getBean(String beanName) {
		return getBeanByName(beanName);
	}

	@Override
	public  <T> T getBean(Class<T> clazz) {
		return getBeanByType(clazz);
	}

	private <T> T getBeanByType(Class<T> clazz) {
		Map<String, T> beans = applicationContext.getBeansOfType(clazz);
		if (beans.size() > 1) {
			throw new RuntimeException("More than one bean of type " + clazz + " is available." +
			" Please try to retrive the bean by name");
		}
		if (beans.isEmpty()) {
			throw new RuntimeException("Could not find a bean of type " + clazz +
			" Please try to retrive the bean by name");
		}
		return beans.values().iterator().next();
	}

	private Object getBeanByName(String beanName) {
		try {
			return applicationContext.getBean(beanName);
		} catch (NoSuchBeanDefinitionException nsbde) {
			throw new RuntimeException("Bean with the name " +
					beanName + " not found.", nsbde);
		} catch (BeansException be) {
			throw new RuntimeException("Bean with the name " +
					beanName + " could not be obtained.", be);
		}
	}

	public void setConnectionUtil(ConnectionUtil connectionUtil) {
		this.connectionUtil = connectionUtil;
	}

	public ConnectionUtil getConnectionUtil() {
		return connectionUtil;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
	throws BeansException {
		this.applicationContext = applicationContext;

	}
}
