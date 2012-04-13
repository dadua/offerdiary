package com.itech.test;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.me.JSONException;
import org.json.me.JSONObject;

public class TestHttpClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet("http://graph.facebook.com/alok.id");
		try {
			HttpResponse httpResp = httpClient.execute(httpGet);
			int contentLength = (int) httpResp.getEntity().getContentLength();
			byte [] resp = new byte[contentLength];
			httpResp.getEntity().getContent().read(resp, 0, contentLength);
			String strResp = new String(resp);
			JSONObject jsonObj = new JSONObject(strResp);
			System.out.println(jsonObj.get("id"));
			System.out.println(strResp);
			System.out.println(jsonObj.toString(4));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
