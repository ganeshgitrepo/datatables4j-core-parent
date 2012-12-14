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
package com.github.datatables4j.core.feature;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.datatables4j.core.api.constants.ResourceType;
import com.github.datatables4j.core.api.exception.BadConfigurationException;
import com.github.datatables4j.core.api.model.Feature;
import com.github.datatables4j.core.api.model.HtmlTable;
import com.github.datatables4j.core.api.model.JsResource;
import com.github.datatables4j.core.api.model.WebResources;
import com.github.datatables4j.core.generator.ColumnFilteringGenerator;
import com.github.datatables4j.core.util.JsonIndentingWriter;
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

	// Custom writer used to pretty JSON
	private static Writer writer;

	private static final String FEATURE_LOCATION = "datatables/features/";

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
	 * @throws IOException 
	 */
	public static void loadFeatures(JsResource mainJsFile, HtmlTable table,
			Map<String, Object> mainConf, WebResources webResources)
			throws BadConfigurationException, IOException {

		JsResource pluginsSourceJsFile = null;

		logger.info("Check for features ...");

		// Column filtering feature
		if(table.hasOneFilterableColumn()){
			logger.debug("Column filtering enabled");
			
			writer = new JsonIndentingWriter();
			mainJsFile.appendToDataTablesExtra("columnFilter");
			pluginsSourceJsFile = new JsResource(ResourceType.FEATURE,
					NameConstants.DT_PLUGIN_JS + "filteringAddOn" + "-" + table.getRandomId()
							+ ".js");

			Map<String, Object> conf = new ColumnFilteringGenerator().generateConfig(table);
			System.out.println("conf = " + conf.toString());
			// Allways pretty prints the JSON
			JSONValue.writeJSONString(conf, writer);
			
			// mainJsFile.appendToDataTablesConf(JSONValue.toJSONString(mainConf));
			mainJsFile.appendToDataTablesExtraConf(writer.toString());
			System.out.println("dans mainJsFile, conf = " + writer.toString());

			// Feature source loading (javascript)
			pluginsSourceJsFile.setContent(ResourceHelper
						.getFileContentFromClasspath(FEATURE_LOCATION + "filtering/filteringAddOn.js"));
			
			webResources.getJavascripts().put(pluginsSourceJsFile.getName(),
					pluginsSourceJsFile);
		}
		
		// Others features
		if (!table.getFeatures().isEmpty()) {

			for (Feature feature : table.getFeatures()) {

				logger.debug("Loading {} v{} ...", feature.getName(),
						feature.getVersion());

				// Feature initialization
				feature.setup(table);

				// Feature javascript
				if (feature.getJsResources() != null && !feature.getJsResources().isEmpty()) {

					pluginsSourceJsFile = new JsResource(ResourceType.FEATURE,
							NameConstants.DT_PLUGIN_JS + feature.getName().toLowerCase() + "-" + table.getRandomId()
									+ ".js");

					// Feature source loading (javascript)
					for (JsResource jsResource : feature.getJsResources()) {
						String location = FEATURE_LOCATION + jsResource.getName();
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