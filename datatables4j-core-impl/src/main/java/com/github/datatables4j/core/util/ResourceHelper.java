/*
 * [The "BSD licence"]
 * Copyright (c) 2012 DataTables4j
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 3. Neither the name of DataTables4j nor the names of its contributors 
 * may be used to endorse or promote products derived from this software 
 * without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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