package org.datatables4j.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.datatables4j.exception.BadConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO
 * @author tduchate
 *
 */
public class ResourceUtils {

	private static Logger logger = LoggerFactory.getLogger(ResourceUtils.class);
	
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
			retval = IOUtils.toString(getFileFromClasspath(pathToFile));
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
			retval = IOUtils.toString(getFileFromWebapp(pathToFile));
		} catch (IOException e) {
			throw new BadConfigurationException(e);
		}
		
		return retval;
	}
}
