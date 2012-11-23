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
 * POJO representing a HTML <strong>A</strong> tag (link). 
 *
 * @author Thibault Duchateau
 */
public class HtmlLink extends HtmlTag {

	private String id;
	private String href;
	private String label;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	/**
	 * Render the tag in HTML.
	 * 
	 * @return a StringBuffer containing the HTML code.
	 */
	public StringBuffer toHtml(){
		StringBuffer html = new StringBuffer();
		html.append("<a");
		
		if(this.id != null){
			html.append(" id=\"");
			html.append(this.id);
			html.append("\"");			
		}
		
		if(this.cssClass != null){
			html.append(" class=\"");
			html.append(this.cssClass);
			html.append("\"");			
		}
		
		if(this.cssStyle != null){
			html.append(" style=\"");
			html.append(this.cssStyle);
			html.append("\"");			
		}
		
		if(this.href != null){
			html.append(" href=\"");
			html.append(this.href);
			html.append("\"");
		}
		
		html.append(">");
		html.append(this.label);
		html.append("</a>");
		
		return html;
	}
}