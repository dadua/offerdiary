package com.itech.web;

import java.util.HashMap;
import java.util.Map;

import com.itech.common.web.action.ActionMapping;

public class ActionMappings {
	private static final Map<String, ActionMapping> actions = new HashMap<String, ActionMapping>();
	public static  void add(ActionMapping actionMapping) {
		actions.put(actionMapping.getUri(), actionMapping);
	}

	public static ActionMapping getAction(String uri) {
		return actions.get(uri);
	}

	static {


	}
}
