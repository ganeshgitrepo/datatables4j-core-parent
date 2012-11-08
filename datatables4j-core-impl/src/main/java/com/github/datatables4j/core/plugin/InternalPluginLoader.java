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
package com.github.datatables4j.core.plugin;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.datatables4j.core.api.constants.ResourceType;
import com.github.datatables4j.core.api.exception.BadConfigurationException;
import com.github.datatables4j.core.api.model.CssResource;
import com.github.datatables4j.core.api.model.HtmlTable;
import com.github.datatables4j.core.api.model.JsResource;
import com.github.datatables4j.core.api.model.Plugin;
import com.github.datatables4j.core.api.model.PluginConf;
import com.github.datatables4j.core.api.model.WebResources;
import com.github.datatables4j.core.util.NameConstants;
import com.github.datatables4j.core.util.ResourceUtils;

/**
 * Internal plugin loader (e.g. : Scroller, FixedHeader, ...).
 * 
 * @author Thibault Duchateau
 */
public class InternalPluginLoader {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(InternalPluginLoader.class);

	/**
	 * <p>Load every plugin activated by the user in the table tag.</p>
	 * <p>A plugin may be composed of several elements :
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
	public static void loadPlugins(JsResource mainJsFile, HtmlTable table, Map<String, Object> mainConf, WebResources webResources) throws BadConfigurationException {

		JsResource pluginsSourceJsFile = null;
		CssResource pluginsSourceCssFile = null;

		logger.info("Check for plugins ...");
		
		if(!table.getPlugins().isEmpty()){
			
			for (Plugin plugin : table.getPlugins()) {
	
				logger.debug("Loading {} v{} ...", plugin.getPluginName(), plugin.getPluginVersion());
				
				// Module initialization
				plugin.setup(table);
	
				// Module javascript
				if(!plugin.getJsResources().isEmpty()){
					
					pluginsSourceJsFile = new JsResource(ResourceType.PLUGIN, NameConstants.DT_PLUGIN_JS + plugin.getPluginName().toLowerCase() + ".js");
	
					// Module source loading (javascript)
					for (JsResource jsResource : plugin.getJsResources()) {
						String location = "datatables/plugins/" + plugin.getPluginName().toLowerCase() + "/js/"
								+ jsResource.getName();
						pluginsSourceJsFile.setContent(ResourceUtils.getFileContentFromClasspath(location));
					}
	
					webResources.getJavascripts().put(pluginsSourceJsFile.getName(), pluginsSourceJsFile);
					
				}
				
				// Module configuration loading
				if (StringUtils.isNotBlank(plugin.getBeforeAllScript())) {
					mainJsFile.appendToBeforeAll(plugin.getBeforeAllScript());
				}
				if (StringUtils.isNotBlank(plugin.getAfterStartDocumentReady())) {
					mainJsFile.appendToAfterStartDocumentReady(plugin.getAfterStartDocumentReady());
				}
				if (StringUtils.isNotBlank(plugin.getBeforeEndDocumentReady())) {
					mainJsFile.appendToBeforeEndDocumentReady(plugin.getBeforeEndDocumentReady());
				}
				if (StringUtils.isNotBlank(plugin.getAfterAllScript())) {
					mainJsFile.appendToAfterAll(plugin.getAfterAllScript());
				}

				for (PluginConf conf : plugin.getPluginConfs()) {
					
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
			
				// Module stylesheet
				if(!plugin.getCssResources().isEmpty()){
					
					pluginsSourceCssFile = new CssResource("plugin", NameConstants.DT_PLUGIN_JS + plugin.getPluginName().toLowerCase() + ".css");
					StringBuffer cssContent = new StringBuffer();
					
					// Module source loading (stylesheets)
					for (CssResource cssResource : plugin.getCssResources()) {
						String location = "datatables/plugins/" + plugin.getPluginName().toLowerCase() + "/css/"
								+ cssResource.getName();
						cssContent.append(ResourceUtils.getFileContentFromClasspath(location));
					}
					
					pluginsSourceCssFile.setContent(cssContent.toString());
					webResources.getStylesheets().put(pluginsSourceCssFile.getName(), pluginsSourceCssFile);
				}
			}

			logger.debug("All UI plugins loaded");
		}
		else{
			logger.info("No plugin to load");
		}
	}
}