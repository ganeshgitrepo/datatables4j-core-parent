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
import javax.servlet.jsp.tagext.TagSupport;

import org.datatables4j.core.properties.PropertiesLoader;

/**
 * Allow to override the DataTables4j global properties locally.
 *
 * @author Thibault Duchateau
 */
public class PropTag extends TagSupport {
	private static final long serialVersionUID = -3453884184847355817L;

	// Tag attributes
	private String name;
	private String value;
	
	/**
	 * TODO
	 */
	public int doStartTag() throws JspException {
		return SKIP_BODY;
	}
	
	/**
	 * TODO
	 * ajouter un test sur la nature du parent
	 */
	public int doEndTag() throws JspException {
		
		// Get parent tag
		AbstractTableTag parent = (AbstractTableTag) getParent();
		
		// Evaluate the tag only once using the isFirstRow method
		if(parent.isFirstRow()){
			
			PropertiesLoader properties = PropertiesLoader.getInstance();
			
			// Override the existing properties with the new one
			properties.setProperty(name, value);
		}
		
		return EVAL_PAGE;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}