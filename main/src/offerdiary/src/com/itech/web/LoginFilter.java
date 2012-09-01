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

import com.itech.common.security.SecurityContext;
import com.itech.user.model.User;

public class LoginFilter implements Filter {
	private static final Logger logger = Logger.getLogger(LoginFilter.class);
	private static Set<String> bypassUrls = new HashSet<String>();
	static {
		bypassUrls.add("/");
		bypassUrls.add("/logout.do");
		bypassUrls.add("/home.do");
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
		bypassUrls.add("/offerTemplate.jsp");
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
		String reqUrl = httpRequest.getServletPath();
		logger.debug(reqUrl);
		User loggedInUser = (User) httpRequest.getSession().getAttribute(SecurityContext.USER_SESSION_KEY);
		if (loggedInUser == null) {
			if (!isBypass(reqUrl)) {
				httpRequest.getSession().setAttribute("redirectURL", reqUrl);
				httpResponse.sendRedirect("login.do");
				logger.debug("redirected to home: " + reqUrl);
				return;
			}
		} else {
			String redirectURL = (String) httpRequest.getSession().getAttribute("redirectURL");
			if (redirectURL != null && redirectURL.length() != 0) {
				httpRequest.getSession().setAttribute("redirectURL", null);
				httpResponse.sendRedirect(redirectURL.substring(1));
				logger.debug("redirected to : " + redirectURL);
				return;
			}

			if ("/index.jsp".equals(reqUrl) || "/".equals(reqUrl)) {
				httpRequest.getRequestDispatcher("/wallet.do").forward(req, resp);
				return;
			}
		}


		boolean loginSuccess = true;
		if (loginSuccess ) {
			filterChain.doFilter(req, resp);
		}
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
