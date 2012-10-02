package org.datatables4j.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.datatables4j.aggregator.AggregateMode;
import org.datatables4j.constants.ConfConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlTableProperties {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(HtmlTableProperties.class);
		
	/**
	 * DataTables properties file.
	 */
	private Properties propertiesResource;

	public final static String DT_DEFAULT_PROPERTIES = "datatables4j-default.properties";
	public final static String DT_PROPERTIES = "datatables4j.properties";

	public HtmlTableProperties() {

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
		propertiesStream = classLoader.getResourceAsStream(DT_PROPERTIES);

		if (propertiesStream != null) {

			Properties customProperties = new Properties();
			try {
				customProperties.load(propertiesStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// If custom properties have been loaded, we merge the properties
			// Custom properties will override existing ones
			propertiesResource.putAll(customProperties);
		}
		else{
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