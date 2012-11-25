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

/**
 * POJO that stores an export type configuration.
 * 
 * @author Thibault Duchateau
 */
public class ExportConf {

	private String fileName;
	private String type;
	private String label;
	private StringBuffer cssStyle;
	private StringBuffer cssClass;
	private ExportLinkPosition position;
	private Boolean includeHeader;
	private String area;
	private String url;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public StringBuffer getCssStyle() {
		return cssStyle;
	}

	public void setCssStyle(StringBuffer cssStyle) {
		this.cssStyle = cssStyle;
	}

	public StringBuffer getCssClass() {
		return cssClass;
	}

	public void setCssClass(StringBuffer cssClass) {
		this.cssClass = cssClass;
	}

	public ExportLinkPosition getPosition() {
		return position;
	}

	public void setPosition(ExportLinkPosition position) {
		this.position = position;
	}

	public Boolean getIncludeHeader() {
		return includeHeader;
	}

	public void setIncludeHeader(Boolean includeHeader) {
		this.includeHeader = includeHeader;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "ExportConf [fileName=" + fileName + ", type=" + type + ", label=" + label
				+ ", cssStyle=" + cssStyle + ", cssClass=" + cssClass + ", position=" + position
				+ ", includeHeader=" + includeHeader + ", area=" + area + ", url=" + url + "]";
	}

}