package org.datatables4j.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.jsp.PageContext;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceUtils {

	private static Logger logger = LoggerFactory.getLogger(ResourceUtils.class);
	
	public static InputStream getFileFromWebapp(PageContext pageContext, String pathToFile){
		String filePath = pageContext.getServletContext().getRealPath(pathToFile);
		File file = new File(filePath);
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return inputStream;
	}
	
	public static InputStream getFileFromClasspath(String pathToFile){
		
		logger.debug("pathToFile : {}", pathToFile);

		return Thread.currentThread().getContextClassLoader().getResourceAsStream(pathToFile);
	}
	
	public static String getFileContentFromClasspath(String pathToFile){
		logger.debug("pathToFile : {}", pathToFile);
		String retval = null;
		try {
			retval = IOUtils.toString(getFileFromClasspath(pathToFile));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return retval;
	}
	
	public static String getFileContentFromWebapp(PageContext pageContext, String pathToFile){
		logger.debug("pathToFile : {}", pathToFile);
		String retval = null;
		try {
			retval = IOUtils.toString(getFileFromWebapp(pageContext, pathToFile));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return retval;
	}
}
