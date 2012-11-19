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
package com.github.datatables4j.core.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.datatables4j.core.api.exception.BadConfigurationException;
import com.github.datatables4j.core.api.model.HtmlTable;

/**
 * Aggregates the default DataTables4j properties file with a potential custom one.
 * The custom properties will override default ones.
 *
 * @author Thibault Duchateau
 */
public class PropertiesLoader {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(PropertiesLoader.class);
	
	/**
	 * DataTables properties file.
	 */
	private Properties propertiesResource;

	public final static String DT_DEFAULT_PROPERTIES = "config/datatables4j-default.properties";
	public final static String DT_CUSTOM_PROPERTIES = "datatables4j.properties";
	
	/**
	 * Load the properties in the table using : <li>first, the global
	 * datatables4j properties file <li>second, the project specific properties
	 * file, if it exists
	 * 
	 * @param table
	 *            The table where to load properties.
	 */
	public static void load(HtmlTable table) throws BadConfigurationException {
		
		// Initialize properties
		Properties propertiesResource = new Properties();

		// Get current classloader
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

		// Get default file as stream
		InputStream propertiesStream = classLoader.getResourceAsStream(DT_DEFAULT_PROPERTIES);

		try {
			// Load all default properties
			propertiesResource.load(propertiesStream);
		} catch (IOException e) {
			throw new BadConfigurationException("Unable to load the default configuration file", e);
		}

		// Next, try to get the custom properties file
		propertiesStream = classLoader.getResourceAsStream(DT_CUSTOM_PROPERTIES);

		if (propertiesStream != null) {

			Properties customProperties = new Properties();
			try {
				// Load project-specific properties
				customProperties.load(propertiesStream);
			} catch (IOException e) {
				throw new BadConfigurationException("Unable to load the project-specific configuration file", e);
			}

			// If custom properties have been loaded, we merge the properties
			// Custom properties will override default ones
			propertiesResource.putAll(customProperties);
		} else {
			logger.info("No custom file datatables4j.properties has been found. Using default one.");
		}
		
		table.getTableProperties().initProperties(propertiesResource);
	}
}