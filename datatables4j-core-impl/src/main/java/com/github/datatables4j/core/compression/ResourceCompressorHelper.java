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
import com.github.datatables4j.core.api.model.HtmlTable;
import com.github.datatables4j.core.util.ReflectHelper;

/**
 * Helper class handling compression methods for Javascript and Stylesheet resources.
 * 
 * @author Thibault Duchateau
 */
public class ResourceCompressorHelper {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(ResourceCompressorHelper.class);
	
	private String compressorClassName;
	
	/**
	 * Private constructor which retrieve the compressor class from properties.
	 */
	public ResourceCompressorHelper(HtmlTable table){
				
		this.compressorClassName = table.getTableProperties().getCompressorClassName();
		
		logger.debug("ResourceCompressor loaded. About to use {} implementation", this.compressorClassName);
	}
	
	
	/**
	 * Compress the javascript input using the compressorClassName and return
	 * it.
	 * 
	 * @param input
	 *            The stringified javascript to compress.
	 * @return The compressed stringified javascript.
	 * @throws BadConfigurationException
	 *             if the compressorClassName is not present in the classPath.
	 */
	public String getCompressedJavascript(String input) throws BadConfigurationException {
		
		Class<?> compressorClass = ReflectHelper.getClass(this.compressorClassName);
		
		logger.debug("Instancing the compressor class {}", compressorClass);
		Object obj = ReflectHelper.getNewInstance(compressorClass);

		logger.debug("Invoking method getCompressedJavascript");
		String compressedContent = (String) ReflectHelper.invokeMethod(obj, "getCompressedJavascript", new Object[]{input});
				
		return compressedContent;
	}
	
	
	/**
	 * Compress the CSS input using the compressorClassName and return
	 * it.
	 * 
	 * @param input
	 *            The stringified CSS to compress.
	 * @return The compressed stringified CSS.
	 * @throws BadConfigurationException
	 *             if the compressorClassName is not present in the classPath.
	 */
	public String getCompressedCss(String input) throws BadConfigurationException {

		Class<?> compressorClass = ReflectHelper.getClass(this.compressorClassName);
		
		Object obj = ReflectHelper.getNewInstance(compressorClass);
		
		return (String) ReflectHelper.invokeMethod(obj, "getCompressedCss", new Object[]{input});
	}
}