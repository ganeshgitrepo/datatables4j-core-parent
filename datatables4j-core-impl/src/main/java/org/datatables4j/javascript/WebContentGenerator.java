package org.datatables4j.javascript;

import java.io.IOException;

import javax.servlet.jsp.PageContext;

import org.datatables4j.compressor.YuiCompressor;
import org.datatables4j.configuration.ConfigGenerator;
import org.datatables4j.configuration.MainConf;
import org.datatables4j.datasource.JerseyDataProvider;
import org.datatables4j.exception.DataNotFoundException;
import org.datatables4j.model.ExtraFile;
import org.datatables4j.model.HtmlTable;
import org.datatables4j.model.JavascriptFile;
import org.datatables4j.module.InternalModuleLoader;
import org.datatables4j.util.JsConstants;
import org.datatables4j.util.RequestHelper;
import org.datatables4j.util.ResourceUtils;
import org.mozilla.javascript.EvaluatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class used for Javascript generation (as text).
 * 
 * @author Thibault Duchateau
 */
public class WebContentGenerator {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(WebContentGenerator.class);

	/**
	 * 
	 */
	private static ConfigGenerator configGenerator;

	/**
	 * Returns the Javascript (as text) which will be serve by the DataTables4j
	 * servlet.
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
	 * @throws EvaluatorException 
	 */
	public static String getJavascript(PageContext pageContext, HtmlTable table)
			throws DataNotFoundException, EvaluatorException, IOException {

//		String filepath = pageContext.getServletContext().getRealPath("js/datatables.extraFile.js");
//		System.out.println("*********** TEST = "
//				+ Utils.getFileContentFromWebapp(pageContext, "js/datatables.extraFile.js"));
//		File file = new File(filepath);
//		try {
//			FileInputStream in = new FileInputStream(file);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		JavascriptFile jsFile = new JavascriptFile();
		configGenerator = new ConfigGenerator();
		MainConf mainConf = configGenerator.generateConfig(table);

		jsFile.appendToBeforeAll("var oTable_");
		jsFile.appendToBeforeAll(table.getId());
		jsFile.appendToBeforeAll(";");
		logger.debug("BeforeAll : {}", jsFile.getBeforeAll());

		// Internal module management
		InternalModuleLoader.loadModules(jsFile, table, mainConf);

		// Extra files management
		extraFileManagement(jsFile, table);

		// DataTables configuration
		// AJAX table
		jsFile.appendToDataTablesConf("oTable_");
		jsFile.appendToDataTablesConf(table.getId());
		jsFile.appendToDataTablesConf("=$('#");
		jsFile.appendToDataTablesConf(table.getId());
		jsFile.appendToDataTablesConf("').dataTable(");
				
		if (table.getDatasourceUrl() != null) {
			String baseUrl = RequestHelper.getBaseUrl(pageContext);
			String webServiceUrl = baseUrl + table.getDatasourceUrl();
			JerseyDataProvider provider = new JerseyDataProvider();

			jsFile.appendToDataTablesConf(
					configGenerator.getConfig(mainConf,provider.getData(webServiceUrl)));
		}
		// DOM Table
		else {
			jsFile.appendToDataTablesConf(configGenerator.getConfig(mainConf));
		}
		
		jsFile.appendToDataTablesConf(");");
		logger.debug("DataTablesConf : {}", jsFile.getDataTablesConf());
		
		logger.debug("datatables4j.js : {}", jsFile.toString());
		
		String output = YuiCompressor.getCompressedJavascript(jsFile.toString());
		logger.debug("datatables4j.js : {}", output);
		return output;
	}
	
	private static void extraFileManagement(JavascriptFile jsFile, HtmlTable table) {

		for (ExtraFile file : table.getExtraFiles()) {
			if (JsConstants.BEFOREALL.equals(file.getInclude())) {
				jsFile.appendToBeforeAll(ResourceUtils.getFileContentFromClasspath(file.getSrc()));
			} else if (JsConstants.AFTERALL.equals(file.getInclude())) {
				jsFile.appendToAfterAll(ResourceUtils.getFileContentFromClasspath(file.getSrc()));
			} else if (JsConstants.AFTERSTARTDOCUMENTEREADY.equals(file.getInclude())) {
				jsFile.appendToAfterStartDocumentReady(ResourceUtils.getFileContentFromClasspath(file
						.getSrc()));
			} else if (JsConstants.BEFOREENDDOCUMENTREADY.equals(file.getInclude())) {
				jsFile.appendToBeforeEndDocumentReady(ResourceUtils.getFileContentFromClasspath(file
						.getSrc()));
			}
		}

		logger.debug("jsfile : {}", jsFile.toString());
	}
}
