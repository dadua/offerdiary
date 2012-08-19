package com.itech.common.resource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;


public class SpringResource extends Resource implements BeanFactoryAware {
	public SpringResource( String... bundleNames )
	{
		super( null, bundleNames );
	}

	public void setBeanFactory( BeanFactory beanFactory ) throws BeansException
	{
		setClassLoader(beanFactory.getClass().getClassLoader());
	}
}

