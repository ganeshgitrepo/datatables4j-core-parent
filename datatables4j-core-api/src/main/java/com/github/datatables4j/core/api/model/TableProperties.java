package com.github.datatables4j.core.api.model;

import java.util.Properties;

import com.github.datatables4j.core.api.aggregator.AggregateMode;
import com.github.datatables4j.core.api.constants.ConfConstants;

/**
 * POJO that contains all the table-specific properties.
 *
 * @author Thibault Duchateau
 */
public class TableProperties {

	/**
	 * DataTables properties file.
	 */
	private Properties propertiesResource;
	
	public void initProperties(Properties properties){
		this.propertiesResource = properties;
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
	 * Check if the parameter exists as a property being part of the
	 * configuration properties file.
	 * 
	 * @param property
	 *            The property to check.
	 * @return true if it exists, false otherwise.
	 */
	public Boolean isValidProperty(String property){
	
		return property.equals(ConfConstants.DT_COMPRESSOR_CLASS)
				|| property.equals(ConfConstants.DT_COMPRESSOR_ENABLE)
				|| property.equals(ConfConstants.DT_AGGREGATOR_ENABLE)
				|| property.equals(ConfConstants.DT_AGGREGATOR_MODE)
				|| property.equals(ConfConstants.DT_DATASOURCE_CLASS);
	}
	
	/**
	 * Get the DataSource provider class name.
	 * 
	 * @return The DataSource provider class name.
	 */
	public String getDatasourceClassName() {
		return getProperty(ConfConstants.DT_DATASOURCE_CLASS);
	}

	/**
	 * TODO
	 * @return
	 */
	public String getCompressorClassName() {
		return getProperty(ConfConstants.DT_COMPRESSOR_CLASS);
	}

	/**
	 * TODO
	 * @return
	 */
	public Boolean isCompressorEnable() {
		return Boolean.parseBoolean(getProperty(ConfConstants.DT_COMPRESSOR_ENABLE));
	}

	/**
	 * TODO
	 * @return
	 */
	public Boolean isAggregatorEnable() {
		return Boolean.parseBoolean(getProperty(ConfConstants.DT_AGGREGATOR_ENABLE));
	}

	/**
	 * TODO
	 * @return
	 */
	public AggregateMode getAggregatorMode() {
		return AggregateMode.valueOf(getProperty(ConfConstants.DT_AGGREGATOR_MODE));
	}
}
