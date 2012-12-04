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
import java.util.List;

public class HtmlRow {

	private String id;
	private String cssClass;
	private List<HtmlColumn> columns = new ArrayList<HtmlColumn>();
	private Object rowData;

	public HtmlRow() {

	}

	public HtmlRow(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public List<HtmlColumn> getColumns() {
		return this.columns;
	}

	public void setColumns(List<HtmlColumn> columns) {
		this.columns = columns;
	}

	public HtmlColumn addHeaderColumn(String columnContent) {
		HtmlColumn newColumn = new HtmlColumn(true, columnContent);
		this.columns.add(newColumn);
		return newColumn;
	}

	public HtmlColumn addColumn(HtmlColumn column) {
		this.columns.add(column);
		return column;
	}

	public HtmlColumn addColumn(String columnContent) {
		HtmlColumn newColumn = new HtmlColumn(false, columnContent);
		this.columns.add(newColumn);
		return newColumn;
	}

	public HtmlRow addHeaderColumns(String... columns) {
		for (String columnContent : columns) {
			this.columns.add(new HtmlColumn(true, columnContent));
		}
		return this;
	}

	public List<HtmlColumn> getHeaderColumns() {
		List<HtmlColumn> retval = new ArrayList<HtmlColumn>();
		for (HtmlColumn column : columns) {
			if (column.isHeaderColumn()) {
				retval.add(column);
			}
		}
		return retval;
	}

	public HtmlRow addColumns(String... columns) {
		for (String columnContent : columns) {
			this.columns.add(new HtmlColumn(false, columnContent));
		}
		return this;
	}

	public String toHtml() {

		StringBuffer tmpRetval = new StringBuffer("<tr");
		if (this.id != null) {
			tmpRetval.append(" id=\"");
			tmpRetval.append(this.id);
			tmpRetval.append("\"");
		}
		tmpRetval.append(">");

		for (HtmlColumn column : this.columns) {
			if (column.getEnabledDisplayTypes() != null
					&& (column.getEnabledDisplayTypes().contains(DisplayType.ALL) || column
							.getEnabledDisplayTypes().contains(DisplayType.HTML))) {
				tmpRetval.append(column.toHtml());
			}
		}
		tmpRetval.append("</tr>");

		return tmpRetval.toString();
	}

	public Object getRowData() {
		return rowData;
	}

	public void setRowData(Object rowData) {
		this.rowData = rowData;
	}
}
