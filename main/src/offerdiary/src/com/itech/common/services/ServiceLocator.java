package com.itech.common.services;

import java.util.Map;

public abstract class ServiceLocator {
	static ServiceLocator instance;

	public static ServiceLocator getInstance() {
		return  instance;
	}

	public abstract Object getBean(String beanName);

	public  abstract <T> T getBean(Class<T> clazz);


	public abstract <T> Map<String, T> getBeans(Class<T> clazz);

}
