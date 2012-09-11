package org.datatables4j.configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.datatables4j.model.HtmlColumn;
import org.datatables4j.model.HtmlTable;
import org.datatables4j.model.InternalModule;
import org.datatables4j.util.DTConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ConfigGenerator {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(ConfigGenerator.class);
	
	public String getConfig(HtmlTable table) {
		Map<String, Object> config = this.generateConfig(table);
		return this.convertConfigToJson(config);
	}

	public String getConfig(HtmlTable table, Map<String, Object> data) {

		Map<String, Object> config = this.generateConfig(table);
		config.putAll(data);
		return this.convertConfigToJson(config);
	}

	private String convertConfigToJson(Map<String, Object> config){
		
		logger.debug("Converting configuration to JSON ...");
		ObjectMapper mapper = new ObjectMapper();
		
		StringBuffer tmpRetval = new StringBuffer();
		// write JSON to a file
		try {
			tmpRetval.append(mapper.writeValueAsString(config));
			// System.out.println(mapper.writeValueAsString(aoColumns));
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.debug("Convertion OK");
		logger.debug("Result : {}", tmpRetval);
		
		// return tmpRetval.toString();
		return tmpRetval.toString();
	}
	
	
	private Map<String, Object> generateConfig(HtmlTable table) {

		logger.debug("Generating DataTables configuration ..");
		
		// Main configuration object
		MainConf mainConf = new MainConf();
		
		// Columns configuration
		AoColumns aoColumns = new AoColumns();
		Map<String, Object> tmp = null;
		for (HtmlColumn column : table.getLastHeaderRow().getColumns()) {
			tmp = new HashMap<String, Object>();
			tmp.put(DTConstants.DT_SORTABLE, column.getSortable());
			if(column.getProperty() != null){
				tmp.put(DTConstants.DT_DATA, column.getProperty());				
			}
			aoColumns.getContent().add(tmp);
//			aoColumns.addValue("bSortable", column.getSortable());
		}
		mainConf.put(DTConstants.DT_AOCOLUMNS, aoColumns.getContent());
			
		if (table.getAutoWidth() != null) {
			mainConf.put(DTConstants.DT_AUTO_WIDTH, table.getAutoWidth());
		}
		if (table.getDeferRender() != null) {
			mainConf.put(DTConstants.DT_DEFER_RENDER, table.getDeferRender());
		}
		if (table.getFilterable() != null) {
			mainConf.put(DTConstants.DT_FILTER, table.getFilterable());
		}
		if (table.getInfo() != null) {
			mainConf.put(DTConstants.DT_INFO, table.getInfo());
		}
		if (table.getPaginate() != null) {
			mainConf.put(DTConstants.DT_PAGINATE, table.getPaginate());
		}
		if (table.getLengthPaginate() != null) {
			mainConf.put(DTConstants.DT_LENGTH_CHANGE, table.getLengthPaginate());
		}
		if (StringUtils.isNotBlank(table.getPaginationStyle())) {
			mainConf.put(DTConstants.DT_PAGINATION_TYPE, table.getPaginationStyle());
		}
		if (table.getProcessing() != null) {
			mainConf.put(DTConstants.DT_PROCESSING, table.getProcessing());
		}
		if (table.getSort() != null) {
			mainConf.put(DTConstants.DT_SORT, table.getSort());
		}
		if (table.getStateSave() != null) {
			mainConf.put(DTConstants.DT_STATE_SAVE, table.getStateSave());
		}

		// sDom
		if(table.getInternalModules().contains(new InternalModule("scroller"))){
			mainConf.put(DTConstants.DT_DOM, "lfrtipS");
			if(table.getScrollY() != null){
				mainConf.put(DTConstants.DT_SCROLLY, table.getScrollY());				
			}
		}
		else{
			mainConf.put(DTConstants.DT_DOM, "lfrtip");			
		}
		
		logger.debug("DataTables configuration generated");
		
		return mainConf;
	}
}
