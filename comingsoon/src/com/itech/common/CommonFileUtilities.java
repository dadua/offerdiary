package com.itech.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.log4j.Logger;

import com.itech.common.util.FileUtil;

public class CommonFileUtilities {

	private static final int CHAR_BUFFER_SIZE = 5000;
	private static Logger logger = Logger.getLogger(FileUtil.class);

	public static String getResourceFileAsString(String resourceFilename) {
		try {
			InputStream in = CommonFileUtilities.class.getClassLoader().getResourceAsStream(resourceFilename);
			InputStreamReader is = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(is);
			String read = br.readLine();
			String asString = read;
			while(read != null) {
				read =  br.readLine();
				asString = asString + read;
			}
			return asString;
		}catch (Exception e) {
			throw new RuntimeException("error inreading data from file", e);
		}
	}


	public static File getResourceFileAsFile(String resourceFilename) throws URISyntaxException{
		URL url = CommonFileUtilities.class.getClassLoader().getResource(resourceFilename);
		File file = null;
		file = new File(url.toURI());
		return file;
	}


	public static boolean appendDataToFile(String filePath, String data, boolean overwiteData) {
		File file = new File(filePath);
		if (!file.getParentFile().exists()) {
			return false;
		}
		FileWriter fos = null;
		try {
			fos = new FileWriter(file, !overwiteData);
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
			logger.warn("File not found " + filePath);
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
