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
package com.github.datatables4j.core.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.datatables4j.core.api.constants.ExportConstants;
import com.github.datatables4j.core.api.model.ExportLinkPosition;
import com.github.datatables4j.core.api.model.ExportConf;
import com.github.datatables4j.core.api.model.ExportType;

/**
 * Tag which allows to configure the table export.
 *
 * @author Thibault Duchateau
 */
public class ExportTag extends TagSupport {
	private static final long serialVersionUID = -3453884184847355817L;

	// Logger
	private static Logger logger = LoggerFactory.getLogger(ExportTag.class);
		
	// Tag attributes
	private String id;
	private String fileName;
	private String type;
	private String label;
	private String cssStyle;
	private String cssClass;
	private ExportLinkPosition position;
	private Boolean includeHeader;
	private String area;// TODO
	
	/**
	 * An ExportTag has no body but we test here that it is in the right place.
	 */
	public int doStartTag() throws JspException {

		if (!(getParent() instanceof AbstractTableTag)) {
			throw new JspException("ExportTag must be inside AbstractTableTag");
		}

		return SKIP_BODY;
	}
	
	/**
	 * Process the tag updating table properties.
	 */
	public int doEndTag() throws JspException {
		
		// Get parent tag
		AbstractTableTag parent = (AbstractTableTag) getParent();

		// Evaluate the tag only once using the parent's isFirstRow method
		if(parent.isFirstRow()){
			
			ExportConf conf = new ExportConf();
			
			conf.setId(id != null ? id : "");
			conf.setFileName(fileName != null ? fileName : "export");
			conf.setType(type != null ? type.toUpperCase() : "CSV");
			conf.setLabel(label != null ? label : type.toUpperCase());
			conf.setCssClass(cssClass != null ? new StringBuffer(cssClass) : new StringBuffer());
			conf.setCssStyle(cssStyle != null ? new StringBuffer(cssStyle) : new StringBuffer());
			conf.setPosition(position != null ? position : ExportLinkPosition.TOP_MIDDLE);
			conf.setIncludeHeader(includeHeader != null ? includeHeader : true);
			conf.setArea(area != null ? area : "ALL");
			conf.setUrl(parent.getTable().getCurrentUrl() + "?" + ExportConstants.DT4J_EXPORT + "=1&" + ExportConstants.DT4J_EXPORT_TYPE + "=" + ExportType.valueOf(conf.getType()).getUrlParameter());
			
			parent.getTable().getExportConfMap().put(ExportType.valueOf(type), conf);
			
			logger.debug("Export conf added to table {}", conf);
		}
		
		return EVAL_PAGE;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getCssStyle() {
		return cssStyle;
	}

	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
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
}