package com.itech.common.web.action;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itech.common.db.ConnectionUtil;
import com.itech.common.db.ConnectionUtilImpl;
import com.itech.common.security.SecurityContext;
import com.itech.common.security.SecurityContextHolder;
import com.itech.common.security.ThreadLocalSecurityContextHolder;
import com.itech.coupon.model.User;
import com.itech.web.ActionMappings;


public class ActionHandler {
	private static SecurityContextHolder securityContextHolder;
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
		ConnectionUtil connectionUtil =  new ConnectionUtilImpl();
		try {
			User user = (User) request.getSession().getAttribute(SecurityContext.USER_SESSION_KEY);
			getSecurityContextHolder().setContext(new SecurityContext(user));
			connectionUtil.createNewConnection();
			ActionMapping actionMapping = ActionMappings.getAction(actionName);
			Object actionBean = actionMapping.getBeanClass().newInstance();

			Method executeMethod = actionMapping.getBeanClass().getMethod(actionMapping.getMethodName(),
					HttpServletRequest.class, HttpServletResponse.class );
			Response responseAction = (Response) executeMethod.invoke(actionBean, request, response);
			connectionUtil.commitCurrentConnection();
			return responseAction;
		} finally {
			connectionUtil.releaseCurrentConnection();
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

	public void setSecurityContextHolder(SecurityContextHolder securityContextHolder) {
		this.securityContextHolder = securityContextHolder;
	}

	public static SecurityContextHolder getSecurityContextHolder() {
		if (securityContextHolder == null) {
			securityContextHolder = new ThreadLocalSecurityContextHolder();
		}
		return securityContextHolder;
	}


}
