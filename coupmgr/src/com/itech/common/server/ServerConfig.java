package com.itech.common.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ServerConfig {
	private static final Logger logger = Logger.getLogger(ServerConfig.class);
	public static String dbUser;
	public static String dbPassword;
	public static String dbIp;
	public static String dbPort;
	public static String dbName;
	public static String dbURL;
	public static boolean isRefreshStaticData;


	public static void init() throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		String path = ProjectContextListener.getServletContext().getRealPath("db.properties");
		File file = new File(path);
		FileInputStream fileInputStream = new FileInputStream(file);
		properties.load(fileInputStream);
		fileInputStream.close();
		ServerConfig.dbIp = properties.getProperty("DB_IP").trim();
		ServerConfig.dbUser = properties.getProperty("DB_USER").trim();
		ServerConfig.dbPassword = properties.getProperty("DB_PASSWORD").trim();
		ServerConfig.dbPort = properties.getProperty("DB_PORT").trim();
		ServerConfig.dbName = properties.getProperty("DB_NAME").trim();
		ServerConfig.isRefreshStaticData = Boolean.parseBoolean(properties.getProperty("IS_REFRESH_STATIC_DATA").trim());
		ServerConfig.dbURL = "jdbc:mysql://" + dbIp + ":" + dbPort +
		"/" + dbName + "?user=" + dbUser+ "&password=" + dbPassword ;


		logger.info("Server config loaded.");
		logger.info("Server Config  DB IP : " +  dbIp);
		logger.info("Server Config DB Port : " + dbPort);
		logger.info("Server Config DB Name : " + dbName);
		logger.info("Server Config DB User : " + dbUser);
		logger.info("Server Config DB Password : " + dbPassword);
		logger.info("Server Config DB URL : " + dbURL);
	}
}
