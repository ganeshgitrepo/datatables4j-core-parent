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
package com.github.datatables4j.core.feature;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.datatables4j.core.api.constants.ResourceType;
import com.github.datatables4j.core.api.exception.BadConfigurationException;
import com.github.datatables4j.core.api.model.Feature;
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
public class FeatureLoader {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(FeatureLoader.class);

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
	public static void loadFeatures(JsResource mainJsFile, HtmlTable table,
			Map<String, Object> mainConf, WebResources webResources)
			throws BadConfigurationException {

		JsResource pluginsSourceJsFile = null;

		logger.info("Check for features ...");

		if (!table.getFeatures().isEmpty()) {

			for (Feature feature : table.getFeatures()) {

				logger.debug("Loading {} v{} ...", feature.getFeatureName(),
						feature.getFeatureVersion());

				// Feature initialization
				feature.setup(table);

				// Feature javascript
				if (!feature.getJsResources().isEmpty()) {

					pluginsSourceJsFile = new JsResource(ResourceType.FEATURE,
							NameConstants.DT_PLUGIN_JS + feature.getFeatureName().toLowerCase() + "-" + table.getRandomId()
									+ ".js");

					// Feature source loading (javascript)
					for (JsResource jsResource : feature.getJsResources()) {
						String location = "datatables/features/"
								+ jsResource.getName();
						pluginsSourceJsFile.setContent(ResourceHelper
								.getFileContentFromClasspath(location));
					}
					
					webResources.getJavascripts().put(pluginsSourceJsFile.getName(),
							pluginsSourceJsFile);
				}

				// Feature configuration loading
				if (StringUtils.isNotBlank(feature.getBeforeAllScript())) {
					mainJsFile.appendToBeforeAll(feature.getBeforeAllScript());
				}
				if (StringUtils.isNotBlank(feature.getAfterStartDocumentReady())) {
					mainJsFile.appendToAfterStartDocumentReady(feature
							.getAfterStartDocumentReady());
				}
				if (StringUtils.isNotBlank(feature.getBeforeEndDocumentReady())) {
					mainJsFile.appendToBeforeEndDocumentReady(feature
							.getBeforeEndDocumentReady());
				}
				if (StringUtils.isNotBlank(feature.getAfterAllScript())) {
					mainJsFile.appendToAfterAll(feature.getAfterAllScript());
				}
			}

			logger.debug("All features loaded");
		}
		else{
			logger.info("No feature to load");
		}
	}
}