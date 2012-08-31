package com.itech.common.test.db;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class DbunitConnectionProxy implements InvocationHandler{
	private final Object target;


	private DbunitConnectionProxy(final Object obj) {
		target = obj;
	}

	public static Object createProxy(final Object obj) {
		if (obj == null) {
			return null;
		}

		Class<?>[] interfaces = new Class[1];
		interfaces[0] = java.sql.Connection.class;

		return Proxy.newProxyInstance(obj.getClass().getClassLoader(), interfaces,
				new DbunitConnectionProxy(obj));
	}

	@Override
	public Object invoke(Object proxy, final Method method, final Object[] args) throws Throwable {
		Object result = null;
		if(method.getName().equals("commit")){
			//			logger.info("commit called by dbunit");
			//			logger.info("the proxy is preventing the commit");
			return null;
		}
		result = method.invoke(target, args);
		return result;
	}
}