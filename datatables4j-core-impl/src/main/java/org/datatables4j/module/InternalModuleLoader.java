package org.datatables4j.module;

import org.apache.commons.lang.StringUtils;
import org.datatables4j.configuration.MainConf;
import org.datatables4j.model.HtmlTable;
import org.datatables4j.model.JavascriptFile;
import org.datatables4j.model.JsResource;
import org.datatables4j.model.Module;
import org.datatables4j.model.ModuleConf;
import org.datatables4j.util.ResourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class qui charge les modules
 * 
 * @author Thibault Duchateau
 */
// Chargement des modules internes (scroller, fixedHeader, etc etc)
public class InternalModuleLoader {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(InternalModuleLoader.class);
	
	public static void loadModules(JavascriptFile jsFile, HtmlTable table, MainConf mainConf) {

		logger.info("Loading UI modules ...");
		
		for (Module module : table.getModules()) {

			logger.debug("Loading {} v{} ...", module.getModuleName(), module.getModuleVersion());
			
			// Module initialization
			module.setup(table);

			// Module source loading
			for (JsResource jsResource : module.getJsResources()) {
				String location = "modules/" + module.getModuleName().toLowerCase() + "/js/" + jsResource.getName();
				jsFile.appendToBeforeAll(ResourceUtils.getFileContentFromClasspath(location));
			}

			// TODO
			// Ajouter chargement des CSS si besoin

			// Module configuration loading
			if (StringUtils.isNotBlank(module.getBeforeAllScript())) {
				jsFile.appendToBeforeAll(module.getBeforeAllScript());
			}
			if (StringUtils.isNotBlank(module.getAfterStartDocumentReady())) {
				jsFile.appendToAfterStartDocumentReady(module.getAfterStartDocumentReady());
			}
			if (StringUtils.isNotBlank(module.getBeforeEndDocumentReady())) {
				jsFile.appendToBeforeEndDocumentReady(module.getBeforeEndDocumentReady());
			}
			if (StringUtils.isNotBlank(module.getAfterAllScript())) {
				jsFile.appendToAfterAll(module.getAfterAllScript());
			}

			for (ModuleConf conf : module.getModuleConfs()) {
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
				} else {
					mainConf.put(conf.getName(), conf.getValue());
				}
			}
			
			logger.debug("All UI modules loaded");
		}
	}
}
