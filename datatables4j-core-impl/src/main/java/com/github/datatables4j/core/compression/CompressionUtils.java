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

import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.datatables4j.core.api.exception.BadConfigurationException;
import com.github.datatables4j.core.api.model.CssResource;
import com.github.datatables4j.core.api.model.HtmlTable;
import com.github.datatables4j.core.api.model.JsResource;
import com.github.datatables4j.core.api.model.WebResources;
import com.github.datatables4j.core.properties.PropertiesLoader;
import com.github.datatables4j.core.util.ReflectUtils;

public class CompressionUtils {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(CompressionUtils.class);
		
	/**
	 * TODO
	 * @param webResources
	 * @param table
	 * @throws BadConfigurationException
	 */
	public static void processCompression(WebResources webResources, HtmlTable table) throws BadConfigurationException{

		PropertiesLoader properties = PropertiesLoader.getInstance();
		
		// If DataTables4j has been manually installed, some jar might be missing
		// So check first if the CompressorClass exist in the classpath
		if(ReflectUtils.canBeUsed(properties.getCompressorClassName())){
			
			// Get the compressor helper instance
			ResourceCompressorHelper compressorHelper = ResourceCompressorHelper.getInstance();
			
			// Compress all Javascript resources
			for (Entry<String, JsResource> entry : webResources.getJavascripts().entrySet()) {
				entry.getValue().setContent(
						compressorHelper.getCompressedJavascript(entry.getValue().getContent()));
			}
			
			// Compress all Stylesheet resources
			for (Entry<String, CssResource> entry : webResources.getStylesheets().entrySet()) {
				entry.getValue().setContent(
						compressorHelper.getCompressedCss(entry.getValue().getContent()));
			}			
		}
		else{
			logger.warn("The compressor class {} hasn't been found in the classpath. Compression is disabled.", properties.getCompressorClassName());
		}
	}
}
