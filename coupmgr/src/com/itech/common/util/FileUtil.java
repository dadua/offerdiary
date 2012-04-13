package com.itech.common.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

public class FileUtil {
	private static Logger logger = Logger.getLogger(FileUtil.class);
	public synchronized static boolean appendDataToFile(String filePath, String data) {
		File file = new File(filePath);
		if (!file.getParentFile().exists()) {
			return false;
		}
		FileWriter fos = null;
		try {
			fos = new FileWriter(file, true);
			fos.write(data);
			fos.write("\n");
			fos.flush();
		} catch (Exception e) {
			logger.error("Error in writing data to - " + filePath , e);
			return false;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					logger.error("Error in closing file - " + filePath , e);
				}
			}
		}
		return true;
	}
}
