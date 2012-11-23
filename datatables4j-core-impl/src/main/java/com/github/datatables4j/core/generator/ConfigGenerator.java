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
package com.github.datatables4j.core.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.datatables4j.core.api.constants.DTConstants;
import com.github.datatables4j.core.api.model.HtmlColumn;
import com.github.datatables4j.core.api.model.HtmlTable;

/**
 * Class in charge of DataTables configuration generation.
 *
 * @author Thibault Duchateau
 */
public class ConfigGenerator {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(ConfigGenerator.class);
	
	/**
	 * If no custom config is specified with table attributes, DataTables
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
			
			// Sortable
			tmp.put(DTConstants.DT_SORTABLE, column.getSortable());
			
			//
			if(column.getProperty() != null){
				tmp.put(DTConstants.DT_DATA, column.getProperty());				
			}

			// Sorting direction
			if(StringUtils.isNotBlank(column.getSortDirection())){
				tmp.put(DTConstants.DT_SORT_DIR, column.getSortDirection().trim().toLowerCase().split(","));
			}
			
			aoColumnsContent.add(tmp);
		}
		mainConf.put(DTConstants.DT_AOCOLUMNS, aoColumnsContent);
		
		// Column sorting initialisation
		List<Object> aaSortingtmp = null;
		List<Object> aaSortingContent = new ArrayList<Object>();
		Integer columnIndex = 0;
		for (HtmlColumn column : table.getLastHeaderRow().getColumns()) {
			
			// Sorting direction
			if(StringUtils.isNotBlank(column.getSortInit())){
				aaSortingtmp = new ArrayList<Object>();
				aaSortingtmp.add(columnIndex);
				aaSortingtmp.add(column.getSortInit());
				aaSortingContent.add(aaSortingtmp);
			}
			
			columnIndex++;
		}
		if(!aaSortingContent.isEmpty()){
			mainConf.put(DTConstants.DT_SORT_INIT, aaSortingContent);			
		}
		
		if(table.getLabels() != null){
			tmp = new HashMap<String, Object>();
			tmp.put(DTConstants.DT_URL, table.getLabels());
			mainConf.put(DTConstants.DT_LANGUAGE, tmp);
		}
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
		if (table.getLengthChange() != null) {
			mainConf.put(DTConstants.DT_LENGTH_CHANGE, table.getLengthChange());
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
		if (table.getJqueryUI() != null) {
			mainConf.put(DTConstants.DT_JQUERYUI, table.getJqueryUI());
		}
		
		mainConf.put(DTConstants.DT_DOM, "lfrtip");
		
		logger.debug("DataTables configuration generated");
		
		return mainConf;
	}
}
