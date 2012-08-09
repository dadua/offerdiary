package com.itech.test;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.print.URIException;

import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import com.itech.web.FbConstants;

public class TestUrlEncoding {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			List <BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("scope", FbConstants.APP_PERMISSIONS_REQUIRED));
			params.add(new BasicNameValuePair("client_id", FbConstants.APP_ID));
			URI url = URIUtils.createURI("http", "graph.facebook.com", -1, "oauth/auth",
					URLEncodedUtils.format(params, "UTF-8"), null);
			System.out.println(url);
			
//			URLEncoder.encode(s, enc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
