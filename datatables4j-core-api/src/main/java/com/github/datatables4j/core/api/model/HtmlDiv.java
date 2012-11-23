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

public class HtmlDiv extends HtmlTag {

	private String id;
	private StringBuffer content = new StringBuffer();
	
	public HtmlDiv(){
		
	}
	
	public HtmlDiv(String id){
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public StringBuffer getContent() {
		return content;
	}
	public void setContent(StringBuffer content) {
		this.content = content;
	}
	public void addContent(StringBuffer contentToAdd){
		this.content.append(contentToAdd); 
	}
	public StringBuffer toHtml(){
		StringBuffer html = new StringBuffer();
		html.append("<div");
		
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
		
		html.append(">");
		html.append(this.content);
		html.append("</div>");
		return html;
	}
}
