package org.datatables4j.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.datatables4j.aggregator.AggregateMode;
import org.datatables4j.constants.ConfConstants;

public class HtmlTableProperties {

	/**
	 * DataTables properties file.
	 */
	private Properties propertiesResource = null;

	public final static String DT_DEFAULT_PROPERTIES = "/datatables4j-default.properties";
	public final static String DT_PROPERTIES = "/datatables4j.properties";

	public HtmlTableProperties() {

		// Initialize properties
		propertiesResource = new Properties();

		// Get current classloader
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream properties = classLoader.getResourceAsStream(DT_DEFAULT_PROPERTIES);
		try {
			propertiesResource.load(properties);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// First, test if custom properties file exists
		properties = classLoader.getResourceAsStream(DT_PROPERTIES);

		// try {
		// propertiesResource.load(properties);

		if(properties != null){
			
		
		Properties customProperties = new Properties();
			try {
				customProperties.load(properties);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			propertiesResource.putAll(customProperties);
		}
		// } catch (Exception e) {
		// logger.warn("No datatables4j properties file was found");
		// logger.warn("Loading default configuration");

		// Get default properties file in not added in the project
		// properties = classLoader.getResourceAsStream(DT_DEFAULT_PROPERTIES);

		// try {
		// propertiesResource.load(properties);
		// } catch (IOException e1) {
		// logger.error("Unable to load datatables4j-default.properties");
		// throw new BadConfigurationException(e);
		// }
		// }

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