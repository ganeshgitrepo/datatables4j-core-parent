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
package com.github.datatables4j.core.theme;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.datatables4j.core.api.constants.ResourceType;
import com.github.datatables4j.core.api.exception.BadConfigurationException;
import com.github.datatables4j.core.api.model.Configuration;
import com.github.datatables4j.core.api.model.CssResource;
import com.github.datatables4j.core.api.model.HtmlTable;
import com.github.datatables4j.core.api.model.JsResource;
import com.github.datatables4j.core.api.model.WebResources;
import com.github.datatables4j.core.util.NameConstants;
import com.github.datatables4j.core.util.ResourceHelper;

/**
 * Feature loader class (e.g. : inputfiltering, selectFiltering, ...).
 * 
 * @author Thibault Duchateau
 */
public class ThemeLoader {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(ThemeLoader.class);

	/**
	 * Load every feature activated for the given table.
	 * 
	 * @param mainJsFile
	 *            The main Javascript file which will be generated and may be
	 *            updated accordingly to modules.
	 * @param table
	 *            The table containing module informations.
	 * @param mainConf
	 *            Main DataTables configuration which may be updated accordingly
	 *            to modules.
	 * @param webResources
	 *            The wrapper POJO containing all web resources to generate.
	 * @throws BadConfigurationException
	 */
	public static void loadTheme(JsResource mainJsFile, HtmlTable table,
			Map<String, Object> mainConf, WebResources webResources)
			throws BadConfigurationException {

		JsResource themeSourceJsFile = null;
		CssResource themeSourceCssFile = null;

		logger.info("Check for features ...");

		if (table.getTheme() != null) {

			logger.debug("Loading {} v{} ...", table.getTheme().getName(), table.getTheme()
					.getVersion());

			// Feature initialization
			table.getTheme().setup(table);

			// Feature javascript
			if (table.getTheme().getJsResources() != null && !table.getTheme().getJsResources().isEmpty()) {

				themeSourceJsFile = new JsResource(ResourceType.FEATURE,
						NameConstants.DT_PLUGIN_JS + table.getTheme().getName().toLowerCase() + "-"
								+ table.getRandomId() + ".js");

				// Feature source loading (javascript)
				for (JsResource jsResource : table.getTheme().getJsResources()) {
					String location = jsResource.getName();
					themeSourceJsFile.setContent(ResourceHelper
							.getFileContentFromClasspath(location));
				}

				webResources.getJavascripts().put(themeSourceJsFile.getName(),
						themeSourceJsFile);
			}

			// Feature configuration loading
			if (StringUtils.isNotBlank(table.getTheme().getBeforeAllScript())) {
				mainJsFile.appendToBeforeAll(table.getTheme().getBeforeAllScript());
			}
			if (StringUtils.isNotBlank(table.getTheme().getAfterStartDocumentReady())) {
				mainJsFile.appendToAfterStartDocumentReady(table.getTheme()
						.getAfterStartDocumentReady());
			}
			if (StringUtils.isNotBlank(table.getTheme().getBeforeEndDocumentReady())) {
				mainJsFile.appendToBeforeEndDocumentReady(table.getTheme()
						.getBeforeEndDocumentReady());
			}
			if (StringUtils.isNotBlank(table.getTheme().getAfterAllScript())) {
				mainJsFile.appendToAfterAll(table.getTheme().getAfterAllScript());
			}

			if(table.getTheme().getConfs() != null){

				for (Configuration conf : table.getTheme().getConfs()) {
					System.out.println("conf name = " + conf.getName());
					System.out.println("conf value = "  + conf.getValue());
					// The module configuration already exists in the main configuration
					if (mainConf.containsKey(conf.getName())) {
						String value = null;
						switch (conf.getMode()) {
						case OVERRIDE:
							mainConf.put(conf.getName(), conf.getValue());
							break;

						case APPEND:
							value = (String) mainConf.get(conf.getName());
							value = value + conf.getValue();
							mainConf.put(conf.getName(), value);
							break;

						case PREPEND:
							value = (String) mainConf.get(conf.getName());
							value = conf.getValue() + value;
							mainConf.put(conf.getName(), value);
							break;
						default:
							break;
						}
					} 
					// No existing configuration in the main configuration, just add it
					else {
						mainConf.put(conf.getName(), conf.getValue());
					}
				}
			}
			
			// Feature javascript
			if (!table.getTheme().getCssResources().isEmpty()) {

				themeSourceCssFile = new CssResource(table.getTheme().getName().toLowerCase() + "-"
								+ table.getRandomId() + ".css");

				// Feature source loading (javascript)
				for (CssResource cssResource : table.getTheme().getCssResources()) {
					String location = cssResource.getName();
					themeSourceCssFile.setContent(ResourceHelper
							.getFileContentFromClasspath(location));
				}

				webResources.getStylesheets().put(themeSourceCssFile.getName(),
						themeSourceCssFile);
			}

			logger.debug("Theme loaded");
		} else {
			logger.info("No theme to load");
		}
	}
}