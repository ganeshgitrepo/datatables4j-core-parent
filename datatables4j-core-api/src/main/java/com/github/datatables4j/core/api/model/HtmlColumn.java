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
package com.github.datatables4j.core.api.model;

import java.util.HashMap;
import java.util.Map;

public class HtmlColumn {

	private Boolean isHeaderColumn = true;
	private String id;
	private String cssClass;
	private String cssCellClass;
	private String cssStyle;
	private String cssCellStyle;
	private String content = "";
	private Boolean sortable;
	private String sortDirection;
	private String sortInit;
	private Map<String, String> attributes = new HashMap<String, String>();
	private String property;
	private Boolean filterable = false;
	private String filterType;
	private String filterCssClass;
	private String filterPlaceholder;
	
	public HtmlColumn() {
	};

	public HtmlColumn(String content) {
		this.content = content;
	}

	public HtmlColumn(Boolean isHeader, String content) {
		this.isHeaderColumn = isHeader;
		this.content = content;
	}
	
	public String getCssClass() {
		return cssClass;
	}

	public HtmlColumn setCssClass(String cssClass) {
		this.cssClass = cssClass;
		this.attributes.put("class", cssClass);
		return this;
	}

	public String getCssStyle() {
		return cssStyle;
	}

	public HtmlColumn setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
		this.attributes.put("style", cssStyle);
		return this;
	}

	public Boolean isHeaderColumn() {
		return isHeaderColumn;
	}

	public HtmlColumn setIsHeaderColumn(Boolean isHeaderColumn) {
		this.isHeaderColumn = isHeaderColumn;
		return this;
	}

	public String getContent() {
		return content;
	}

	public HtmlColumn setContent(String content) {
		this.content = content;
		return this;
	}

	public String toHtml() {
		StringBuffer tmpRetval = new StringBuffer();
		if (this.isHeaderColumn) {
			tmpRetval.append("<th");
		} else {
			tmpRetval.append("<td");
		}
		
		for(Map.Entry<String, String> entry : this.attributes.entrySet()){
			tmpRetval.append(" ");
			tmpRetval.append(entry.getKey());
			tmpRetval.append("=\"");
			tmpRetval.append(entry.getValue());
			tmpRetval.append("\"");
		}
		
		tmpRetval.append(">");
		tmpRetval.append(content);
		if (this.isHeaderColumn) {
			tmpRetval.append("</th>");
		} else {
			tmpRetval.append("</td>");
		}

		return tmpRetval.toString();
	}

	public String getCssCellClass() {
		return this.cssCellClass;
	}

	public HtmlColumn setCssCellClass(String cssCellClass) {
		this.cssCellClass = cssCellClass;
		this.attributes.put("class", cssCellClass);
		return this;
	}

	public String getCssCellStyle() {
		return this.cssCellStyle;
	}

	public HtmlColumn setCssCellStyle(String cssCellStyle) {
		this.cssCellStyle = cssCellStyle;
		this.attributes.put("style", cssCellStyle);
		return this;
	}

	public Boolean getSortable() {
		return this.sortable;
	}

	public void setSortable(Boolean sortable) {
		this.sortable = sortable;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getFilterable() {
		return filterable;
	}

	public void setFilterable(Boolean filterable) {
		this.filterable = filterable;
	}

	public String getFilterType() {
		return filterType;
	}

	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}

	public String getFilterCssClass() {
		return filterCssClass;
	}

	public void setFilterCssClass(String filterCssClass) {
		this.filterCssClass = filterCssClass;
	}

	public String getFilterPlaceholder() {
		return filterPlaceholder;
	}

	public void setFilterPlaceholder(String filterPlaceholder) {
		this.filterPlaceholder = filterPlaceholder;
	}

	public String getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}

	public String getSortInit() {
		return sortInit;
	}

	public void setSortInit(String sortInit) {
		this.sortInit = sortInit;
	}
}