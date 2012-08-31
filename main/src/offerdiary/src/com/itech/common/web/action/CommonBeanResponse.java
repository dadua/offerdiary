package com.itech.common.web.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;


public class CommonBeanResponse implements Response {

	private Object bean = null;
	private java.lang.reflect.Type type= null;
	public CommonBeanResponse(Object bean) {
		super();
		this.bean = bean;
	}
	public CommonBeanResponse(Object bean,java.lang.reflect.Type type) {
		super();
		this.bean = bean;
		this.type = type;
	}
	public CommonBeanResponse() {
		super();
	}
	public void respond(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		Object bean = getBean();
		String gsonStr="";
		Gson gson = new Gson();
		if(this.getType()==null){
			gsonStr = gson.toJson(bean);
		}
		else{
			gsonStr = gson.toJson(bean,this.getType());
		}
		response.getWriter().write(gsonStr);

	}
	public Object getBean() {
		return bean;
	}
	public void setBean(Object bean) {
		this.bean = bean;
	}

	public java.lang.reflect.Type getType() {
		return type;
	}
}
