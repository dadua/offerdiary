package com.itech.web;

import java.io.IOException;

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
import com.itech.coupon.model.User;

public class LoginFilter implements Filter {
	private static final Logger logger = Logger.getLogger(LoginFilter.class);
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
			if (!"/logout.do".equalsIgnoreCase(reqUrl) && !"/".equalsIgnoreCase(reqUrl) && !"/setFbAdapter.do".equalsIgnoreCase(reqUrl) &&
					!"/login.do".equalsIgnoreCase(reqUrl) && !"/signup.do".equalsIgnoreCase(reqUrl)) {
				httpResponse.sendRedirect("");
				return;
			}
		}

		boolean loginSuccess = true;
		if (loginSuccess ) {
			filterChain.doFilter(req, resp);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
