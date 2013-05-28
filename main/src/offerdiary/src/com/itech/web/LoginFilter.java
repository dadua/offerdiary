package com.itech.web;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.itech.common.CommonUtilities;
import com.itech.common.security.SecurityContext;
import com.itech.config.ProjectConfigs;
import com.itech.user.model.User;

public class LoginFilter implements Filter {

	private static final String REDIRECT_URL_SESSION_ATTR_KEY = "redirectURL";
	public static final String IS_PUBLIC_PAGE_SOURCE_ATTR_KEY = "isPublicSourceAttrKey";

	private static final Logger logger = Logger.getLogger(LoginFilter.class);

	private static Set<String> bypassUrls = new HashSet<String>();
	static {
		bypassUrls.add("/");
		bypassUrls.add("/logout.do");
		bypassUrls.add("/home.do");
		bypassUrls.add("/privacy.do");
		bypassUrls.add("/tnc.do");
		bypassUrls.add("/hearMore.do");
		bypassUrls.add("/setFbAdapter.do");
		bypassUrls.add("/login.do");
		bypassUrls.add("/signup.do");
		bypassUrls.add("/emailLogin.do");
		bypassUrls.add("/emailSignup.do");
		bypassUrls.add("/index.jsp");
		bypassUrls.add("/loginSignUp.jsp");
		bypassUrls.add("/commonHeader.jsp");
		bypassUrls.add("/comingSoon.jsp");
		bypassUrls.add("/getPassword.do");
		bypassUrls.add("/verifyEmailForPassword.do");
		bypassUrls.add("/gotPassword.do");
		bypassUrls.add("/getSharedOffer.do");
		bypassUrls.add("/sharedOffer.jsp");
		bypassUrls.add("/reShareOffer.do");
		bypassUrls.add("/user/pages/signUpFailed.jsp");
		bypassUrls.add("/authorizationFailureJsonResponse.do");
		bypassUrls.add("/aboutUs.do");
		bypassUrls.add("/offers.do");
		bypassUrls.add("/searchOffers.do");

		bypassUrls.add("/common/pages/message_page.jsp");
		bypassUrls.add("/verifyEmail.do");
		bypassUrls.add("/shareOfferViaEmail.do");
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}


	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) req;
		HttpServletResponse httpResponse = (HttpServletResponse) resp;

		setFbDetails(httpRequest);

		String reqUrl = httpRequest.getServletPath();
		/*
		if (contextContainsWWW(httpRequest)) {
			redirectToNormal(httpRequest, httpResponse);
			return;
		}
		logger.debug(reqUrl);
		 */
		User loggedInUser = (User) httpRequest.getSession().getAttribute(SecurityContext.USER_SESSION_KEY);
		if (loggedInUser == null) {
			if (!isBypass(reqUrl)) {
				if (isRequestAjax(httpRequest)) {
					updateRedirectUrlOnLogin(httpRequest);
					httpRequest.getRequestDispatcher("/authorizationFailureJsonResponse.do").forward(req, resp);
					return;
				}
				String queryString = httpRequest.getQueryString();
				String redirectURL  = reqUrl;
				if (CommonUtilities.isNotEmpty(queryString)) {
					redirectURL  +="?"+queryString;
				}
				httpRequest.getSession().setAttribute(REDIRECT_URL_SESSION_ATTR_KEY, redirectURL);
				httpResponse.sendRedirect("login.do");
				logger.debug("redirected to home: " + reqUrl);
				return;
			}
		} else {
			String redirectURL = (String) httpRequest.getSession().getAttribute(REDIRECT_URL_SESSION_ATTR_KEY);
			if ((redirectURL != null) && (redirectURL.length() != 0)) {
				httpRequest.getSession().setAttribute(REDIRECT_URL_SESSION_ATTR_KEY, null);
				httpResponse.sendRedirect(redirectURL.substring(1));
				logger.debug("redirected to : " + redirectURL);
				return;
			}

			if ("/index.jsp".equals(reqUrl) || "/".equals(reqUrl)) {
				httpRequest.getRequestDispatcher("/diary.do").forward(req, resp);
				return;
			}
		}

		filterChain.doFilter(req, resp);
	}


	private void updateRedirectUrlOnLogin(HttpServletRequest httpRequest) {
		String srcParamVal = httpRequest.getParameter("src");
		if (!CommonUtilities.isNullOrEmpty(srcParamVal)) {
			httpRequest.getSession().setAttribute(REDIRECT_URL_SESSION_ATTR_KEY, srcParamVal);
			httpRequest.setAttribute(IS_PUBLIC_PAGE_SOURCE_ATTR_KEY, true);
		}
	}


	private boolean isRequestAjax(HttpServletRequest httpRequest) {
		String headerForJsonCheck = httpRequest.getHeader("x-requested-with");
		return "XMLHttpRequest".equalsIgnoreCase(headerForJsonCheck);
	}

	private void redirectToNormal(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
		String reqUrl = httpRequest.getServletPath();
		String queryString = httpRequest.getQueryString();
		//String serverName = httpRequest.getServerName();
		String redirectURL  = ProjectConfigs.defaultServerUrl.get() + reqUrl;
		if (CommonUtilities.isNotEmpty(queryString)) {
			redirectURL  +="?"+queryString;
		}
		httpResponse.sendRedirect(redirectURL);
		logger.debug("redirected to home: " + reqUrl);
	}


	private boolean contextContainsWWW(HttpServletRequest httpRequest) {
		String serverName = httpRequest.getServerName();
		return serverName.toLowerCase().startsWith("www.offerdiary.com");
	}


	private static void setFbDetails(HttpServletRequest request) {
		request.setAttribute(WebConstatnts.FB_APP_ID_ATTRIBUTE_KEY, FbConstants.getFbAppId());
		request.setAttribute("odServerMode", ProjectConfigs.serverMode.get());
	}


	private boolean isBypass(String reqUrl) {
		for (String bypassUrl : bypassUrls) {
			if (reqUrl.equals(bypassUrl)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
