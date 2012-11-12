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

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.datatables4j.core.api.constants.ResourceType;
import com.github.datatables4j.core.api.exception.BadConfigurationException;
import com.github.datatables4j.core.api.model.CssResource;
import com.github.datatables4j.core.api.model.HtmlTable;
import com.github.datatables4j.core.api.model.JsResource;
import com.github.datatables4j.core.api.model.WebResources;
import com.github.datatables4j.core.util.ReflectHelper;

/**
 * TODO
 *
 * @author Thibault Duchateau
 */
public class CompressionUtils {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(CompressionUtils.class);
		
	/**
	 * TODO
	 * 
	 * @param webResources
	 *            All web resources (JS and CSS) wrapped into one POJO.
	 * @param table
	 *            The table to generate.
	 * @throws BadConfigurationException
	 */
	public static void processCompression(WebResources webResources, HtmlTable table) throws BadConfigurationException{
		
		logger.debug("Processing compression, using class {}", table.getTableProperties().getCompressorClassName());
		
		Map<String, JsResource> newJavascripts = new TreeMap<String, JsResource>();
		Map<String, CssResource> newStylesheets = new TreeMap<String, CssResource>();
		
		// If DataTables4j has been manually installed, some jar might be missing
		// So check first if the CompressorClass exist in the classpath
		if(ReflectHelper.canBeUsed(table.getTableProperties().getCompressorClassName())){
			
			// Get the compressor helper instance
			ResourceCompressorHelper compressorHelper = new ResourceCompressorHelper(table);

			// Compress all Javascript resources
			for (Entry<String, JsResource> oldEntry : webResources.getJavascripts().entrySet()) {
				
				// Copy the entry
				JsResource minimifiedResource = oldEntry.getValue();
				
				// Update content using the compressor implementation
				minimifiedResource.setContent(
						compressorHelper.getCompressedJavascript(oldEntry.getValue().getContent()));
				
				// Update name
				minimifiedResource.setName(oldEntry.getValue().getName().replace(".js", ".min.js"));
				
				// Update type
				minimifiedResource.setType(ResourceType.MINIMIFIED);
				
				// Add the new minified resource
				newJavascripts.put(minimifiedResource.getName(), minimifiedResource);
			}
			
			// Use the new map of compressed javascript file instead of the old one
			webResources.setJavascripts(newJavascripts);
			
			// Compress all Stylesheet resources
			for (Entry<String, CssResource> entry : webResources.getStylesheets().entrySet()) {
				
				// Copy the entry
				CssResource minimifiedResource = entry.getValue();
				
				// Update content using the compressor implementation
				minimifiedResource.setContent(
						compressorHelper.getCompressedCss(entry.getValue().getContent()));
				
				// Update name
				minimifiedResource.setName(entry.getValue().getName().replace(".css", ".min.css"));
				
				newStylesheets.put(minimifiedResource.getName(), minimifiedResource);
			}			

			// Use the new map of compressed stylesheets file instead of the old one
			webResources.setStylesheets(newStylesheets);
						
			logger.debug("Compression completed");
		}
		else{
			logger.warn("The compressor class {} hasn't been found in the classpath. Compression is disabled.", table.getTableProperties().getCompressorClassName());
		}
	}
}