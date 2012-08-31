/**
 * Created by IntelliJ IDEA.
 * User: mdespain
 * Date: Feb 3, 2006
 * Time: 3:29:10 PM
 * Copyright (C) 2005 McAfee, Inc.  All Rights Reserved.
 */
package com.itech.common.web.action;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Redirect extends ActionResponse
{
	private String uri = null;

	public Redirect( String uri ){
		this.uri = uri;
	}

	@Override
	public void respond( HttpServletRequest request,
			HttpServletResponse response )
	throws IOException, ServletException {
		response.sendRedirect( response.encodeRedirectURL(uri));
	}

}
