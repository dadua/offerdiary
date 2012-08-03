package com.itech.common.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

public class FileUtil {
	private static final int CHAR_BUFFER_SIZE = 5000;
	private static Logger logger = Logger.getLogger(FileUtil.class);
	public static boolean appendDataToFile(String filePath, String data) {
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

	public static String readDataFromFile(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			return "";
		}
		FileReader fis = null;
		StringBuilder result = new StringBuilder("");
		char[] charBuffer = new char[CHAR_BUFFER_SIZE];
		try {
			fis = new FileReader(file);
			while (fis.ready()) {
				fis = new FileReader(file);
				int numOfChars = fis.read(charBuffer);
				if (numOfChars == -1) {
					break;
				}
				result.append(charBuffer, 0, numOfChars);
			}
		} catch (Exception e) {
			logger.error("Error in writing data to - " + filePath , e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error("Error in closing file - " + filePath , e);
				}
			}
		}
		return result.toString();
	}

	public static void main(String[] args) {
		String readDataFromFile = FileUtil.readDataFromFile("c:\\data\\redanarcards.txt");
		System.out.println(readDataFromFile);
	}
}
