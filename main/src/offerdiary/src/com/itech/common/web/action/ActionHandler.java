package com.itech.common.web.action;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import com.google.gson.reflect.TypeToken;
import com.itech.common.db.hibernate.HibernateSessionFactory;
import com.itech.common.exeption.CommonException;
import com.itech.common.exeption.ReturnCode;
import com.itech.common.exeption.ReturnCodes;
import com.itech.common.exeption.ValidationException;
import com.itech.common.resource.Resource;
import com.itech.common.security.Authorization;
import com.itech.common.security.SecurityContext;
import com.itech.common.security.SecurityContextHolder;
import com.itech.common.services.ServiceLocator;
import com.itech.user.model.User;
import com.itech.web.ActionMappings;


public class ActionHandler {
	private static final Logger logger = Logger.getLogger(ActionHandler.class);
	private static SecurityContextHolder securityContextHolder;
	private static HibernateSessionFactory hibernateSessionFactory;
	private static final Locale SERVER_LOCAL = Resource.getServerLocale();
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
		Method executeMethod = null;
		Transaction transaction = null;
		try {
			User user = (User) request.getSession().getAttribute(SecurityContext.USER_SESSION_KEY);
			getSecurityContextHolder().setContext(new SecurityContext(user));
			getHibernateSessionFactory().openNewSession();
			transaction = getHibernateSessionFactory().getCurrentSession().beginTransaction();
			ActionMapping actionMapping = ActionMappings.getAction(actionName);
			Object actionBean = null;
			try {
				actionBean = ServiceLocator.getInstance().getBean(actionMapping.getBeanClass());
			} catch (Exception e) {
				logger.warn("no bean present in services for - " + actionName, e);
			}
			if (actionBean == null) {
				actionBean = actionMapping.getBeanClass().newInstance();
			}

			executeMethod = actionMapping.getBeanClass().getMethod(actionMapping.getMethodName(),
					HttpServletRequest.class, HttpServletResponse.class );
			Authorization classLevelAuthorization = actionBean.getClass().getAnnotation(Authorization.class);
			if (classLevelAuthorization != null) {
				if (!classLevelAuthorization.userRole().equals(user.getUserRole())) {
					ActionResponseAnnotation actionResponseAnnotation = executeMethod.getAnnotation(ActionResponseAnnotation.class);
					return prepareErrorPageResponse(actionResponseAnnotation, ReturnCodes.AUTHORIZATION_FAILURE, request);
				}
			}

			Authorization methodLevelAuthorization = executeMethod.getAnnotation(Authorization.class);
			if (methodLevelAuthorization != null) {
				if (!methodLevelAuthorization.userRole().equals(user.getUserRole())) {
					ActionResponseAnnotation actionResponseAnnotation = executeMethod.getAnnotation(ActionResponseAnnotation.class);
					return prepareErrorPageResponse(actionResponseAnnotation, ReturnCodes.AUTHORIZATION_FAILURE, request);
				}
			}


			Response responseAction = (Response) executeMethod.invoke(actionBean, request, response);
			transaction.commit();
			return responseAction;

		} catch (Exception invocationExceptio) {
			Exception ex = (Exception) invocationExceptio.getCause();
			logException(ex, executeMethod);
			if (executeMethod == null) {
				throw ex;
			}

			ActionResponseAnnotation actionResponseAnnotation = executeMethod.getAnnotation(ActionResponseAnnotation.class);
			ReturnCode returnCode = ReturnCodes.INTERNAL_ERROR;
			if (CommonException.class.isAssignableFrom(ex.getClass())) {
				returnCode = ((CommonException) ex).getRetCode();
			}

			if (actionResponseAnnotation == null) {
				throw ex;
			}

			Class<?> responseType = actionResponseAnnotation.responseType();
			if (CommonBeanResponse.class.isAssignableFrom(responseType)) {
				return prepareScorBeanResponse(returnCode);

			}

			if (Forward.class.isAssignableFrom(responseType) || Redirect.class.isAssignableFrom(responseType)) {
				return prepareErrorPageResponse(actionResponseAnnotation, returnCode, request);
			}

			throw ex;

		} finally {
			getHibernateSessionFactory().closeCurrentSession();
			if (transaction !=null && transaction.isActive()) {
				try{
					transaction.rollback();
				} catch (Exception e) {
					logger.error("Failed to rollback transaction", e);
				}
			}
		}

	}

	private static void logException(Exception ex, Method method) {
		String methodName = method.getDeclaringClass().getName() + "." + method.getName();

		if (!CommonException.class.isAssignableFrom(ex.getClass())) {
			logger.error("An error occurred while calling - " + methodName, ex);
			return;
		}

		if (ValidationException.class.isAssignableFrom(ex.getClass())) {
			logger.debug("An error occurred while calling - " + methodName, ex);
			return;
		}

		logger.error("An error occurred while calling - " + methodName, ex);

	}

	private static ActionResponse prepareErrorPageResponse(ActionResponseAnnotation actionResponseAnnotation, ReturnCode returnCode, HttpServletRequest request) {
		String message = getMsg(getResource(), SERVER_LOCAL, returnCode.getDisplayKey());
		request.setAttribute(URLConstants.COMMON_ERROR_MESSAGE_ATTR_NAME, message);
		if (actionResponseAnnotation == null || ActionResponseAnnotation.FULL_PAGE == actionResponseAnnotation.errorPageType()) {
			return ActionResponse.forward( URLConstants.COMMON_ERROR_PAGE);
		} else {
			return ActionResponse.forward( URLConstants.COMMON_ERROR_INCLUDE_PAGE);
		}
	}

	private static Response prepareScorBeanResponse(ReturnCode returnCode) {
		Result<String> resultObj= new Result<String>();
		Type resType = new TypeToken<Result<String>>(){}.getType();
		resultObj.setError(getResource(),Resource.getServerLocale() ,returnCode);
		return new CommonBeanResponse(resultObj, resType);
	}

	public static String getMsg(Resource resource, Locale locale, String key){
		return resource.getString(key,locale);
	}

	public static Resource getResource() {
		return ServiceLocator.getInstance().getBean(Resource.class);
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



	public static HibernateSessionFactory getHibernateSessionFactory() {
		if (hibernateSessionFactory == null) {
			hibernateSessionFactory = ServiceLocator.getInstance().getBean(HibernateSessionFactory.class);
		}
		return hibernateSessionFactory;
	}



}
