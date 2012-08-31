package com.itech.test;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;

import com.itech.common.util.UtilHttp;

public class TestParseQueryString {

	/**
	 * @param args
	 * @throws URISyntaxException 
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws URISyntaxException, UnsupportedEncodingException {
		// TODO Auto-generated method stub
		
		String qs = "access_token=175776209113921|2.f9zsJm1EfJCny55_0_MtiQ__.3600.1301320800-100001451727305|urMVozy5_4RatjITdVQOmM2N6pQ&expires=6496";
		Map<String , List<String>> nameValues = UtilHttp.parseQueryString(qs);
		System.out.println(nameValues.size());
		System.out.println(java.util.regex.Pattern.quote("?"));
		
		System.out.println(nameValues.get("access_token"));
		System.out.println(qs.split(Pattern.quote("?")).length);
		
		
		URI url = URIUtils.createURI("http", "example.com", -1, "", URLEncoder.encode(qs), null);
		List<NameValuePair> params = URLEncodedUtils.parse(url, "UTF-8");
		
		for (NameValuePair param: params) {
			System.out.println(param);
		}

	}

}
