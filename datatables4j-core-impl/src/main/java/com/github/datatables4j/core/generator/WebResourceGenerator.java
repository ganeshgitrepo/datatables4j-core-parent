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

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.datatables4j.core.api.constants.DTConstants;
import com.github.datatables4j.core.api.constants.ResourceType;
import com.github.datatables4j.core.api.exception.BadConfigurationException;
import com.github.datatables4j.core.api.exception.CompressionException;
import com.github.datatables4j.core.api.exception.DataNotFoundException;
import com.github.datatables4j.core.api.model.ExportLinkPosition;
import com.github.datatables4j.core.api.model.ExportConf;
import com.github.datatables4j.core.api.model.ExtraConf;
import com.github.datatables4j.core.api.model.ExtraFile;
import com.github.datatables4j.core.api.model.HtmlDiv;
import com.github.datatables4j.core.api.model.HtmlLink;
import com.github.datatables4j.core.api.model.HtmlTable;
import com.github.datatables4j.core.api.model.JsResource;
import com.github.datatables4j.core.api.model.WebResources;
import com.github.datatables4j.core.datasource.DataProvider;
import com.github.datatables4j.core.feature.FeatureLoader;
import com.github.datatables4j.core.plugin.InternalPluginLoader;
import com.github.datatables4j.core.util.NameConstants;
import com.github.datatables4j.core.util.RequestHelper;
import com.github.datatables4j.core.util.ResourceHelper;

/**
 * Class in charge of web resources generation.
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
	 * <p>Main method which generated the web resources (js and css files).
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
			throws DataNotFoundException, CompressionException, IOException,
			BadConfigurationException {

		// Bean which stores all needed web resources (js, css)
		WebResources webResources = new WebResources();

		// Init the "configuration" map with the table informations
		// The configuration may be updated depending on the user's choices
		configGenerator = new ConfigGenerator();
		Map<String, Object> mainConf = configGenerator.generateConfig(table);

		/**
		 * Build the main file.
		 */
		// We need to append a randomUUID in case of multiple tables exists in
		// the same JSP
		JsResource mainJsFile = new JsResource(ResourceType.MAIN, NameConstants.DT_MAIN_JS
				+ table.getRandomId() + ".js", table.getId());

		// Extra files management
		if (!table.getExtraFiles().isEmpty()) {
			extraFileManagement(mainJsFile, table);
		}

		// Internal module management
		InternalPluginLoader.loadPlugins(mainJsFile, table, mainConf, webResources);
		FeatureLoader.loadFeatures(mainJsFile, table, mainConf, webResources);

		// Extra conf management
		extraConfManagement(mainJsFile, mainConf, table);

		// AJAX datasource : data must be added in the configuration file
		if (table.getDatasourceUrl() != null) {
			String baseUrl = RequestHelper.getBaseUrl(pageContext);
			String webServiceUrl = baseUrl + table.getDatasourceUrl();

			DataProvider dataProvider = new DataProvider();

			mainConf.put(DTConstants.DT_DS_DATA, dataProvider.getData(table, webServiceUrl));

			mainJsFile.appendToDataTablesConf(JSONValue.toJSONString(mainConf));
		}
		// DOM datasource
		else {
			mainJsFile.appendToDataTablesConf(JSONValue.toJSONString(mainConf));
		}

		if (table.isExportable()) {
			exportManagement(table, mainJsFile);
		}

		webResources.getJavascripts().put(mainJsFile.getName(), mainJsFile);

		return webResources;
	}

	/**
	 * <p>
	 * If the export attribute of the table tag has been set to true, this
	 * method will convert every ExportConf bean to a HTML link, corresponding
	 * to each activated export type.
	 * 
	 * <p>
	 * All the link are wrapped into a div which is inserted in the DOM using
	 * jQuery. The wrapping div can be inserted at multiple position, depending
	 * on the tag configuration.
	 * 
	 * <p>
	 * If the user didn't add any ExportTag, DataTables4j will use the default
	 * configuration.
	 * 
	 * @param table
	 *            The HTML table where to get ExportConf beans.
	 * @param mainJsFile
	 *            The web resource to update
	 */
	private void exportManagement(HtmlTable table, JsResource mainJsFile) {

		// Init the wrapping HTML div
		HtmlDiv divExport = new HtmlDiv();

		// ExportTag have been added to the TableTag
		if (table.getExportConfMap() != null && table.getExportConfMap().size() > 0) {
			logger.debug("ExportTag have been found. Generating export links");

			HtmlLink link = null;

			// A HTML link is generated for each ExportConf bean
			for (ExportConf conf : table.getExportConfMap().values()) {

				link = new HtmlLink();

				if (StringUtils.isNotBlank(conf.getId())) {
					link.setId(conf.getId());
				}

				if (conf.getCssClass() != null) {
					link.setCssClass(conf.getCssClass());
				}

				if (conf.getCssStyle() != null) {
					link.setCssStyle(conf.getCssStyle());
				}
				link.addCssStyle("margin-left:2px;margin-left:2px;");

				link.setHref(conf.getUrl());
				link.setLabel(conf.getLabel());

				divExport.addContent(link.toHtml());
			}
		}

		for (ExportLinkPosition position : table.getExportLinkPositions()) {

			switch (position) {
			case BOTTOM_LEFT:
				divExport.addCssStyle("float:left;margin-right:10px;");
				mainJsFile.appendToBeforeEndDocumentReady("$('#" + table.getId()
						+ "_info').before('" + divExport.toHtml() + "');$('#" + table.getId()
						+ "_info').css('clear', 'none');");
				break;

			case BOTTOM_MIDDLE:
				divExport.addCssStyle("float:left;margin-left:10px;");
				mainJsFile.appendToBeforeEndDocumentReady("$('#" + table.getId()
						+ "_paginate').before('" + divExport.toHtml() + "');");
				break;

			case BOTTOM_RIGHT:
				divExport.addCssStyle("float:right;");
				mainJsFile.appendToBeforeEndDocumentReady("$('#" + table.getId()
						+ "_paginate').before('" + divExport.toHtml() + "');");
				break;

			case TOP_LEFT:
				divExport.addCssStyle("float:left;margin-right:10px;");
				mainJsFile.appendToBeforeEndDocumentReady("$('#" + table.getId()
						+ "_length').before('" + divExport.toHtml() + "');");
				break;

			case TOP_MIDDLE:
				divExport.addCssStyle("float:left;margin-left:10px;");
				mainJsFile.appendToBeforeEndDocumentReady("$('#" + table.getId()
						+ "_filter').before('" + divExport.toHtml() + "');");
				break;

			case TOP_RIGHT:
				divExport.addCssStyle("float:right;");
				mainJsFile.appendToBeforeEndDocumentReady("$('#" + table.getId()
						+ "_length').after('" + divExport.toHtml() + "');");
				break;

			default:
				break;
			}
		}
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
	 */
	private void extraFileManagement(JsResource mainFile, HtmlTable table) throws IOException,
			BadConfigurationException {

		logger.info("Extra files found");

		for (ExtraFile file : table.getExtraFiles()) {

			switch (file.getInsert()) {
			case BEFOREALL:
				mainFile.appendToBeforeAll(ResourceHelper.getFileContentFromWebapp(file.getSrc()));
				break;

			case AFTERSTARTDOCUMENTREADY:
				mainFile.appendToAfterStartDocumentReady(ResourceHelper
						.getFileContentFromWebapp(file.getSrc()));
				break;

			case BEFOREENDDOCUMENTREADY:
				mainFile.appendToBeforeEndDocumentReady(ResourceHelper
						.getFileContentFromWebapp(file.getSrc()));
				break;

			case AFTERALL:
				mainFile.appendToAfterAll(ResourceHelper.getFileContentFromWebapp(file.getSrc()));
				break;

			default:
				throw new BadConfigurationException("Unable to get the extraFile " + file.getSrc());
			}
		}
	}

	/**
	 * Generates a jQuery AJAX call to be able to merge the server-generated
	 * DataTables configuration with the configuration stored in extraConf
	 * files. <br />
	 * Warning : this is a temporary method. The goal is to be able to generate
	 * the entire configuration server-side.
	 * 
	 * @param mainConf
	 * @param table
	 */
	private void extraConfManagement(JsResource mainJsFile, Map<String, Object> mainConf,
			HtmlTable table) throws BadConfigurationException {

		for (ExtraConf conf : table.getExtraConfs()) {
			StringBuffer extaConf = new StringBuffer();
			extaConf.append("$.ajax({url:\"");
			extaConf.append(conf.getSrc());
			extaConf.append("\",dataType: \"text\",type: \"GET\", success: function(extraProperties, xhr, response) {");
			extaConf.append("$.extend(true, oTable_");
			extaConf.append(table.getId());
			extaConf.append("_params, eval('(' + extraProperties + ')'));");
			extaConf.append("}, error : function(jqXHR, textStatus, errorThrown){");
			extaConf.append("console.log(textStatus);");
			extaConf.append("console.log(errorThrown);");
			extaConf.append("}});");
			extaConf.append("console.log(oTable_" + table.getId() + "_params);");
			mainJsFile.appendToBeforeStartDocumentReady(extaConf.toString());
		}

		// TODO Old way here, trying to parse in JSON the content of extraConf
		// file
		// TODO using Jackson but "function" keyword is not JSON compliant
		
		// // Jackson object mapper
		// ObjectMapper mapper = new ObjectMapper();
		//
		// Map<String, Object> extraConf = new HashMap<String, Object>();
		// Map<String, Object> tmpMap;
		// for (ExtraConf conf : table.getExtraConfs()) {
		// String confStr =
		// ResourceHelper.getFileContentFromWebapp(conf.getSrc());
		// logger.debug("confStr = {}", confStr);
		// JsonNode newJson = JsonUtils.convertStringToJsonNode(confStr);
		// logger.debug("newJson = {}", newJson);
		// JsonNode oldJson = JsonUtils.convertObjectToJsonNode(mainConf);
		// logger.debug("oldJson = {}", oldJson);
		//
		// JsonNode result = JsonUtils.merge(oldJson, newJson);
		// logger.debug("result = {}", result);
		//
		// tmpMap = (Map<String, Object>)
		// JsonUtils.convertJsonNodeToObject(result, Map.class);
		// extraConf.putAll(tmpMap);
		// }
		//
		// logger.debug("extraConf = {}", extraConf);
		// mainConf.putAll(extraConf);
	}
}