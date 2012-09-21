package org.datatables4j.aggregator;

import java.util.Map.Entry;

import org.datatables4j.model.CssResource;
import org.datatables4j.model.JsResource;
import org.datatables4j.model.WebResources;

public class AggregateUtils {

	/**
	 * TODO
	 * @param webResources
	 */
	public static void aggregateAll(WebResources webResources){
		
		JsResource aggregateJsFile = new JsResource("datatables4j-aggregated.js");
		aggregateJsFile.setContent("");
		CssResource aggregateCssFile = new CssResource("datatables4j-aggregated.css");
		aggregateCssFile.setContent("");
		
		for (Entry<String, JsResource> entry : webResources.getJavascripts().entrySet()) {
			aggregateJsFile.setContent(aggregateJsFile.getContent() + entry.getValue().getContent());
		}
		for (Entry<String, CssResource> entry : webResources.getStylesheets().entrySet()) {
			aggregateCssFile.setContent(aggregateCssFile.getContent() + entry.getValue().getContent());
		}
		
		webResources.getJavascripts().clear();
		webResources.getJavascripts().put(aggregateJsFile.getName(), aggregateJsFile);
		
		webResources.getStylesheets().clear();
		webResources.getStylesheets().put(aggregateCssFile.getName(), aggregateCssFile);
	}
	
	/**
	 * TODO
	 * @param webResources
	 */
	public static void aggregatePluginsJs(WebResources webResources){
		
		JsResource aggregatePluginsJsFile = new JsResource("datatables4j-plugins-aggregated.js");
		aggregatePluginsJsFile.setContent("");
		
		for (Entry<String, JsResource> entry : webResources.getJavascripts().entrySet()) {
			
			if(entry.getValue().getType().equals("plugin")){
				aggregatePluginsJsFile.setContent(aggregatePluginsJsFile.getContent() + entry.getValue().getContent());
			}
			
			// Remove the plugin JsResource from the map
			webResources.getJavascripts().remove(entry.getKey());
//			
//			aggregatePluginsJsFile.setContent(aggregatePluginsJsFile.getContent() + entry.getValue().getContent());
		}
				
		webResources.getJavascripts().put(aggregatePluginsJsFile.getName(), aggregatePluginsJsFile);
	}
}
