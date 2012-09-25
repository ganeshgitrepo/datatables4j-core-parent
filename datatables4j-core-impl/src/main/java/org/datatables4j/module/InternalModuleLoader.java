package org.datatables4j.module;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.datatables4j.exception.BadConfigurationException;
import org.datatables4j.model.CssResource;
import org.datatables4j.model.HtmlTable;
import org.datatables4j.model.JsResource;
import org.datatables4j.model.Module;
import org.datatables4j.model.ModuleConf;
import org.datatables4j.model.WebResources;
import org.datatables4j.util.ResourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Internal modules loader (e.g. : Scroller, FixedHeader, ...).
 * 
 * @author Thibault Duchateau
 */
public class InternalModuleLoader {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(InternalModuleLoader.class);

	/**
	 * Load every module activated by the user in the table tag.
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
	public static void loadModules(JsResource mainJsFile, HtmlTable table, Map<String, Object> mainConf, WebResources webResources) throws BadConfigurationException {

		JsResource modulesSourceJsFile = null;
		CssResource modulesSourceCssFile = null;

		logger.info("Check for modules ...");
		
		if(!table.getModules().isEmpty()){
			
			for (Module module : table.getModules()) {
	
				logger.debug("Loading {} v{} ...", module.getModuleName(), module.getModuleVersion());
				
				// Module initialization
				module.setup(table);
	
				// Module javascript
				if(!module.getJsResources().isEmpty()){
					modulesSourceJsFile = new JsResource("plugin", "datatables4j-" + module.getModuleName().toLowerCase() + ".js");
	
					// Module source loading (javascript)
					for (JsResource jsResource : module.getJsResources()) {
						String location = "modules/" + module.getModuleName().toLowerCase() + "/js/"
								+ jsResource.getName();
						modulesSourceJsFile.appendToBeforeAll(ResourceUtils.getFileContentFromClasspath(location));
					}
	
					// Module configuration loading
					if (StringUtils.isNotBlank(module.getBeforeAllScript())) {
						mainJsFile.appendToBeforeAll(module.getBeforeAllScript());
					}
					if (StringUtils.isNotBlank(module.getAfterStartDocumentReady())) {
						mainJsFile.appendToAfterStartDocumentReady(module.getAfterStartDocumentReady());
					}
					if (StringUtils.isNotBlank(module.getBeforeEndDocumentReady())) {
						mainJsFile.appendToBeforeEndDocumentReady(module.getBeforeEndDocumentReady());
					}
					if (StringUtils.isNotBlank(module.getAfterAllScript())) {
						mainJsFile.appendToAfterAll(module.getAfterAllScript());
					}
	
					for (ModuleConf conf : module.getModuleConfs()) {
						
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
	
					webResources.getJavascripts().put(modulesSourceJsFile.getName(), modulesSourceJsFile);
				}
			
				// Module stylesheet
				if(!module.getCssResources().isEmpty()){
					
					modulesSourceCssFile = new CssResource("plugin", "datatables4j-" + module.getModuleName().toLowerCase() + ".css");
					StringBuffer cssContent = new StringBuffer();
					
					// Module source loading (stylesheets)
					for (CssResource cssResource : module.getCssResources()) {
						String location = "modules/" + module.getModuleName().toLowerCase() + "/css/"
								+ cssResource.getName();
						cssContent.append(ResourceUtils.getFileContentFromClasspath(location));
					}
					
					modulesSourceCssFile.setContent(cssContent.toString());
					webResources.getStylesheets().put(modulesSourceCssFile.getName(), modulesSourceCssFile);
				}
			}

			logger.debug("All UI modules loaded");
		}
		else{
			logger.info("No module to load");
		}
	}
}
