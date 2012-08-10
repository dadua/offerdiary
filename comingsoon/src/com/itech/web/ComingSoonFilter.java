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

public class ComingSoonFilter implements Filter {
	private static final Logger logger = Logger.getLogger(LoginFilter.class);
	private static Set<String> allowedUrls = new HashSet<String>();
	static {
		allowedUrls.add("/");
		allowedUrls.add("/hearMore.do");
		allowedUrls.add("/commonHeader.jsp");
		allowedUrls.add("/comingSoon.jsp");
		
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
		if (!isAllowed(reqUrl)) {
				httpResponse.sendRedirect("comingSoon.jsp");
				logger.debug("redirected to home: " + reqUrl);
				return;
			}
		else{
			filterChain.doFilter(req, resp);
		}
	}

	private boolean isAllowed(String reqUrl) {
		for (String allowedUrl : allowedUrls) {
			if (reqUrl.equals(allowedUrl)) {
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
