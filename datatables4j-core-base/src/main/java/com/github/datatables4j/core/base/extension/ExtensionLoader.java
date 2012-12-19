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
package com.github.datatables4j.core.base.extension;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.datatables4j.core.api.constants.ResourceType;
import com.github.datatables4j.core.api.exception.BadConfigurationException;
import com.github.datatables4j.core.api.model.AbstractExtension;
import com.github.datatables4j.core.api.model.Configuration;
import com.github.datatables4j.core.api.model.CssResource;
import com.github.datatables4j.core.api.model.HtmlTable;
import com.github.datatables4j.core.api.model.JsResource;
import com.github.datatables4j.core.api.model.WebResources;
import com.github.datatables4j.core.base.util.JsonIndentingWriter;
import com.github.datatables4j.core.base.util.NameConstants;
import com.github.datatables4j.core.base.util.ResourceHelper;

/**
 * TODO
 * 
 * @author Thibault Duchateau
 */
public class ExtensionLoader {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(ExtensionLoader.class);

	private HtmlTable table;
	private JsResource mainJsFile;
	private Map<String, Object> mainConfig;
	private WebResources webResources;

	/**
	 * Constructor of the ExtensionLoader.
	 * 
	 * @param table
	 *            The table containing module informations.
	 * @param mainJsFile
	 *            The main Javascript file which will be generated and may be
	 *            updated accordingly to modules.
	 * @param mainConfig
	 *            Main DataTables configuration which may be updated accordingly
	 *            to modules.
	 * @param webResources
	 *            The wrapper POJO containing all web resources to generate.
	 * @throws BadConfigurationException
	 * @throws IOException
	 */
	public ExtensionLoader(HtmlTable table, JsResource mainJsFile, Map<String, Object> mainConfig,
			WebResources webResources) {
		this.table = table;
		this.mainJsFile = mainJsFile;
		this.mainConfig = mainConfig;
		this.webResources = webResources;
	}

	/**
	 * Load all type of extension (plugin, feature, theme).
	 * 
	 * @param extensions
	 *            The collection of extensions to load.
	 * @param type
	 *            The type of extension to load.
	 * @throws BadConfigurationException
	 * @throws IOException
	 */
	public void load(List<? extends AbstractExtension> extensions, AbstractExtension.Type type)
			throws BadConfigurationException, IOException {

		String extensionBasePath = getPath(type);

		if (extensions != null && !extensions.isEmpty()) {

			// TODO tester s'il faut appender un random number
			for (AbstractExtension extension : extensions) {

				// Extension initialization
				extension.setup(table);

				// TODO
				loadJsResources(extension, extensionBasePath, type);

				// TODO
				loadCssResources(extension, extensionBasePath, type);

				// TODO
				injectIntoMainJsFile(extension);

				// TODO
				injectIntoMainConfiguration(extension);
			}
		}
	}

	/**
	 * 
	 * @param extension
	 * @param extensionBasePath
	 * @param type
	 * @throws BadConfigurationException
	 */
	private void loadJsResources(AbstractExtension extension, String extensionBasePath, AbstractExtension.Type type)
			throws BadConfigurationException {

		JsResource extensionJsFile = null;
		String resourceName = null;

		// Extension javascript
		if (extension.getJsResources() != null && !extension.getJsResources().isEmpty()) {

			resourceName = extension.getAppendRandomNumber() 
					? extension.getName().toLowerCase() + "-" + table.getRandomId() + ".js" 
					: extension.getName().toLowerCase() + ".js";
			extensionJsFile = new JsResource(ResourceType.EXTENSION, resourceName);
			StringBuffer jsContent = new StringBuffer();

			// Feature source loading (javascript)
			for (JsResource jsResource : extension.getJsResources()) {
				System.out.println("location = " + jsResource.getLocation());
				jsContent.append(ResourceHelper.getFileContentFromClasspath(jsResource.getLocation()));
			}

			extensionJsFile.setContent(jsContent.toString());
			webResources.getJavascripts().put(extensionJsFile.getName(), extensionJsFile);
		}
	}

	/**
	 * 
	 * @param extension
	 * @param extensionBasePath
	 * @param type
	 * @throws BadConfigurationException
	 */
	private void loadCssResources(AbstractExtension extension, String extensionBasePath, AbstractExtension.Type type)
			throws BadConfigurationException {

		CssResource pluginsSourceCssFile = null;
		String resourceName = null;

		// Extension javascript
		if (extension.getCssResources() != null && !extension.getCssResources().isEmpty()) {

			resourceName = extension.getAppendRandomNumber() ? NameConstants.DT_PLUGIN_JS
					+ extension.getName().toLowerCase() + "-" + table.getRandomId() + ".css"
					: NameConstants.DT_PLUGIN_JS + extension.getName().toLowerCase() + ".css";
			pluginsSourceCssFile = new CssResource(ResourceType.EXTENSION, resourceName);
			StringBuffer cssContent = new StringBuffer();

			// Module source loading (stylesheets)
			for (CssResource cssResource : extension.getCssResources()) {
				System.out.println("location = " + cssResource.getLocation());
				cssContent.append(ResourceHelper.getFileContentFromClasspath(cssResource.getLocation()));
			}

			pluginsSourceCssFile.setContent(cssContent.toString());
			webResources.getStylesheets().put(pluginsSourceCssFile.getName(), pluginsSourceCssFile);
		}
	}

	
	/**
	 * 
	 * @param extension
	 * @throws IOException
	 */
	private void injectIntoMainJsFile(AbstractExtension extension) throws IOException {

		// Extension configuration loading
		if (extension.getBeforeAllScript() != null) {
			mainJsFile.appendToBeforeAll(extension.getBeforeAllScript().toString());
		}
		if (extension.getAfterStartDocumentReady() != null) {
			mainJsFile.appendToAfterStartDocumentReady(extension.getAfterStartDocumentReady()
					.toString());
		}
		if (extension.getBeforeEndDocumentReady() != null) {
			mainJsFile.appendToBeforeEndDocumentReady(extension.getBeforeEndDocumentReady()
					.toString());
		}
		if (extension.getAfterAllScript() != null) {
			mainJsFile.appendToAfterAll(extension.getAfterAllScript().toString());
		}

		// TODO
		if (extension.getFunction() != null) {
			mainJsFile.appendToDataTablesExtra(extension.getFunction());
		}

		// Extension custom configuration generator
		if (extension.getConfigGenerator() != null) {
			logger.debug("A custom configuration generator has been set");

			Writer writer = new JsonIndentingWriter();

			Map<String, Object> conf = extension.getConfigGenerator().generate(table);

			// Allways pretty prints the JSON
			JSONValue.writeJSONString(conf, writer);

			mainJsFile.appendToDataTablesExtraConf(writer.toString());
		}
	}

	
	/**
	 * 
	 * @param extension
	 */
	private void injectIntoMainConfiguration(AbstractExtension extension) {

		// Extra configuration setting
		if (extension.getConfs() != null) {

			for (Configuration conf : extension.getConfs()) {

				// The module configuration already exists in the main
				// configuration
				if (mainConfig.containsKey(conf.getName())) {
					String value = null;

					switch (conf.getMode()) {
					case OVERRIDE:
						mainConfig.put(conf.getName(), conf.getValue());
						break;

					case APPEND:
						value = (String) mainConfig.get(conf.getName());
						value = value + conf.getValue();
						mainConfig.put(conf.getName(), value);
						break;

					case PREPEND:
						value = (String) mainConfig.get(conf.getName());
						value = conf.getValue() + value;
						mainConfig.put(conf.getName(), value);
						break;

					case APPEND_WITH_SPACE:
						value = (String) mainConfig.get(conf.getName());
						value = value + " " + conf.getValue();
						mainConfig.put(conf.getName(), value);
						break;

					case PREPEND_WITH_SPACE:
						value = (String) mainConfig.get(conf.getName());
						value = conf.getValue() + " " + value;
						mainConfig.put(conf.getName(), value);
						break;

					default:
						break;
					}
				}
				// No existing configuration in the main configuration, just add
				// it
				else {
					mainConfig.put(conf.getName(), conf.getValue());
				}
			}
		}
	}

	/**
	 * TODO
	 * 
	 * @param type
	 * @return
	 */
	private String getPath(AbstractExtension.Type type) {

		switch (type) {
		case FEATURE:
			return "datatables/features/";
		case PLUGIN:
			return "datatables/plugins/";
		case THEME:
			return "datatables/themes/";
		default:
			return "";
		}
	}
}
