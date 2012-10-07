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
package org.datatables4j.core.tag;

import javax.servlet.jsp.JspException;

/**
 * TODO
 *
 * @author Thibault Duchateau
 */
public class ColumnTag extends AbstractColumnTag {

	private static final long serialVersionUID = -8928415196287387948L;

	/**
	 * TODO
	 */
	public int doStartTag() throws JspException {
		return processDoStartTag();
	}

	/**
	 * TODO
	 */
	public int doEndTag() throws JspException {
		return processDoEndTag();
	}

	/**
	 * TODO
	 */
	public int doAfterBody() throws JspException {

		TableTag parent = (TableTag) getParent();
		if (parent.getLoadingType() == "AJAX") {
			
		}
		else{
			if (getBodyContent() != null) {
				String bodyString = getBodyContent().getString();
				this.addColumn(false, bodyString);
			} else {
				// System.out.println("BODY VIDE !");
			}
		}

		return EVAL_PAGE;
	}
}
