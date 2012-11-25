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
package com.github.datatables4j.core.api.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.github.datatables4j.core.api.aggregator.AggregatorMode;
import com.github.datatables4j.core.api.compressor.CompressorMode;
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

	public void initProperties(Properties properties) {
		this.propertiesResource = properties;
	}

	/**
	 * Update a property. Called if a prop tag is present in a table tag. Only
	 * the current table is affected.
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
	public Boolean isValidProperty(String property) {

		return property.equals(ConfConstants.DT_COMPRESSOR_CLASS)
				|| property.equals(ConfConstants.DT_COMPRESSOR_ENABLE)
				|| property.equals(ConfConstants.DT_COMPRESSOR_MODE)
				|| property.equals(ConfConstants.DT_COMPRESSOR_MUNGE)
				|| property.equals(ConfConstants.DT_COMPRESSOR_PRESERVE_SEMI)
				|| property.equals(ConfConstants.DT_COMPRESSOR_DISABLE_OPTI)
				|| property.equals(ConfConstants.DT_AGGREGATOR_ENABLE)
				|| property.equals(ConfConstants.DT_AGGREGATOR_MODE)
				|| property.equals(ConfConstants.DT_DATASOURCE_CLASS)
				|| property.equals(ConfConstants.DT_EXPORT_TYPES);
	}

	/**
	 * @return The data source provider class name.
	 */
	public String getDatasourceClassName() {
		return getProperty(ConfConstants.DT_DATASOURCE_CLASS);
	}

	/**
	 * @return the compressor class name.
	 */
	public String getCompressorClassName() {
		return getProperty(ConfConstants.DT_COMPRESSOR_CLASS);
	}

	/**
	 * @return true if the compression is enabled (the value must be "true"),
	 *         false otherwise.
	 */
	public Boolean isCompressorEnable() {
		return Boolean.parseBoolean(getProperty(ConfConstants.DT_COMPRESSOR_ENABLE));
	}

	/**
	 * @return the compressor mode.
	 */
	public CompressorMode getCompressorMode() {
		return CompressorMode.valueOf(getProperty(ConfConstants.DT_COMPRESSOR_MODE));
	}

	/**
	 * @return the compressor munge option.
	 */
	public Boolean getCompressorMunge() {
		return Boolean.parseBoolean(getProperty(ConfConstants.DT_COMPRESSOR_MUNGE));
	}

	/**
	 * @return true to preserve semi-colons, even if it's not necessary (if the
	 *         next character is a right-curly), false otherwise.
	 */
	public Boolean getCompressorPreserveSemi() {
		return Boolean.parseBoolean(getProperty(ConfConstants.DT_COMPRESSOR_PRESERVE_SEMI));
	}

	/**
	 * <p>
	 * According to the YUI JavaScriptCompressor class, micro optimizations
	 * concern :
	 * 
	 * <ul>
	 * <li>object member access : Transforms obj["foo"] into obj.foo whenever
	 * possible, saving 3 bytes</li>
	 * <li>object litteral member declaration : Transforms 'foo': ... into foo:
	 * ... whenever possible, saving 2 bytes</li>
	 * </ul>
	 * 
	 * @return true to disable micro optimizations, false otherwise.
	 */
	public Boolean getCompressorDisableOpti() {
		return Boolean.parseBoolean(getProperty(ConfConstants.DT_COMPRESSOR_DISABLE_OPTI));
	}

	/**
	 * @return true if the aggregation is enabled (the value must be "true"),
	 *         false otherwise.
	 */
	public Boolean isAggregatorEnable() {
		return Boolean.parseBoolean(getProperty(ConfConstants.DT_AGGREGATOR_ENABLE));
	}

	/**
	 * @return the aggregator mode.
	 */
	public AggregatorMode getAggregatorMode() {
		return AggregatorMode.valueOf(getProperty(ConfConstants.DT_AGGREGATOR_MODE));
	}

	/**
	 * Extract the authorized export types.
	 * 
	 * @return a list of authorized ExportType.
	 */
	public List<ExportType> getExportTypes() {
		List<ExportType> exportTypes = new ArrayList<ExportType>();

		List<String> exportTypesString = Arrays.asList(getProperty(ConfConstants.DT_EXPORT_TYPES)
				.split(","));

		for (String type : exportTypesString) {
			exportTypes.add(ExportType.valueOf(type));
		}

		return exportTypes;
	}
}