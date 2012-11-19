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

import java.lang.reflect.InvocationTargetException;

import javax.servlet.jsp.JspException;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

			String bodyString = getBodyContent().getString();
			this.addColumn(false, bodyString);
		}

		return EVAL_PAGE;
	}
}