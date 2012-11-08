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

import com.github.datatables4j.core.api.constants.InsertMode;
import com.github.datatables4j.core.api.model.ExtraFile;

/**
 * TODO
 *
 * @author Thibault Duchateau
 */
public class ExtraFileTag extends TagSupport {
	private static final long serialVersionUID = -287813095911386884L;

	// Logger
	private static Logger logger = LoggerFactory.getLogger(AbstractTableTag.class);

	// Tag attributes
	private String src;
	private InsertMode insert = InsertMode.BEFOREALL;
	
	public int doStartTag() throws JspException {
		return SKIP_BODY;
	}
	
	public int doEndTag() throws JspException {
		
		AbstractTableTag parent = (AbstractTableTag) getParent();
		
		if(parent.isFirstRow()){
			parent.getTable().getExtraFiles().add(new ExtraFile(getRealSource(this.src), this.insert));
		}
		return EVAL_PAGE;
	}
	
	private String getRealSource(String tmpSource){
		logger.debug("getRealSource = {}", pageContext.getServletContext().getRealPath(tmpSource));
		return pageContext.getServletContext().getRealPath(tmpSource);
	}
	
	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public InsertMode getInsert() {
		return insert;
	}

	public void setInsert(InsertMode insert) {
		this.insert = insert;
	}
}
