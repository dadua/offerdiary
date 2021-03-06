package com.itech.common.util;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

public class UtilHttp {
	public static final int CONN_TIME_OUT = (1000 * 10);//10 second timeout
	private int connTimeOut = CONN_TIME_OUT;

	public UtilHttp() {

	}

	public UtilHttp(int connTimeOut) {
		this.connTimeOut = connTimeOut * 1000;
	}

	public static Map<String, List<String>> parseQueryString(String qs) {
		Map<String, List<String>> nameValues = new HashMap<String, List<String>>();
		StringTokenizer pairs = new StringTokenizer(qs, "&");
		while (pairs.hasMoreTokens()) {
			String pair = pairs.nextToken();
			String []nameValuePair = pair.split("=");
			if (nameValues.containsKey(nameValuePair[0])) {
				nameValues.get(nameValuePair[0]).add(nameValuePair[1]);
			} else {
				List<String> vals = new ArrayList<String>();
				vals.add(nameValuePair[1]);
				nameValues.put(nameValuePair[0], vals);
			}
		}
		return nameValues;
	}

	public static String getRedirectedURLLocation(String currentURL){
		try{
			HttpParams httpParams = new BasicHttpParams();
			httpParams.setParameter("http.protocol.handle-redirects",false);
			HttpGet httpGetter = new HttpGet(currentURL);
			httpGetter.setParams(httpParams);
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(httpGetter);
			Header httpHeader = response.getFirstHeader("location");
			return httpHeader.toString();
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}

	public String getHttpResponse(String urlString) {
		StringBuffer sb = new StringBuffer(10000);
		try {
			URL url = new URL(urlString );
			URLConnection urlCon = url.openConnection();
			urlCon.setConnectTimeout(this.connTimeOut);
			urlCon.connect();
			InputStream in = new BufferedInputStream(urlCon.getInputStream());
			Reader reader = new InputStreamReader(in , "UTF-8");
			int c;
			while ((c = in.read()) != -1) {
				sb.append((char) c);
			}
			reader.close();
			in.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {

		}
		return sb.toString();
	}

	public String getHttpResponseForGetRequest(String urlString, Map<String, String> paramsMap) {
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		for (Entry<String, String> entry : paramsMap.entrySet()) {
			params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		return getHttpResponse(urlString.toString() + "?" + URLEncodedUtils.format(params, "UTF-8"));
	}

	public InputStream getHttpResponseAsInputStream(String urlString) {
		InputStream in = null;
		try {
			URL url = new URL(urlString );
			URLConnection urlCon = url.openConnection();
			urlCon.setConnectTimeout(this.connTimeOut);
			urlCon.connect();
			in = new BufferedInputStream(urlCon.getInputStream());
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {

		}
		return in;
	}

	private static final UtilHttp DEFAULT_UTIL_HTTP_TEN_SEC_TIMEOUT = new UtilHttp();

	public static String fetchHttpResponse(String url) {
		return DEFAULT_UTIL_HTTP_TEN_SEC_TIMEOUT.getHttpResponse(url);
	}

	public static String fetchHttpResponseForGetRequest(String url, Map<String, String> params ) {
		return DEFAULT_UTIL_HTTP_TEN_SEC_TIMEOUT.getHttpResponseForGetRequest(url, params);
	}

	public static InputStream fetchHttpResponseAsInputStream(String url) {
		return DEFAULT_UTIL_HTTP_TEN_SEC_TIMEOUT.getHttpResponseAsInputStream(url);
	}
}
