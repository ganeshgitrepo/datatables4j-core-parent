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
package com.github.datatables4j.core.feature.ui;

import com.github.datatables4j.core.api.model.Feature;
import com.github.datatables4j.core.api.model.HtmlTable;
import com.github.datatables4j.core.api.model.JsResource;

public class SelectFilteringFeature extends Feature {

	@Override
	public String getFeatureName() {
		return "SelectFiltering";
	}

	@Override
	public String getFeatureVersion() {
		return "1.0.0";
	}

	@Override
	public void setup(HtmlTable table) {
		addJsResource(new JsResource("filtering/filterWithSelect.js"));
		beforeEndDocumentReady = 
				"/* Add a select menu for each TH element in the table footer */" +
				"$('#" + table.getId() + " tfoot th').each( function ( i ) {" +
					"if($(this).hasClass('select-filtering')) {" +
						"this.innerHTML = fnCreateSelect( oTable_" + table.getId() + ".fnGetColumnData(i) );" +
						"$('select', this).change( function () {" +
							"oTable_" + table.getId() + ".fnFilter( $(this).val(), i );" +
						"} );" +
					"}" +
    			"} );";
	}
}
