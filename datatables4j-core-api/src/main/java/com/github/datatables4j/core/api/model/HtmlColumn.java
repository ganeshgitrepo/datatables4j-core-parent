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
package com.github.datatables4j.core.api.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	private List<DisplayType> enabledDisplayTypes = new ArrayList<DisplayType>();
	
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

	public List<DisplayType> getEnabledDisplayTypes() {
		return enabledDisplayTypes;
	}

	public void setEnabledDisplayTypes(List<DisplayType> enabledDisplayTypes) {
		this.enabledDisplayTypes = enabledDisplayTypes;
	}
}