/*
 * DataTables4j, a JSP taglib to display table with jQuery and DataTables
 * Copyright (c) 2012, DataTables4j <datatables4j@gmail.com>
 *
 * This Program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License, version 2, as
 * published by the Free Software Foundation.
 * 
 * The Program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package com.github.datatables4j.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import org.apache.commons.lang.RandomStringUtils;

import com.github.datatables4j.core.api.exception.BadConfigurationException;

/**
 * Helper class used to extract content from different type of input.
 * 
 * @author Thibault Duchateau
 */
public class ResourceHelper {
	
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
		System.out.println(pathToFile);
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