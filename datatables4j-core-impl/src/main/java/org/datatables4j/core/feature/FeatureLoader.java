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
package org.datatables4j.core.feature;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.datatables4j.core.api.constants.ResourceType;
import org.datatables4j.core.api.exception.BadConfigurationException;
import org.datatables4j.core.api.model.Feature;
import org.datatables4j.core.api.model.HtmlTable;
import org.datatables4j.core.api.model.JsResource;
import org.datatables4j.core.api.model.WebResources;
import org.datatables4j.core.util.NameConstants;
import org.datatables4j.core.util.ResourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO
 * 
 * @author Thibault Duchateau
 */
public class FeatureLoader {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(FeatureLoader.class);

	/**
	 * <p>
	 * Load every plugin activated by the user in the table tag.
	 * </p>
	 * <p>
	 * A plugin may be composed of several elements :
	 * <ul>
	 * <li></li>
	 * </ul>
	 * </p>
	 * 
	 * @param jsFile
	 *            The Javascript which will be generated and may be updated
	 *            accordingly to modules.
	 * @param table
	 *            The table containing module informations.
	 * @param mainConf
	 *            Main DataTables configuration which may be updated accordingly
	 *            to modules.
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

				// Module initialization
				feature.setup(table);

				// Module javascript
				if (!feature.getJsResources().isEmpty()) {

					pluginsSourceJsFile = new JsResource(ResourceType.FEATURE,
							NameConstants.DT_PLUGIN_JS + feature.getFeatureName().toLowerCase() + "-" + table.getRandomId()
									+ ".js");

					// Module source loading (javascript)
					for (JsResource jsResource : feature.getJsResources()) {
						String location = "datatables/features/"
								+ jsResource.getName();
						pluginsSourceJsFile.setContent(ResourceUtils
								.getFileContentFromClasspath(location));
					}
					
					webResources.getJavascripts().put(pluginsSourceJsFile.getName(),
							pluginsSourceJsFile);
				}

				// Module configuration loading
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