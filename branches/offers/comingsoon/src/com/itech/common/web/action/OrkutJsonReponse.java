package com.itech.common.web.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.me.JSONObject;

public class OrkutJsonReponse implements Response {
	private  JSONObject jsonObject;
	private  String message = "Success";
	private  List<JSONObject> jsonObjects;
	public OrkutJsonReponse(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	public OrkutJsonReponse(List<JSONObject> jsonObjects) {
		this.jsonObjects = jsonObjects;
	}

	public OrkutJsonReponse(String errorMessage) {
		this.message = errorMessage;
	}
	public  void respond( HttpServletRequest request, HttpServletResponse response )
	throws IOException, ServletException {
		if (jsonObjects != null) {
			response.getWriter().write("{\"returnCode\":0, errorMessage:\"" + message +"\"," +
			"\"data\":");
			String jsonArray = getJsonArray(jsonObjects);
			response.getWriter().write(jsonArray + "}");
		} else if (jsonObject != null) {
			response.getWriter().write("{\"returnCode\":0, errorMessage:\"" + message +"\"," +
					"\"data\":[" + jsonObject.toString() +"]}");
		} else {
			response.getWriter().write("{\"returnCode\":1, errorMessage:\"" + message +"\"}");
		}

	}

	private String getJsonArray(List<JSONObject> jsonObjects) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		for (JSONObject jsonObject : jsonObjects) {
			builder.append(jsonObject.toString()).append(",");
		}
		builder.deleteCharAt(builder.length()-1);
		return builder.toString();
	}
}
