package com.itech.common.web.action;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface ActionResponseAnnotation {
	/**
	 * Valid responseTypes
	 * {@link Forward}
	 * {@link CommonBeanResponse}
	 */
	public Class responseType();

	public int errorPageType() default FULL_PAGE;

	public static final int INCLUDED_PAGE = 0;

	public static final int FULL_PAGE = 1;
}
