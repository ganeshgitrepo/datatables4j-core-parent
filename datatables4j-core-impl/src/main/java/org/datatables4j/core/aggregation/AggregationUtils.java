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
package org.datatables4j.core.aggregation;

import java.util.Map.Entry;

import org.datatables4j.core.api.constants.ResourceType;
import org.datatables4j.core.api.model.CssResource;
import org.datatables4j.core.api.model.HtmlTable;
import org.datatables4j.core.api.model.JsResource;
import org.datatables4j.core.api.model.WebResources;
import org.datatables4j.core.properties.PropertiesLoader;
import org.datatables4j.core.util.NameConstants;
import org.datatables4j.core.util.ResourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 *
 * @author Thibault Duchateau
 */
public class AggregationUtils {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(AggregationUtils.class);

	/**
	 * TODO
	 * @param webResources
	 * @param table
	 */
	public static void processAggregation(WebResources webResources, HtmlTable table) {
		
		PropertiesLoader properties = PropertiesLoader.getInstance();
		
		switch (properties.getAggregatorMode()) {
		case ALL:
			aggregateAll(webResources);
			break;

		case PLUGINS_JS:
			aggregatePluginsJs(webResources);
			break;

		case PLUGINS_CSS:
			aggregatePluginsCss(webResources);
			break;
		}
	}
	
	/**
	 * TODO
	 * @param webResources
	 */
	public static void aggregateAll(WebResources webResources){
		
		String jsResourceName = NameConstants.DT_AGG_ALL_JS + ResourceUtils.getRamdomNumber() + ".js";
		String cssResourceName = NameConstants.DT_AGG_ALL_CSS + ResourceUtils.getRamdomNumber() + ".css";
		
		JsResource aggregateJsFile = new JsResource(ResourceType.AGGREGATE, jsResourceName);
		CssResource aggregateCssFile = new CssResource(cssResourceName);
		
		String aggregatedJsContent = "";
		String aggregatedCssContent = "";
		
		for (Entry<String, JsResource> entry : webResources.getJavascripts().entrySet()) {
			aggregatedJsContent += entry.getValue().getContent();
		}
		aggregateJsFile.setContent(aggregatedJsContent);
		
		for (Entry<String, CssResource> entry : webResources.getStylesheets().entrySet()) {
			aggregatedCssContent += entry.getValue().getContent();
		}
		aggregateCssFile.setContent(aggregatedCssContent);
		
		// Remove all existing Javascript resources
		webResources.getJavascripts().clear();
		
		// Add aggregated one
		webResources.getJavascripts().put(aggregateJsFile.getName(), aggregateJsFile);
		
		// Remove all existing Stylesheets resources
		webResources.getStylesheets().clear();
		
		// Add aggregated one
		webResources.getStylesheets().put(aggregateCssFile.getName(), aggregateCssFile);
		
		logger.debug("Aggregation (ALL) completed");
	}
	
	/**
	 * TODO
	 * @param webResources
	 */
	public static void aggregatePluginsJs(WebResources webResources){
		
		String jsResourceName = NameConstants.DT_AGG_PLUGINS_JS + ResourceUtils.getRamdomNumber() + ".js";
		JsResource aggregatePluginsJsFile = new JsResource(jsResourceName);
		
		String aggregatedJsContent = "";
		
		for (Entry<String, JsResource> entry : webResources.getJavascripts().entrySet()) {
			
			// Filter plugin only
			if(entry.getValue().getType().equals(ResourceType.PLUGIN)){
				aggregatedJsContent += entry.getValue().getContent();
			}
			
			// Remove the plugin JsResource from the map
			webResources.getJavascripts().remove(entry.getKey());			
		}
				
		aggregatePluginsJsFile.setContent(aggregatedJsContent);
		
		webResources.getJavascripts().put(aggregatePluginsJsFile.getName(), aggregatePluginsJsFile);
		
		logger.debug("Aggregation (PLUGINS_JS) completed");
	}
	
	/**
	 * TODO
	 * @param webResources
	 */
	public static void aggregatePluginsCss(WebResources webResources){
		
		String cssResourceName = NameConstants.DT_AGG_PLUGINS_CSS + ResourceUtils.getRamdomNumber() + ".css";
		CssResource aggregatePluginsCssFile = new CssResource(cssResourceName);
		
		String aggregatedCssContent = "";
		
		for (Entry<String, CssResource> entry : webResources.getStylesheets().entrySet()) {
			
			// Filter plugin only
			if(entry.getValue().getType().equals(ResourceType.PLUGIN)){
				aggregatedCssContent += entry.getValue().getContent();
			}
			
			// Remove the plugin JsResource from the map
			webResources.getStylesheets().remove(entry.getKey());			
		}
				
		aggregatePluginsCssFile.setContent(aggregatedCssContent);
		
		webResources.getStylesheets().put(aggregatePluginsCssFile.getName(), aggregatePluginsCssFile);
		
		logger.debug("Aggregation (PLUGINS_CSS) completed");
	}
}
