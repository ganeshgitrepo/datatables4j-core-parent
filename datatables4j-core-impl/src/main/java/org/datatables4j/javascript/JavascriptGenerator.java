package org.datatables4j.javascript;

import javax.servlet.jsp.PageContext;

import org.datatables4j.configuration.ConfigGenerator;
import org.datatables4j.datasource.JerseyDataProvider;
import org.datatables4j.exception.DataNotFoundException;
import org.datatables4j.model.ExtraFile;
import org.datatables4j.model.HtmlTable;
import org.datatables4j.model.InternalModule;
import org.datatables4j.util.JsConstants;
import org.datatables4j.util.RequestHelper;
import org.datatables4j.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class used for Javascript generation (as text).
 * 
 * @author Thibault Duchateau
 */
public class JavascriptGenerator {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(JavascriptGenerator.class);

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
	 */
	public static String getScript(PageContext pageContext, HtmlTable table)
			throws DataNotFoundException {

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

		// Extra features activation (as internal modules)
		StringBuffer extraConf = new StringBuffer();
		for (InternalModule internalModule : table.getInternalModules()) {
			extraConf.append(Utils.getFileContentFromClasspath("modules/"
					+ internalModule.getName() + "/js/" + internalModule.getName() + ".min.js"));
		}

		jsFile.appendToBeforeAll(extraConf.toString());
		logger.debug("ExtraConf : {}", extraConf);

		jsFile.appendToBeforeAll("var oTable_");
		jsFile.appendToBeforeAll(table.getId());
		jsFile.appendToBeforeAll(";");
		logger.debug("BeforeAll : {}", jsFile.getBeforeAll());

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
					configGenerator.getConfig(table,provider.getData(webServiceUrl)));
		}
		// DOM Table
		else {
			jsFile.appendToDataTablesConf(configGenerator.getConfig(table));
		}

		jsFile.appendToDataTablesConf(");");
		logger.debug("DataTablesConf : {}", jsFile.getDataTablesConf());

		// Extra features custom configuration
		// Module FixedHeader
		if (table.getInternalModules().contains(new InternalModule("fixedheader"))) {
			jsFile.appendToBeforeEndDocumentReady("new FixedHeader(oTable_");
			jsFile.appendToBeforeEndDocumentReady(table.getId());
			jsFile.appendToBeforeEndDocumentReady(");");
		}

		// Extra files management
		extraFileManagement(jsFile, table);

		logger.debug("datatables4j.js : {}", jsFile.toString());
		return jsFile.toString();
	}

	private static void extraFileManagement(JavascriptFile jsFile, HtmlTable table) {

		for (ExtraFile file : table.getExtraFiles()) {
			if (JsConstants.BEFOREALL.equals(file.getInclude())) {
				jsFile.appendToBeforeAll(Utils.getFileContentFromClasspath(file.getSrc()));
			} else if (JsConstants.AFTERALL.equals(file.getInclude())) {
				jsFile.appendToAfterAll(Utils.getFileContentFromClasspath(file.getSrc()));
			} else if (JsConstants.AFTERSTARTDOCUMENTEREADY.equals(file.getInclude())) {
				jsFile.appendToAfterStartDocumentReady(Utils.getFileContentFromClasspath(file
						.getSrc()));
			} else if (JsConstants.BEFOREENDDOCUMENTREADY.equals(file.getInclude())) {
				jsFile.appendToBeforeEndDocumentReady(Utils.getFileContentFromClasspath(file
						.getSrc()));
			}
		}

		logger.debug("jsfile : {}", jsFile.toString());
	}
}
