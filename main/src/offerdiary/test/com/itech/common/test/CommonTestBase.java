package com.itech.common.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import junit.framework.TestCase;

public abstract class CommonTestBase extends TestCase {

	@Override
	protected abstract void setUp() throws Exception;

	@Override
	protected abstract void tearDown() throws Exception;

	/* Place holder so that if there are no test cases
	 * in an inherited JUNIT class, this fills the gap
	 * and build does not fail.
	 */
	public void testSample(){

	}

	public byte[] getFileDataInBytes(String fileName) throws IOException{
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream((new File(getTestFileLocation(fileName)))));
		byte [] buffer = new byte [200*1024];

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int numBytesRead = -1;
		while((numBytesRead = bis.read(buffer)) > 0){
			bos.write(buffer, 0, numBytesRead);
		}

		bis.close();

		byte [] bytesRead = bos.toByteArray();

		bos.close();
		bos = null;

		return bytesRead;
	}
	public String getFileDataInString(String fileName) throws IOException{
		return getFileDataInString(fileName, Charset.forName("utf-8"));
	}
	public String getFileDataInString(String fileName, Charset charset) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream((new File(getTestFileLocation(fileName)))), charset));

		StringBuilder sb = new StringBuilder();
		String lineSeparator = System.getProperty ( "line.separator" );
		boolean firstLine = true;
		String line = null;
		while((line = br.readLine()) != null){
			if(!firstLine){
				sb.append(lineSeparator);
			}
			sb.append(line);
			firstLine = false;
		}
		br.close();

		return sb.toString();
	}

	protected String getTestFileName(String fileName) {
		String extHome = System.getProperty("user.dir");
		String testClassName = getClass().getName();
		String testPackageName = testClassName.replaceAll("\\."+getClass().getSimpleName(), "");
		String path = "file:/" + extHome + "/test/" + testPackageName.replaceAll("\\.", "/") + "/"+ "testFiles" + "/" + fileName;
		return path;
	}

	protected String getTestFileLocation(String fileName) {
		String extHome = System.getProperty("user.dir");
		String testClassName = getClass().getName();
		String testPackageName = testClassName.replaceAll("\\."+getClass().getSimpleName(), "");
		String path = extHome + "/test/" + testPackageName.replaceAll("\\.", "/") + "/"+ "testFiles" + "/" + fileName;
		return path;
	}

	protected static void assertEquals(int expected, Integer actual){
		assertEquals(expected, actual.intValue());
	}
	protected static void assertEquals(Integer expected,  int actual){
		assertEquals(expected.intValue(), actual);
	}
	protected static void assertEquals(long expected, Integer actual){
		assertEquals(expected, actual.intValue());
	}
	protected static void assertEquals(int expected, Long actual){
		assertEquals(expected, actual.intValue());
	}
	protected static void assertEquals(String message, int expected, Integer actual){
		assertEquals(message, expected, actual.intValue());
	}
	protected static void assertEquals(String message, long expected, Integer actual){
		assertEquals(message, expected, actual.intValue());
	}
	protected static  void assertEquals(String message, int expected, Long actual){
		assertEquals(message, expected, actual.intValue());
	}
	protected static  void assertEquals(String message, Integer expected,  int actual){
		assertEquals(message, expected.intValue(), actual);
	}
	protected static void assertEquals(String message, long expected,  Long actual){
		assertEquals(message, expected, actual.longValue());
	}
}
