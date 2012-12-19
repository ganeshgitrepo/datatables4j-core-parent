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

import java.lang.reflect.InvocationTargetException;

import javax.servlet.jsp.JspException;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.datatables4j.core.api.model.DisplayType;
import com.github.datatables4j.core.api.model.HtmlColumn;

/**
 * Tag used to generate a HTML table's column.
 * 
 * @author Thibault Duchateau
 */
public class ColumnTag extends AbstractColumnTag {

	private static final long serialVersionUID = -8928415196287387948L;

	// Logger
	private static Logger logger = LoggerFactory.getLogger(ColumnTag.class);

	/**
	 * TODO
	 */
	public int doStartTag() throws JspException {
		TableTag parent = (TableTag) getParent();

		if (parent.getLoadingType() == "AJAX") {
			return EVAL_PAGE;
		} else if (parent.getLoadingType() == "DOM") {
			if (getBodyContent() != null) {
				return EVAL_BODY_BUFFERED;
			} else {
				if (property != null) {

					// AbstractTableTag parent = (AbstractTableTag) getParent();

					try {
						this.addColumn(
								false,
								PropertyUtils.getNestedProperty(parent.getCurrentObject(),
										this.property).toString());

					} catch (IllegalAccessException e) {
						logger.error("Unable to get the value for the given property {}",
								this.property);
						throw new JspException(e);
					} catch (InvocationTargetException e) {
						logger.error("Unable to get the value for the given property {}",
								this.property);
						throw new JspException(e);
					} catch (NoSuchMethodException e) {
						logger.error("Unable to get the value for the given property {}",
								this.property);
						throw new JspException(e);
					}
				}
				return EVAL_PAGE;
			}
		}

		// Never reached
		return SKIP_BODY;
	}

	/**
	 * TODO
	 */
	public int doEndTag() throws JspException {
		TableTag parent = (TableTag) getParent();

		if ("DOM".equals(parent.getLoadingType())) {

			if (parent.isFirstRow()) {

				this.addColumn(true, this.title);
			}
			return EVAL_PAGE;
		} else if ("AJAX".equals(parent.getLoadingType())) {

			HtmlColumn column = new HtmlColumn(true, this.title);
			
			// All display types are added
			for(DisplayType type : DisplayType.values()){
				column.getEnabledDisplayTypes().add(type);
			}
			column.setProperty(this.property);
			column.setSortable(this.sortable);
			parent.getTable().getLastHeaderRow().addColumn(column);
			parent.getTable().getLastFooterRow().addColumn(new HtmlColumn());
			return EVAL_PAGE;
		}

		return SKIP_PAGE;
	}

	/**
	 * TODO
	 */
	public int doAfterBody() throws JspException {

		TableTag parent = (TableTag) getParent();
		if ("DOM".equals(parent.getLoadingType()) && getBodyContent() != null) {			
			String bodyString = getBodyContent().getString().trim().replaceAll("[\n\r]", "");
			this.addColumn(false, bodyString);
		}

		return EVAL_PAGE;
	}
}