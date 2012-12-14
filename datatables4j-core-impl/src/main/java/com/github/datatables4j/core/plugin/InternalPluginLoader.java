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
import com.github.datatables4j.core.api.model.Configuration;
import com.github.datatables4j.core.api.model.WebResources;
import com.github.datatables4j.core.util.NameConstants;
import com.github.datatables4j.core.util.ResourceHelper;

/**
 * Internal plugin loader class (e.g. : Scroller, FixedHeader, ...).
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
	
				logger.debug("Loading {} v{} ...", plugin.getName(), plugin.getVersion());
				
				// Module initialization
				plugin.setup(table);
	
				// Module javascript
				if(!plugin.getJsResources().isEmpty()){
					
					pluginsSourceJsFile = new JsResource(ResourceType.PLUGIN, NameConstants.DT_PLUGIN_JS + plugin.getName().toLowerCase() + ".js");
	
					// Module source loading (javascript)
					for (JsResource jsResource : plugin.getJsResources()) {
						String location = "datatables/plugins/" + plugin.getName().toLowerCase() + "/js/"
								+ jsResource.getName();
						pluginsSourceJsFile.setContent(ResourceHelper.getFileContentFromClasspath(location));
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

				if(plugin.getConfs() != null){

					for (Configuration conf : plugin.getConfs()) {
						
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
				}
			
				// Module stylesheet
				if(plugin.getCssResources() != null && !plugin.getCssResources().isEmpty()){
					
					pluginsSourceCssFile = new CssResource("plugin", NameConstants.DT_PLUGIN_JS + plugin.getName().toLowerCase() + ".css");
					StringBuffer cssContent = new StringBuffer();
					
					// Module source loading (stylesheets)
					for (CssResource cssResource : plugin.getCssResources()) {
						String location = "datatables/plugins/" + plugin.getName().toLowerCase() + "/css/"
								+ cssResource.getName();
						cssContent.append(ResourceHelper.getFileContentFromClasspath(location));
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