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
package com.github.datatables4j.core.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.datatables4j.core.api.constants.DTConstants;
import com.github.datatables4j.core.api.generator.DataTableConfigurationGenerator;
import com.github.datatables4j.core.api.model.DisplayType;
import com.github.datatables4j.core.api.model.HtmlColumn;
import com.github.datatables4j.core.api.model.HtmlTable;

/**
 * Class in charge of Column Filtering widget configuration generation.
 *
 * @author Thibault Duchateau
 */
public class ColumnFilteringGenerator extends DataTableConfigurationGenerator {

    // Logger
    private static Logger logger = LoggerFactory.getLogger(ColumnFilteringGenerator.class);

    /**
     * If no custom config is specified with table attributes, DataTables
     * will internally use default one.
     *
     * @param table The POJO containing the HTML table.
     * @return MainConf The main configuration file associated with the HTML
     *         table.
     */
    public Map<String, Object> generate(HtmlTable table) {

        logger.debug("Generating Column Filtering configuration ..");

        // Main configuration object
        Map<String, Object> conf = new HashMap<String, Object>();

        // Columns configuration
        Map<String, Object> tmp = null;
        List<Map<String, Object>> aoColumnsContent = new ArrayList<Map<String, Object>>();
        for (HtmlColumn column : table.getLastHeaderRow().getColumns()) {
        	
        	if(column.getEnabledDisplayTypes().contains(DisplayType.HTML)){
        		tmp = new HashMap<String, Object>();
        		
        		if(column.isFilterable() && column.getFilterType() != null){
        		
        			switch(column.getFilterType()){
					case INPUT:
						tmp.put(DTConstants.DT_FILTER_TYPE, "text");
						break;
					case NUMBER:
						tmp.put(DTConstants.DT_FILTER_TYPE, "number");
						break;
					case SELECT:
						tmp.put(DTConstants.DT_FILTER_TYPE, "select");        				
						break;
					default:
						tmp.put(DTConstants.DT_FILTER_TYPE, "text");
						break;
        			}
        		}
        		else{
        			tmp.put(DTConstants.DT_FILTER_TYPE, "null");
        		}
        		
        		aoColumnsContent.add(tmp);
        	}
        }
        conf.put(DTConstants.DT_AOCOLUMNS, aoColumnsContent);

        logger.debug("Column filtering configuration generated");

        return conf;
    }
}
