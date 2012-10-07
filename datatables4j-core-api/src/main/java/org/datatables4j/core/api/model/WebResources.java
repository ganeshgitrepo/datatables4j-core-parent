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
package org.datatables4j.core.api.model;

import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class WebResources {

	private Map<String, JsResource> javascripts = new TreeMap<String, JsResource>();
	private Map<String, CssResource> stylesheets = new TreeMap<String, CssResource>();

	public Map<String, JsResource> getJavascripts() {
		return javascripts;
	}

	public void setJavascripts(Map<String, JsResource> javascripts) {
		this.javascripts = javascripts;
	}

	public Map<String, CssResource> getStylesheets() {
		return stylesheets;
	}

	public void setStylesheets(Map<String, CssResource> stylesheets) {
		this.stylesheets = stylesheets;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("JS:");
		for(Entry<String, JsResource> entry : javascripts.entrySet()){
			buffer.append(entry.getKey());
			buffer.append(",");
		}
		buffer.append("|CSS:");
		for(Entry<String, CssResource> entry : stylesheets.entrySet()){
			buffer.append(entry.getKey());
			buffer.append(",");
		}
		return buffer.toString();
	}

	
}
