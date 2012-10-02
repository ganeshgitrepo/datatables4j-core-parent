package org.datatables4j.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import org.apache.commons.lang.RandomStringUtils;
import org.datatables4j.exception.BadConfigurationException;

/**
 * TODO
 * @author tduchate
 *
 */
public class ResourceUtils {
	
	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
	
	/**
	 * TODO
	 * @param pathToFile
	 * @return
	 * @throws BadConfigurationException 
	 */
	public static InputStream getFileFromWebapp(String pathToFile) throws BadConfigurationException{
		File file = new File(pathToFile);
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new BadConfigurationException(e);
		}
		return inputStream;
	}
	
	/**
	 * TODO
	 * @param pathToFile
	 * @return
	 */
	public static InputStream getFileFromClasspath(String pathToFile){
		
//		logger.debug("pathToFile : {}", pathToFile);

		return Thread.currentThread().getContextClassLoader().getResourceAsStream(pathToFile);
	}
	
	/**
	 * TODO
	 * @param pathToFile
	 * @return
	 * @throws BadConfigurationException 
	 * @throws IOException
	 */
	public static String getFileContentFromClasspath(String pathToFile) throws BadConfigurationException {
		String retval = null;
		try {
			retval = toString(getFileFromClasspath(pathToFile));
		} catch (IOException e) {
			throw new BadConfigurationException(e);
		}

		return retval;
	}
	
	
	/**
	 * TODO
	 * @param pathToFile
	 * @return
	 * @throws BadConfigurationException 
	 */
	public static String getFileContentFromWebapp(String pathToFile) throws BadConfigurationException{
		String retval = null;
		try {
			retval = toString(getFileFromWebapp(pathToFile));
		} catch (IOException e) {
			throw new BadConfigurationException(e);
		}
		
		return retval;
	}
	
	
	/**
	 * TODO
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public static String toString(InputStream input) throws IOException {
		StringWriter sw = new StringWriter();

		InputStreamReader   in = new InputStreamReader  (input);
		 
		char[] buffer = new char[DEFAULT_BUFFER_SIZE];
		int count = 0;
		int n = 0;
		while (-1 != (n = in.read(buffer))) {
			sw.write(buffer, 0, n);
			count += n;
		}

		System.out.println("================= sw = " + sw.toString());
		return sw.toString();
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public static String getRamdomNumber(){
		return RandomStringUtils.randomNumeric(5);
	}
}