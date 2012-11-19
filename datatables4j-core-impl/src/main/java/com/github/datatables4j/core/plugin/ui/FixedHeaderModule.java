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
package com.github.datatables4j.core.plugin.ui;

import com.github.datatables4j.core.api.constants.DTConstants;
import com.github.datatables4j.core.api.model.HtmlTable;
import com.github.datatables4j.core.api.model.JsResource;
import com.github.datatables4j.core.api.model.Plugin;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Java implementation of the DataTables FixedHeader plugin.
 *
 * @author Thibault Duchateau
 * @see <a href="http://datatables.net/extras/fixedheader/">Reference</a>
 */
public class FixedHeaderModule extends Plugin {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPluginName() {
	return "FixedHeader";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPluginVersion() {
	return "2.0.6";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setup(HtmlTable table) {

	Map<String, Object> specificConfObj = getSpecificCongiguration(table);
	String specificConfStr = null;
	if (!specificConfObj.isEmpty()) {
	    specificConfStr = JSONValue.toJSONString(specificConfObj);
	    beforeEndDocumentReady = "new FixedHeader(oTable_" + table.getId() + "," + specificConfStr + ");";
	} else {
	    beforeEndDocumentReady = "new FixedHeader(oTable_" + table.getId() + ");";
	}

	addJsResource(new JsResource("datatables.fixedheader.min.js"));
    }

    /**
     * Depending on the attributes, the FixedHeader object may need a JSON
     * object as configuration.
     *
     * @param table The HTML table.
     * @return Map<String, Object> Map of property used by the FixedHeader
     *         plugin.
     */
    private Map<String, Object> getSpecificCongiguration(HtmlTable table) {
	Map<String, Object> conf = new HashMap<String, Object>();

	// fixedPosition attribute (default "top")
	if (StringUtils.isNotBlank(table.getFixedPosition())) {
	    if (table.getFixedPosition().equals("bottom")) {
		conf.put("bottom", true);
	    } else if (table.getFixedPosition().equals("right")) {
		conf.put("right", true);
	    } else if (table.getFixedPosition().equals("left")) {
		conf.put("left", true);
	    } else {
		beforeEndDocumentReady = "new FixedHeader(oTable_" + table.getId() + ");";
	    }
	} else {
	    conf.put("top", true);
	}

	// offsetTop attribute
	if (table.getFixedOffsetTop() != null) {
	    conf.put(DTConstants.DT_OFFSETTOP, table.getFixedOffsetTop());
	}

	return conf;
    }
}
