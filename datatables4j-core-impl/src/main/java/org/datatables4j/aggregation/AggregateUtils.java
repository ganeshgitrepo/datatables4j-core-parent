package org.datatables4j.aggregation;

import java.util.Map.Entry;

import org.datatables4j.constants.ResourceType;
import org.datatables4j.model.CssResource;
import org.datatables4j.model.JsResource;
import org.datatables4j.model.WebResources;
import org.datatables4j.util.NameConstants;
import org.datatables4j.util.ResourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 *
 * @author Thibault Duchateau
 */
public class AggregateUtils {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(AggregateUtils.class);

	/**
	 * TODO
	 * @param webResources
	 */
	public static void aggregateAll(WebResources webResources){
		
		String jsResourceName = NameConstants.DT_AGG_ALL_JS + ResourceUtils.getRamdomNumber() + ".js";
		String cssResourceName = NameConstants.DT_AGG_ALL_CSS + ResourceUtils.getRamdomNumber() + ".css";
		
		JsResource aggregateJsFile = new JsResource(jsResourceName);
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
