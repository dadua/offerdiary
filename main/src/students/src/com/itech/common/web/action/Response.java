package com.itech.common.web.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Response {
	public abstract void respond( HttpServletRequest request, HttpServletResponse response )
	throws IOException, ServletException;
}
