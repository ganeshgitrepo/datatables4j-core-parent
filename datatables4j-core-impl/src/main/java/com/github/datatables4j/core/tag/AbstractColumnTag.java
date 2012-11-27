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
package com.github.datatables4j.core.tag;

import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;

import com.github.datatables4j.core.api.constants.FilterType;
import com.github.datatables4j.core.api.model.HtmlColumn;
import com.github.datatables4j.core.api.model.HtmlRow;

/**
 * Abstract class which contains :<br />
 * <ul>
 * <li>all the boring technical stuff needed by Java tags (getters and setters
 * for all Column tag attributes)</li>
 * <li>helper methods used to manipulate the columns</li>
 * </ul>
 * 
 * @author Thibault Duchateau
 */
public abstract class AbstractColumnTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;

	// Tag attributes
	protected String title;
	protected String property;
	protected String cssStyle;
	protected String cssCellStyle;
	protected String cssClass;
	protected String cssCellClass;
	protected Boolean sortable = true;
	protected String sortDirection;
	protected String sortInit;
	protected Boolean filterable = false;
	protected String filterType;
	protected String filterCssClass = "";
	protected String filterPlaceholder = "";
	
	/**
	 * Add a column to the table.
	 * 
	 * @param isHeader
	 * @param content
	 */
	protected void addColumn(Boolean isHeader, String content){
		
		// Init the column
		HtmlColumn column = new HtmlColumn(isHeader, content);

		// Common configuration (between header and non-header columns)
		column.setSortable(this.sortable);
		
		AbstractTableTag parent = (AbstractTableTag) getParent();
		
		// Non-header columns
		if(!isHeader){
			if(StringUtils.isNotBlank(this.cssCellClass)){
				column.setCssCellClass(this.cssCellClass);
			}
			if(StringUtils.isNotBlank(this.cssCellStyle)){
				column.setCssCellStyle(this.cssCellStyle);
			}

			parent.getTable().getLastRow().addColumn(column);			
		}
		// Header columns
		else{
			if(StringUtils.isNotBlank(this.cssClass)){
				column.setCssClass(this.cssClass);
			}
			if(StringUtils.isNotBlank(this.cssStyle)){
				column.setCssStyle(this.cssStyle);
			}

			column.setSortDirection(this.sortDirection);
			column.setSortInit(this.sortInit);
			column.setFilterable(this.filterable);
			column.setFilterType(this.filterType);
			column.setFilterCssClass(this.filterCssClass);
			column.setFilterPlaceholder(this.filterPlaceholder);
			
			parent.getTable().getLastHeaderRow().addColumn(column);
			
			// Specific column filtering enabled
			if(this.filterable){
				
				HtmlRow footerRow = parent.getTable().getLastFooterRow();
				HtmlColumn footerColumn = null;
				FilterType filterTypeChoice = null;
				
				// Default : INPUT filtering
				if(StringUtils.isBlank(this.filterType)){
					filterTypeChoice = FilterType.INPUT;
				}
				else{
					// INPUT filtering
					if(FilterType.INPUT.toString().equals(this.filterType)){
						filterTypeChoice = FilterType.INPUT;				
					}
					// SELECT filtering
					else if(FilterType.SELECT.toString().equals(this.filterType)){
						filterTypeChoice = FilterType.SELECT;
					}
				}
				
				switch(filterTypeChoice){
					case INPUT :
						String inputContent = StringUtils.isNotBlank(this.filterPlaceholder) ? this.filterPlaceholder : this.title;					
						footerColumn = new HtmlColumn("<input type=\"text\" name=\"search-engine\" value=\"" + inputContent + "\" class=\"search_init " + this.filterCssClass + "\">");
						break;
					
					case SELECT :
						footerColumn = new HtmlColumn().setCssClass("select-filtering");	
						break;
						
					default :
						footerColumn = new HtmlColumn();
						break;
				}
				
				footerRow.addColumn(footerColumn);
			}
			// Add a column cell in the footer anyway
			else{
				parent.getTable().getLastFooterRow().addColumn(new HtmlColumn());
			}
		}
	}
	
	/** Getters and setters */
	
	public void setId(String id) {
		this.id = id;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public Boolean getSortable() {
		return sortable;
	}

	public void setSortable(Boolean sortable) {
		this.sortable = sortable;
	}

	public String getCssCellStyle() {
		return cssCellStyle;
	}

	public void setCssCellStyle(String cssCellStyle) {
		this.cssCellStyle = cssCellStyle;
	}

	public String getCssCellClass() {
		return cssCellClass;
	}

	public void setCssCellClass(String cssCellClass) {
		this.cssCellClass = cssCellClass;
	}

	public Boolean getFilterable() {
		return filterable;
	}

	public void setFilterable(Boolean filterable) {
		this.filterable = filterable;
	}

	public String getFilterType() {
		return filterType;
	}

	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}

	public String getFilterCssClass() {
		return filterCssClass;
	}

	public void setFilterCssClass(String filterCssClass) {
		this.filterCssClass = filterCssClass;
	}

	public String getFilterPlaceholder() {
		return filterPlaceholder;
	}

	public void setFilterPlaceholder(String filterPlaceholder) {
		this.filterPlaceholder = filterPlaceholder;
	}
	
	public String getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}
	
	public String getSortInit() {
		return sortInit;
	}

	public void setSortInit(String sortInit) {
		this.sortInit = sortInit;
	}
}