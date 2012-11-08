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
package com.github.datatables4j.core.compression;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.datatables4j.core.api.exception.BadConfigurationException;
import com.github.datatables4j.core.properties.PropertiesLoader;
import com.github.datatables4j.core.util.ReflectUtils;

/**
 * TODO
 * 
 * @author Thibault Duchateau
 */
public class ResourceCompressorHelper {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(ResourceCompressorHelper.class);
	
	private String compressorClassName;
	
	/**
	 * Static inner class used for initialization-on-demand holder strategy.
	 */
	private static class SingletonHolder {
		private final static ResourceCompressorHelper instance = new ResourceCompressorHelper();
	}

	/**
	 * Unique entry point for the class.
	 * 
	 * @return the unique instance of the class.
	 */
	public static ResourceCompressorHelper getInstance() {
		return SingletonHolder.instance;
	}
	
	/**
	 * Private constructor which retrieve the compressor class from properties.
	 */
	private ResourceCompressorHelper(){
		
		PropertiesLoader properties = PropertiesLoader.getInstance();
		
		this.compressorClassName = properties.getCompressorClassName();

		logger.debug("Compress using {} implementation", this.compressorClassName);
	}
	
	
	/**
	 * TODO
	 * 
	 * @param input
	 * @return
	 * @throws BadConfigurationException
	 */
	public String getCompressedJavascript(String input) throws BadConfigurationException {
		
		Class<?> compressorClass = ReflectUtils.getClass(this.compressorClassName);
		
		Object obj = ReflectUtils.getNewInstance(compressorClass);

		return (String) ReflectUtils.invokeMethod(obj, "getCompressedJavascript", new Object[]{input});
	}
	
	
	/**
	 * TODO
	 * 
	 * @param input
	 * @return
	 * @throws BadConfigurationException
	 */
	public String getCompressedCss(String input) throws BadConfigurationException {

		Class<?> compressorClass = ReflectUtils.getClass(this.compressorClassName);
		
		Object obj = ReflectUtils.getNewInstance(compressorClass);
		
		return (String) ReflectUtils.invokeMethod(obj, "getCompressedCss", new Object[]{input});
	}
}