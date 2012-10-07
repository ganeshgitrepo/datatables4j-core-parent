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
package org.datatables4j.core.feature.ui;

import org.datatables4j.core.api.model.Feature;
import org.datatables4j.core.api.model.HtmlTable;

public class InputFilteringFeature extends Feature {

	@Override
	public String getFeatureName() {
		return "InputFiltering";
	}

	@Override
	public String getFeatureVersion() {
		return "1.0.0";
	}

	@Override
	public void setup(HtmlTable table) {
		beforeAllScript = "var asInitVals_" + table.getId() + " = new Array();";
		beforeEndDocumentReady = 
			"$('#" + table.getId() + " tfoot th').each( function (index, dom) {" +
		        "if($('input', this).length > 0){" +
			        "asInitVals_" + table.getId() + "[index] = $('input', this).val();" +
		        	"$('input', this).keyup(function(){" +
						"oTable_" + table.getId() + ".fnFilter( this.value, index);" +
		        	"});" +
		        	"$('input', this).focus(function(){" +
						"if ( $(this).hasClass('search_init') )" +
				        "{" +
				            "$(this).removeClass('search_init');" +
				            "this.value = '';" +
				        "}" +
				    "});" +
				    "$('input', this).blur(function(){" +
						"if ( this.value == '' )" +
				        "{" +
				            "$(this).addClass('search_init');" +
				            "this.value =  asInitVals_" + table.getId() + "[index];" +
				        "}" +
				    "});" +
				"}" +
		    "});";
	}
}