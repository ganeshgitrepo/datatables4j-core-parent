package org.datatables4j.generator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.jsp.PageContext;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.datatables4j.aggregator.AggregateUtils;
import org.datatables4j.compressor.ResourceCompressorHelper;
import org.datatables4j.configuration.ConfigGenerator;
import org.datatables4j.configuration.MainConf;
import org.datatables4j.datasource.DataProviderHelper;
import org.datatables4j.exception.BadConfigurationException;
import org.datatables4j.exception.CompressionException;
import org.datatables4j.exception.DataNotFoundException;
import org.datatables4j.model.CssResource;
import org.datatables4j.model.ExtraConf;
import org.datatables4j.model.ExtraFile;
import org.datatables4j.model.HtmlTable;
import org.datatables4j.model.JsResource;
import org.datatables4j.model.WebResources;
import org.datatables4j.module.InternalModuleLoader;
import org.datatables4j.util.JsConstants;
import org.datatables4j.util.JsonUtils;
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
	 * TODO
	 */
	private static ConfigGenerator configGenerator;

	/**
	 * TODO
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
	 * @throws CompressionException 
	 * @throws BadConfigurationException 
	 */
	public WebResources generateWebResources(PageContext pageContext, HtmlTable table)
			throws DataNotFoundException, CompressionException, IOException, BadConfigurationException {

		JsResource mainFile = new JsResource("datatables4j.js");
		WebResources webResources = new WebResources();
		
		// TODO transformer configGenerator en singleton
		configGenerator = new ConfigGenerator();
		
		MainConf mainConf = configGenerator.generateConfig(table);
		
		mainFile.appendToBeforeAll("var oTable_");
		mainFile.appendToBeforeAll(table.getId());
		mainFile.appendToBeforeAll(";");
		logger.debug("BeforeAll : {}", mainFile.getBeforeAll());

		// Extra files management
		extraFileManagement(mainFile, table);

		// Internal module management
		InternalModuleLoader.loadModules(mainFile, table, mainConf, webResources);
				
		// DataTables configuration
		mainFile.appendToDataTablesConf("oTable_");
		mainFile.appendToDataTablesConf(table.getId());
		mainFile.appendToDataTablesConf("=$('#");
		mainFile.appendToDataTablesConf(table.getId());
		mainFile.appendToDataTablesConf("').dataTable(");
				
		extraConfManagement(mainConf, table);
		
		// AJAX table
		if (table.getDatasourceUrl() != null) {
			String baseUrl = RequestHelper.getBaseUrl(pageContext);
			String webServiceUrl = baseUrl + table.getDatasourceUrl();
			
			DataProviderHelper providerHelper = new DataProviderHelper();
			
			mainFile.appendToDataTablesConf(
					configGenerator.getConfig(mainConf,providerHelper.getData(table, webServiceUrl)));
		}
		// DOM Table
		else {
			mainFile.appendToDataTablesConf(configGenerator.getConfig(mainConf));
		}
		
		mainFile.appendToDataTablesConf(");");
		
		webResources.getJavascripts().put("datatables4j.js", mainFile);
				
		// Compressor
		if(table.getProperties().isCompressorEnable()){
			compressWebResources(webResources, table);
		}
		
		aggregatorManagement(webResources, table);
				
		return webResources;
	}

	private void aggregatorManagement(WebResources webResources, HtmlTable table){
		if(table.getProperties().isAggregatorEnable()){			
			switch(table.getProperties().getAggregatorMode()){
				case ALL :
					AggregateUtils.aggregateAll(webResources);
					break;
				
				case PLUGINS_JS :
					// TODO
					break;
					
				case PLUGINS_CSS :
					// TODO
					break;
			}
		}
	}
	
	/**
	 * TODO
	 * @param webResources
	 * @param table
	 * @throws BadConfigurationException
	 */
	private void compressWebResources(WebResources webResources, HtmlTable table) throws BadConfigurationException{
		// <script> HTML tag generation
		ResourceCompressorHelper compressorHelper = new ResourceCompressorHelper(table);
		
		for (Entry<String, JsResource> entry : webResources.getJavascripts().entrySet()) {
			entry.getValue().setContent(
					compressorHelper.getCompressedJavascript(entry.getValue().toString()));
		}
		
		for (Entry<String, CssResource> entry : webResources.getStylesheets().entrySet()) {
			entry.getValue().setContent(
					compressorHelper.getCompressedCss(entry.getValue().getContent()));
		}
	}
	
	/**
	 * TODO
	 * @param mainFile
	 * @param table
	 * @throws IOException
	 */
	private void extraFileManagement(JsResource mainFile, HtmlTable table) throws IOException {

		for (ExtraFile file : table.getExtraFiles()) {
			if (JsConstants.BEFOREALL.equals(file.getInsert())) {
				mainFile.appendToBeforeAll(ResourceUtils.getFileContentFromWebapp(file.getSrc()));
			} else if (JsConstants.AFTERALL.equals(file.getInsert())) {
				mainFile.appendToAfterAll(ResourceUtils.getFileContentFromWebapp(file.getSrc()));
			} else if (JsConstants.AFTERSTARTDOCUMENTEREADY.equals(file.getInsert())) {
				mainFile.appendToAfterStartDocumentReady(ResourceUtils
						.getFileContentFromWebapp(file.getSrc()));
			} else if (JsConstants.BEFOREENDDOCUMENTREADY.equals(file.getInsert())) {
				mainFile.appendToBeforeEndDocumentReady(ResourceUtils.getFileContentFromWebapp(file
						.getSrc()));
			}
		}

		// logger.debug("jsfile : {}", jsFile.toString());
	}
	
	/**
	 * TODO
	 * @param mainConf
	 * @param table
	 */
	private void extraConfManagement(MainConf mainConf, HtmlTable table) {

		// Jackson object mapper
		ObjectMapper mapper = new ObjectMapper();
		
//		try {
			Map<String, Object> extraConf = new HashMap<String, Object>();
			Map<String, Object> tmpMap;
			for (ExtraConf conf : table.getExtraConfs()) {
				String confStr = ResourceUtils.getFileContentFromWebapp(conf.getSrc());
				logger.debug("confStr = {}", confStr);
				JsonNode newJson = JsonUtils.convertStringToJsonNode(confStr);
				logger.debug("newJson = {}", newJson);
				JsonNode oldJson = JsonUtils.convertObjectToJsonNode(mainConf);
				logger.debug("oldJson = {}", oldJson);
				
				JsonNode result = JsonUtils.merge(oldJson, newJson);
				logger.debug("result = {}", result);
				
				tmpMap = (Map<String, Object>) JsonUtils.convertJsonNodeToObject(result, MainConf.class);
				extraConf.putAll(tmpMap);
			}
			
			logger.debug("extraConf = {}", extraConf);
			mainConf.putAll(extraConf);
			
//		} catch (JsonParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
