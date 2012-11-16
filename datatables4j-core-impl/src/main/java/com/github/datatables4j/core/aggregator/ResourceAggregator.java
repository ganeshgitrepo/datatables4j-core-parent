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
package com.github.datatables4j.core.aggregator;

import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.datatables4j.core.api.constants.ResourceType;
import com.github.datatables4j.core.api.model.CssResource;
import com.github.datatables4j.core.api.model.HtmlTable;
import com.github.datatables4j.core.api.model.JsResource;
import com.github.datatables4j.core.api.model.WebResources;
import com.github.datatables4j.core.util.NameConstants;
import com.github.datatables4j.core.util.ResourceHelper;

/**
 * Web resources aggregator.
 *
 * @author Thibault Duchateau
 */
public class ResourceAggregator {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(ResourceAggregator.class);

	/**
	 * Main routine of the aggregator which launches different type of
	 * aggregation depending on the datatables4j configuration.
	 * 
	 * @param webResources
	 *            The wrapper POJO containing all web resources to aggregate.
	 * @param table
	 *            The table containing the datatatables4j configuration.
	 */
	public static void processAggregation(WebResources webResources, HtmlTable table) {
		
		logger.debug("Processing aggregation, using configuration {}", table.getTableProperties().getAggregatorMode());
		
		switch (table.getTableProperties().getAggregatorMode()) {
		case ALL:
			aggregateAll(webResources);
			break;

		case PLUGINS_JS:
			aggregatePluginsJs(webResources);
			break;

		case PLUGINS_CSS:
			aggregatePluginsCss(webResources);
			break;
			
		default:
			break;
		}
	}
	
	/**
	 * All web resources are aggregated. <li>All javascript resources will be
	 * merge into one file <li>All stylesheets resources will be merge into one
	 * file, if there is some.
	 * 
	 * @param webResources
	 *            The wrapper POJO containing all web resources to aggregate.
	 */
	public static void aggregateAll(WebResources webResources){
		
		String jsResourceName = NameConstants.DT_AGG_ALL_JS + ResourceHelper.getRamdomNumber() + ".js";
		String cssResourceName = NameConstants.DT_AGG_ALL_CSS + ResourceHelper.getRamdomNumber() + ".css";
		
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
		
		// All existing Javascript resources are removed
		webResources.getJavascripts().clear();
		
		// Add aggregated Javascript resource
		webResources.getJavascripts().put(aggregateJsFile.getName(), aggregateJsFile);
		
		// All existing Stylesheets resources are removed
		webResources.getStylesheets().clear();
		
		// Add aggregated stylesheet resource
		webResources.getStylesheets().put(aggregateCssFile.getName(), aggregateCssFile);
		
		logger.debug("Aggregation (ALL) completed");
	}
	
	/**
	 * Only javascript resources are aggregated. The other ones remain unchanged.
	 * 
	 * @param webResources
	 *            The wrapper POJO containing all web resources to aggregate.
	 */
	public static void aggregatePluginsJs(WebResources webResources){
		
		String jsResourceName = NameConstants.DT_AGG_PLUGINS_JS + ResourceHelper.getRamdomNumber() + ".js";
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
	 * Only stylesheet resources are aggregated. The other ones remain unchanged.
	 * 
	 * @param webResources
	 *            The wrapper POJO containing all web resources to aggregate.
	 */
	public static void aggregatePluginsCss(WebResources webResources){
		
		String cssResourceName = NameConstants.DT_AGG_PLUGINS_CSS + ResourceHelper.getRamdomNumber() + ".css";
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
