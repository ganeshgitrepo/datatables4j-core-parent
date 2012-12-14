/*
 * [The "BSD licence"]
 * Copyright (c) 2012 DataTables4j
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 3. Neither the name of DataTables4j nor the names of its contributors 
 * may be used to endorse or promote products derived from this software 
 * without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
				|| property.equals(ConfConstants.DT_EXPORT_TYPES)
				|| property.equals(ConfConstants.DT_EXPORT_XLS_DEFAULT_CLASS)
				|| property.equals(ConfConstants.DT_EXPORT_PDF_DEFAULT_CLASS_1)
				|| property.equals(ConfConstants.DT_EXPORT_PDF_DEFAULT_CLASS_2);
	}

	/**
	 * @return The default data source provider class name.
	 */
	public String getDatasourceClassName() {
		return getProperty(ConfConstants.DT_DATASOURCE_CLASS);
	}

	/**
	 * @return the default compressor class name.
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
	
	/**
	 * @return the default xls export class name.
	 */
	public String getDefaultXlsExportClassName() {
		return getProperty(ConfConstants.DT_EXPORT_XLS_DEFAULT_CLASS);
	}
	
	/**
	 * @return the default xlsx export class name.
	 */
	public String getDefaultXlsxExportClassName() {
		return getProperty(ConfConstants.DT_EXPORT_XLSX_DEFAULT_CLASS);
	}
	
	/**
	 * @return the first default pdf export class name.
	 */
	public String getDefaultPdfExportClassName1() {
		return getProperty(ConfConstants.DT_EXPORT_PDF_DEFAULT_CLASS_1);
	}
	
	/**
	 * @return the second default pdf export class name.
	 */
	public String getDefaultPdfExportClassName2() {
		return getProperty(ConfConstants.DT_EXPORT_PDF_DEFAULT_CLASS_2);
	}
}