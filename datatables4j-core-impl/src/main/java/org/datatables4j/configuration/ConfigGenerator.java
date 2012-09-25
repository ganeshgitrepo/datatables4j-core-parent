package org.datatables4j.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.datatables4j.constants.DTConstants;
import org.datatables4j.model.HtmlColumn;
import org.datatables4j.model.HtmlTable;
import org.datatables4j.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ConfigGenerator {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(ConfigGenerator.class);
	
	public String getConfig(Map<String, Object> mainConf) {
		return JsonUtils.convertObjectToJsonString(mainConf);
	}

	public String getConfig(Map<String, Object> mainConf, Map<String, Object> data) {
		mainConf.putAll(data);
		return JsonUtils.convertObjectToJsonString(mainConf);
	}
	
	/**
	 * If no custom config is specified with table attributs, DataTables
	 * will internally use default one.
	 * 
	 * @param table
	 *            The POJO containing the HTML table.
	 * @return MainConf The main configuration file associated with the HTML
	 *         table.
	 */
	public Map<String, Object> generateConfig(HtmlTable table) {

		logger.debug("Generating DataTables configuration ..");
		
		// Main configuration object
		Map<String, Object> mainConf = new HashMap<String, Object>();
		
		// Columns configuration
		Map<String, Object> tmp = null;
		List<Map<String, Object>> aoColumnsContent = new ArrayList<Map<String, Object>>(); 
		for (HtmlColumn column : table.getLastHeaderRow().getColumns()) {
			tmp = new HashMap<String, Object>();
			tmp.put(DTConstants.DT_SORTABLE, column.getSortable());
			if(column.getProperty() != null){
				tmp.put(DTConstants.DT_DATA, column.getProperty());				
			}
			aoColumnsContent.add(tmp);
		}
		mainConf.put(DTConstants.DT_AOCOLUMNS, aoColumnsContent);
			
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
		mainConf.put(DTConstants.DT_DOM, "lfrtip");
		
		logger.debug("DataTables configuration generated");
		
		return mainConf;
	}
}
