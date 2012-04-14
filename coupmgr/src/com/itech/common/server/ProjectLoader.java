package com.itech.common.server;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.itech.common.db.DBConnectionManager;


public class ProjectLoader {
	private static final Logger logger = Logger.getLogger(ProjectLoader.class);

	public static void init(String serverConfigFile) throws FileNotFoundException, IOException, ClassNotFoundException {
		ServerConfig.init(serverConfigFile);
		DBConnectionManager.init();
		//DBConnectionManager.testDb();
	}
}
