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
package com.github.datatables4j.core.generator;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.datatables4j.core.api.constants.DTConstants;
import com.github.datatables4j.core.api.constants.ResourceType;
import com.github.datatables4j.core.api.exception.BadConfigurationException;
import com.github.datatables4j.core.api.exception.CompressionException;
import com.github.datatables4j.core.api.exception.DataNotFoundException;
import com.github.datatables4j.core.api.model.ExtraConf;
import com.github.datatables4j.core.api.model.ExtraFile;
import com.github.datatables4j.core.api.model.HtmlTable;
import com.github.datatables4j.core.api.model.JsResource;
import com.github.datatables4j.core.api.model.WebResources;
import com.github.datatables4j.core.datasource.DataProvider;
import com.github.datatables4j.core.feature.FeatureLoader;
import com.github.datatables4j.core.plugin.InternalPluginLoader;
import com.github.datatables4j.core.util.JsonUtils;
import com.github.datatables4j.core.util.NameConstants;
import com.github.datatables4j.core.util.RequestHelper;
import com.github.datatables4j.core.util.ResourceUtils;

/**
 * Class used for Javascript generation (as text).
 * 
 * @author Thibault Duchateau
 */
public class WebResourceGenerator {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(WebResourceGenerator.class);

	/**
	 * The DataTables configuration generator.
	 */
	private static ConfigGenerator configGenerator;

	/**
	 * TODO
	 * 
	 * @param pageContext
	 *            Context of the servlet.
	 * @param table
	 *            Table from which the configuration is extracted.
	 * @return A string corresponding to the Javascript code to return to the
	 *         JSP.
	 * @throws DataNotFoundException
	 *             if the web service URL is wrong (only for AJAX datasource)
	 * @throws IOException 
	 * @throws CompressionException 
	 * @throws BadConfigurationException 
	 */
	public WebResources generateWebResources(PageContext pageContext, HtmlTable table)
			throws DataNotFoundException, CompressionException, IOException, BadConfigurationException {
		
		// Bean which stores all needed web resources (js, css)
		WebResources webResources = new WebResources();
		
		// TODO transformer configGenerator en singleton
		configGenerator = new ConfigGenerator();
		
		// Init the "configuration" map with the table informations
		// The configuration may be updated depending on the user's choices
		Map<String, Object> mainConf = configGenerator.generateConfig(table);
		
		/**
		 * Build the main file.
		 */
		// We need to append a randomUUID in case of multiple tables exists in the same JSP
		JsResource mainJsFile = new JsResource(ResourceType.MAIN, NameConstants.DT_MAIN_JS + table.getRandomId() + ".js", table.getId());

		// Extra files management
		if (!table.getExtraFiles().isEmpty()) {
			extraFileManagement(mainJsFile, table);
		}

		// Internal module management
		InternalPluginLoader.loadPlugins(mainJsFile, table, mainConf, webResources);
		FeatureLoader.loadFeatures(mainJsFile, table, mainConf, webResources);
		
		// TODO extraConf en standby car souci de parsing si function dans l'ojbet JSON
		extraConfManagement(mainJsFile, mainConf, table);
		
		// AJAX datasource : data must be added in the configuration file
		if (table.getDatasourceUrl() != null) {
			String baseUrl = RequestHelper.getBaseUrl(pageContext);
			String webServiceUrl = baseUrl + table.getDatasourceUrl();
			
			DataProvider dataProvider = new DataProvider();
			
			mainConf.put(DTConstants.DT_DS_DATA, dataProvider.getData(table, webServiceUrl));
			
			mainJsFile.appendToDataTablesConf(JsonUtils.convertObjectToJsonString(mainConf));
		}
		// DOM datasource
		else {
			mainJsFile.appendToDataTablesConf(JsonUtils.convertObjectToJsonString(mainConf));
		}
				
		webResources.getJavascripts().put(mainJsFile.getName(), mainJsFile);
				
		return webResources;
	}
	
	
	/**
	 * If extraFile tag have been added, its content must be extracted and merge
	 * to the main js file.
	 * 
	 * @param mainFile
	 *            The resource to update with extraFiles.
	 * @param table
	 *            The HTML tale.
	 * @throws BadConfigurationException
	 *             if
	 * 
	 */
	private void extraFileManagement(JsResource mainFile, HtmlTable table) throws IOException,
			BadConfigurationException {

		logger.info("Extra files found");

		for (ExtraFile file : table.getExtraFiles()) {
			
			switch (file.getInsert()) {
			case BEFOREALL:
				mainFile.appendToBeforeAll(ResourceUtils.getFileContentFromWebapp(file.getSrc()));
				break;

			case AFTERSTARTDOCUMENTREADY:
				mainFile.appendToAfterStartDocumentReady(ResourceUtils
						.getFileContentFromWebapp(file.getSrc()));
				break;
				
			case BEFOREENDDOCUMENTREADY:
				mainFile.appendToBeforeEndDocumentReady(ResourceUtils
						.getFileContentFromWebapp(file.getSrc()));
				break;
				
			case AFTERALL:
				mainFile.appendToAfterAll(ResourceUtils.getFileContentFromWebapp(file.getSrc()));
				break;
				
			default:
				throw new BadConfigurationException("Unable to get the extraFile " + file.getSrc());
			}
		}
	}
	
	
	/**
	 * TODO
	 * @param mainConf
	 * @param table
	 */
	private void extraConfManagement(JsResource mainJsFile, Map<String, Object> mainConf, HtmlTable table) throws BadConfigurationException {

		for (ExtraConf conf : table.getExtraConfs()) {
			StringBuffer extaConf = new StringBuffer();
			extaConf.append("$.ajax({url:\"");
			extaConf.append(conf.getSrc());
			extaConf.append("\",dataType: \"text\",type: \"GET\", success: function(extraProperties, xhr, response) {");
			extaConf.append("$.extend(true, oTable_");
			extaConf.append(table.getId());
			extaConf.append("_params, eval('(' + extraProperties + ')'));");
			extaConf.append("}, error : function(jqXHR, textStatus, errorThrown){");
			extaConf.append("alert(\"textStatus = \" + textStatus);");
			extaConf.append("alert(\"errorThrown = \" + errorThrown);");
			extaConf.append("}});");
			extaConf.append("console.log(oTable_" + table.getId() + "_params);");
			mainJsFile.appendToBeforeStartDocumentReady(extaConf.toString());
		}
		 
//		// Jackson object mapper
//		ObjectMapper mapper = new ObjectMapper();
//		
//		Map<String, Object> extraConf = new HashMap<String, Object>();
//		Map<String, Object> tmpMap;
//		for (ExtraConf conf : table.getExtraConfs()) {
//			String confStr = ResourceUtils.getFileContentFromWebapp(conf.getSrc());
//			logger.debug("confStr = {}", confStr);
//			JsonNode newJson = JsonUtils.convertStringToJsonNode(confStr);
//			logger.debug("newJson = {}", newJson);
//			JsonNode oldJson = JsonUtils.convertObjectToJsonNode(mainConf);
//			logger.debug("oldJson = {}", oldJson);
//			
//			JsonNode result = JsonUtils.merge(oldJson, newJson);
//			logger.debug("result = {}", result);
//			
//			tmpMap = (Map<String, Object>) JsonUtils.convertJsonNodeToObject(result, Map.class);
//			extraConf.putAll(tmpMap);
//		}
//		
//		logger.debug("extraConf = {}", extraConf);
//		mainConf.putAll(extraConf);
	}
}
