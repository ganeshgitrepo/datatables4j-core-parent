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

import com.github.datatables4j.core.api.aggregator.AggregateMode;
import com.github.datatables4j.core.api.constants.ConfConstants;

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
	 * Static inner class used for initialization-on-demand holder strategy.
	 */
	private static class SingletonHolder {
		// Unique instance non initialised yet
		private final static PropertiesLoader instance = new PropertiesLoader();
	}

	/**
	 * Unique entry point for the class.
	 * 
	 * @return the unique instance of the class.
	 */
	public static PropertiesLoader getInstance() {
		return SingletonHolder.instance;
	}
	
	/**
	 * Private constructor which loads properties file
	 */
	private PropertiesLoader(){
		
		// Initialize properties
		propertiesResource = new Properties();

		// Get current classloader
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

		// Get default file as stream
		InputStream propertiesStream = classLoader.getResourceAsStream(DT_DEFAULT_PROPERTIES);

		try {
			// Load all default properties
			propertiesResource.load(propertiesStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Next, try to get the custom properties file
		propertiesStream = classLoader.getResourceAsStream(DT_CUSTOM_PROPERTIES);

		if (propertiesStream != null) {

			Properties customProperties = new Properties();
			try {
				customProperties.load(propertiesStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// If custom properties have been loaded, we merge the properties
			// Custom properties will override default ones
			propertiesResource.putAll(customProperties);
		} else {
			logger.info("No custom file datatables4j.properties has been found. Using default one.");
		}
	}
	

	/**
	 * Update a property. Called if a prop tag is present in a table tag.
	 * 
	 * @param key
	 *            The property's key to update.
	 * @param value
	 *            The property's value to use.
	 */
	public void setProperty(String key, String value) {
		this.propertiesResource.put(key, value);
	}

	/**
	 * Get the value associated with the key in the properties file.
	 * 
	 * @param key
	 *            String The key.
	 * @return String The value associated with the key.
	 */
	public String getProperty(String key) {
		return propertiesResource.getProperty(key);
	}

	/**
	 * Get the DataSource provider class name.
	 * 
	 * @return The DataSource provider class name.
	 */
	public String getDatasourceClassName() {
		return getProperty(ConfConstants.DT_DATASOURCE_CLASS);
	}

	public String getCompressorClassName() {
		return getProperty(ConfConstants.DT_COMPRESSOR_CLASS);
	}

	public Boolean isCompressorEnable() {
		return Boolean.parseBoolean(getProperty(ConfConstants.DT_COMPRESSOR_ENABLE));
	}

	public Boolean isAggregatorEnable() {
		return Boolean.parseBoolean(getProperty(ConfConstants.DT_AGGREGATOR_ENABLE));
	}

	public AggregateMode getAggregatorMode() {
		return AggregateMode.valueOf(getProperty(ConfConstants.DT_AGGREGATOR_MODE));
	}
}