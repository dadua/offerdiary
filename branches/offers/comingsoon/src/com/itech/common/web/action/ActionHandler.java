package com.itech.common.web.action;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itech.common.db.ConnectionUtil;
import com.itech.common.db.hibernate.HibernateSessionFactory;
import com.itech.common.security.SecurityContext;
import com.itech.common.security.SecurityContextHolder;
import com.itech.common.services.ServiceLocator;
import com.itech.coupon.model.User;
import com.itech.web.ActionMappings;


public class ActionHandler {
	private static SecurityContextHolder securityContextHolder;
	private static ConnectionUtil connectionUtil;
	private static HibernateSessionFactory hibernateSessionFactory;
	public static void handleAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType( "text/html; charset=UTF-8" );
		response.setHeader( "Cache-Control", "no-cache" );
		response.setHeader( "Pragma", "no-cache" );
		response.setDateHeader( "Expires", System.currentTimeMillis() );
		String actionName = extractAction(request);
		Response responseAction = executeAction(actionName, request, response);
		responseAction.respond(request, response);
	}

	private static Response executeAction( String actionName, HttpServletRequest request,
			HttpServletResponse response ) throws Exception {

		try {
			User user = (User) request.getSession().getAttribute(SecurityContext.USER_SESSION_KEY);
			getSecurityContextHolder().setContext(new SecurityContext(user));
			getConnectionUtil().createNewConnection();
			getHibernateSessionFactory().openNewSession();
			ActionMapping actionMapping = ActionMappings.getAction(actionName);
			Object actionBean = actionMapping.getBeanClass().newInstance();

			Method executeMethod = actionMapping.getBeanClass().getMethod(actionMapping.getMethodName(),
					HttpServletRequest.class, HttpServletResponse.class );
			Response responseAction = (Response) executeMethod.invoke(actionBean, request, response);
			getConnectionUtil().commitCurrentConnection();
			getHibernateSessionFactory().getCurrentSession().flush();
			return responseAction;
		} finally {
			getConnectionUtil().releaseCurrentConnection();
			getHibernateSessionFactory().closeCurrentSession();
		}
	}

	public static void init() {

	}

	private static String extractAction( HttpServletRequest request) {
		String uri = request.getRequestURI();
		int i = uri.lastIndexOf( '/' );
		if (i == -1) {
			return uri;
		}
		return uri.substring( i + 1 );

	}

	public static void setSecurityContextHolder(SecurityContextHolder securityContextHolder) {
		ActionHandler.securityContextHolder = securityContextHolder;
	}

	public static SecurityContextHolder getSecurityContextHolder() {
		if (securityContextHolder == null) {
			securityContextHolder = ServiceLocator.getInstance().getBean(SecurityContextHolder.class);
		}
		return securityContextHolder;
	}

	public static void setConnectionUtil(ConnectionUtil connectionUtil) {
		ActionHandler.connectionUtil = connectionUtil;
	}

	public static ConnectionUtil getConnectionUtil() {
		if (connectionUtil == null) {
			connectionUtil = ServiceLocator.getInstance().getBean(ConnectionUtil.class);
		}
		return connectionUtil;
	}

	public static HibernateSessionFactory getHibernateSessionFactory() {
		if (hibernateSessionFactory == null) {
			hibernateSessionFactory = ServiceLocator.getInstance().getBean(HibernateSessionFactory.class);
		}
		return hibernateSessionFactory;
	}



}
