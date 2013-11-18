package com.itech.common.server;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.PropertyConfigurator;

public class ProjectContextListener implements ServletContextListener{
	private static final String SERVER_CONFIG_PROPERTIES = "db.properties";
	private static ServletContext servletContext;
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent servletContext) {
		ProjectContextListener.setServletContext(servletContext.getServletContext());
		try {
			String log4jFile = ProjectContextListener.getServletContext().getRealPath("log4j.properties");
			PropertyConfigurator.configure(log4jFile);
			ProjectLoader.init(getServletContext().getRealPath(SERVER_CONFIG_PROPERTIES));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setServletContext(ServletContext servletContext) {
		ProjectContextListener.servletContext = servletContext;
	}

	public static ServletContext getServletContext() {
		return servletContext;
	}

}
