package com.itech.web;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Controller;
import org.springframework.web.portlet.bind.annotation.ActionMapping;

import com.itech.common.services.Initialize;
import com.itech.common.services.ServiceLocator;

public class ActionMappingService implements Initialize {

	@Override
	public void init() {
		Map<String, Object> allBeans = ServiceLocator.getInstance().getBeans(Object.class);
		for (Entry<String, Object> beanEntry : allBeans.entrySet()) {

			if (!isController(beanEntry.getValue().getClass())) {
				continue;
			}

			Class beanClass = beanEntry.getValue().getClass();
			Method[] methods = beanClass.getMethods();
			for (Method method : methods) {
				ActionMapping actionMappingAnnotation = method.getAnnotation(ActionMapping.class);
				if (actionMappingAnnotation == null) {
					continue;
				}

				com.itech.common.web.action.ActionMapping actionMapping = new com.itech.common.web.action.ActionMapping(
						actionMappingAnnotation.value(), beanClass, method.getName());
				ActionMappings.add(actionMapping);

			}
		}

	}

	private boolean isController(Class clazz) {
		boolean isController = clazz.isAnnotationPresent(Controller.class);
		return isController;
	}

}
