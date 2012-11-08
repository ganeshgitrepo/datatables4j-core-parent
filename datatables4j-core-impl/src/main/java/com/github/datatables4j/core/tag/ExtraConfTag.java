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


import com.github.datatables4j.core.api.model.ExtraConf;
import com.github.datatables4j.core.util.RequestHelper;

/**
 * TODO
 *
 * @author Thibault Duchateau
 */
public class ExtraConfTag extends TagSupport {

	private static final long serialVersionUID = 7656056513635184779L;

	// Tag attributes
	private String src;

	public int doStartTag() throws JspException {
		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {

		AbstractTableTag parent = (AbstractTableTag) getParent();

		if (parent.isFirstRow()) {
			parent.getTable().getExtraConfs().add(new ExtraConf(getLocation(this.src)));
		}
		return EVAL_PAGE;
	}

	private String getLocation(String src){
		return RequestHelper.getBaseUrl(pageContext) + src;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}
}