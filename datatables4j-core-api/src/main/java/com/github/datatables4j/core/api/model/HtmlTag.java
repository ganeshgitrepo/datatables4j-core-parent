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

public abstract class HtmlTag {

	protected StringBuffer cssClass;
	protected StringBuffer cssStyle;
	
	public StringBuffer getCssClass() {
		return cssClass;
	}
	public void setCssClass(StringBuffer cssClass) {
		this.cssClass = cssClass;
	}
	public StringBuffer getCssStyle() {
		return cssStyle;
	}
	public void setCssStyle(StringBuffer cssStyle) {
		this.cssStyle = cssStyle;
	}
	public void addCssClass(String cssClass){
		if(this.cssClass == null){
			this.cssClass = new StringBuffer();
		}
		this.cssClass.append(cssClass);
	}
	public void addCssStyle(String cssStyle){
		if(this.cssStyle == null){
			this.cssStyle = new StringBuffer();
		}
		this.cssStyle.append(cssStyle);
	}
}
