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
package com.github.datatables4j.core.jsp.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.datatables4j.core.api.constants.ExportConstants;
import com.github.datatables4j.core.api.export.ExportConf;
import com.github.datatables4j.core.api.export.ExportLinkPosition;
import com.github.datatables4j.core.api.export.ExportType;

/**
 * Tag which allows to configure an export type for the current table.
 * 
 * @author Thibault Duchateau
 */
public class ExportTag extends TagSupport {
	private static final long serialVersionUID = -3453884184847355817L;

	// Logger
	private static Logger logger = LoggerFactory.getLogger(ExportTag.class);

	// Tag attributes
	private String fileName;
	private String type;
	private String label;
	private String cssStyle;
	private String cssClass;
	private ExportLinkPosition position;
	private Boolean includeHeader;
	private String area;// TODO
	private Boolean autoSize;

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
		if (parent.isFirstRow()) {

			ExportType exportType = null;
			try {
				exportType = ExportType.valueOf(type);
			} catch (IllegalArgumentException e) {
				logger.error("The export cannot be activated for the table {}. ", parent.getTable()
						.getId());
				logger.error("{} is not a valid value among {}", type, ExportType.values());
				throw new JspException(e);
			}

			ExportConf conf = new ExportConf();

			conf.setFileName(fileName != null ? fileName : "export");
			conf.setType(type != null ? type.toUpperCase() : "CSV");
			conf.setLabel(label != null ? label : type.toUpperCase());
			conf.setCssClass(cssClass != null ? new StringBuffer(cssClass) : new StringBuffer());
			conf.setCssStyle(cssStyle != null ? new StringBuffer(cssStyle) : new StringBuffer());
			conf.setPosition(position != null ? position : ExportLinkPosition.TOP_MIDDLE);
			conf.setIncludeHeader(includeHeader != null ? includeHeader : true);
			conf.setArea(area != null ? area : "ALL");
			conf.setAutoSize(autoSize != null ? autoSize : false);
			conf.setUrl(parent.getTable().getCurrentUrl() + "?" + ExportConstants.DT4J_EXPORT_TYPE
					+ "=" + exportType.getUrlParameter() + "&" + ExportConstants.DT4J_EXPORT_ID
					+ "=" + parent.getTable().getId());

			parent.getTable().getExportConfMap().put(exportType, conf);

			logger.debug("Export conf added to table {}", conf);
		}

		return EVAL_PAGE;
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
	
	public Boolean getAutoSize() {
		return autoSize;
	}

	public void setAutoSize(Boolean autoSize) {
		this.autoSize = autoSize;
	}
}