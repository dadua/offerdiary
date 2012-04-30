package com.itech.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;

public class CommonFileUtilities {

	public static String getResourceFileAsString(String resourceFilename) throws IOException{
		InputStream in = CommonFileUtilities.class.getClassLoader().getResourceAsStream("resources\\"+resourceFilename);
		InputStreamReader is = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(is);
		String read = br.readLine();
		String asString = read;
		while(read != null) {
			read =  br.readLine();
			asString = asString + read;
		}
		System.out.println(read);
		return asString;
	}


	public static File getResourceFileAsFile(String resourceFilename) throws URISyntaxException{
		URL url = CommonFileUtilities.class.getClassLoader().getResource("resources\\"+resourceFilename);
		File file = null;
		file = new File(url.toURI());
		return file;
	}

}
