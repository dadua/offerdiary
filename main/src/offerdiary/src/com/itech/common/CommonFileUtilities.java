package com.itech.common;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.itech.common.util.FileUtil;

public class CommonFileUtilities {

	private static final int CHAR_BUFFER_SIZE = 5000;
	private static Logger logger = Logger.getLogger(CommonFileUtilities.class);

	public static String getResourceFileAsString(String resourceFilename) {
		return getResourceFileAsString(resourceFilename, false);
	}

	public static String getResourceFileAsString(String sourceFileName,
			boolean isAbsoluteUrl) {
		InputStream in = null;
		try {
			if (isAbsoluteUrl) {
				in = new FileInputStream(sourceFileName);
			} else {
				in = CommonFileUtilities.class.getClassLoader().getResourceAsStream(sourceFileName);
			}
			InputStreamReader is = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(is);
			String read = null;
			String asString = "";
			while((read = br.readLine())!= null) {
				asString += read;
			}
			return asString;
		}catch (Exception e) {
			throw new RuntimeException("error inreading data from file", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("error in closing file stream", e);
				}
			}
		}
	}

	public static String[] getResourceFileAsLines(String resourceFilename) {
		try {
			List<String> lines = new ArrayList<String>();
			InputStream in = CommonFileUtilities.class.getClassLoader().getResourceAsStream(resourceFilename);
			InputStreamReader is = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(is);
			String read = null;
			while((read = br.readLine())!= null) {
				lines.add(read);
			}
			return lines.toArray(new String[] {});
		}catch (Exception e) {
			throw new RuntimeException("error inreading data from file", e);
		}
	}


	public static void writeImageToImageRepoFolder(Map<String, String> imageNameSrcMap){
		for (Map.Entry<String, String> entry : imageNameSrcMap.entrySet()) {
			String nameOfImage =  entry.getKey();
			String imageSrc=  entry.getValue();
			if((null == imageSrc) || (null == nameOfImage) || imageSrc.isEmpty() || nameOfImage.isEmpty()){
				continue;
			}
			try{
				URL url = new URL(imageSrc);
				BufferedImage image = null;
				image = ImageIO.read(url);
				File file = new File("c:\\data\\storeImages" + "\\" + nameOfImage+".jpg");
				ImageIO.write(image, "jpg", file);
			}catch(Exception e){
				logger.error(nameOfImage+ " image could not be written ", e);
			}
		}
	}

	public static File getResourceFileAsFile(String resourceFilename, boolean isAbsoluteUrl) {
		if (isAbsoluteUrl) {
			return new File(resourceFilename);
		}
		URL url = CommonFileUtilities.class.getClassLoader().getResource(resourceFilename);
		File file = null;
		try {
			file = new File(url.toURI());
		} catch (URISyntaxException e) {
			logger.error("invalid file",  e);
		}
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
