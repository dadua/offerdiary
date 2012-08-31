/**
 * Created by IntelliJ IDEA.
 * User: mdespain
 * Date: Feb 3, 2006
 * Time: 3:28:55 PM
 * Copyright (C) 2005 McAfee, Inc.  All Rights Reserved.
 */
package com.itech.common.web.action;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Forward extends ActionResponse {
	private final String uri;

	public Forward( String url ) {
		this.uri = url;
	}

	@Override
	public void respond( HttpServletRequest request,
			HttpServletResponse response ) throws IOException, ServletException {
		request.getRequestDispatcher(uri).forward(request, response);
	}

}
