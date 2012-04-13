package com.itech.common.web.action;


public class ActionMapping {
	private String uri;
	private Class beanClass;
	private String methodName;

	public ActionMapping(String uri, Class beanClass, String methodName) {
		super();
		this.uri = uri;
		this.beanClass = beanClass;
		this.methodName = methodName;
	}

	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public Class getBeanClass() {
		return beanClass;
	}
	public void setBeanClass(Class beanClass) {
		this.beanClass = beanClass;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

}
