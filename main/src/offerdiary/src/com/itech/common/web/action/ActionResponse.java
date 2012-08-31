package com.itech.common.web.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public abstract class ActionResponse implements Response {

	public static Forward forward(String uri) {
		return new Forward(uri);
	}

	public static Redirect redirect( String uri ) {
		return new Redirect( uri );
	}

	public abstract void respond( HttpServletRequest request, HttpServletResponse response )
	throws IOException, ServletException;


	public static String normalizeContext( String context )	{
		if (!context.startsWith( "/" ))
		{
			context = "/" + context;
		}
		return context;
	}

	public static String prefixRequestURI(String requestURI){
		if (!requestURI.startsWith( "/" ))
		{
			requestURI = "/" + requestURI;
		}
		return requestURI;
	}

	public static String createRedirectURI(String context, String requestURI) {
		return normalizeContext(context)+prefixRequestURI( requestURI);
	}

	public static String getServerPath(HttpServletRequest request,
			HttpServletResponse response) {
		//		String url = request.getRequestURL().toString();
		//		String queryString = request.getQueryString();
		//		String uri = request.getRequestURI();
		//		String serverPath = ActionResponse.getServerPath(request, response);
		//		String scheme = request.getScheme();
		//		String serverName = request.getServerName();
		//		int portNumber = request.getServerPort();
		//		String contextPath = request.getContextPath();
		//		String servletPath = request.getServletPath();
		//		String pathInfo = request.getPathInfo();
		//		String query = request.getQueryString();


		String serverName = request.getServerName();
		String port = request.getServerPort() == 80? "" : ":" + request.getServerPort();
		String contextPath = request.getContextPath();
		return "http://" + serverName + port + contextPath;

	}

}