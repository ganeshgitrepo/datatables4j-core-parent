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

import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.github.datatables4j.core.api.constants.FilterType;
import com.github.datatables4j.core.api.model.HtmlColumn;
import com.github.datatables4j.core.api.model.HtmlRow;

/**
 * TODO
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
	 * 
	 * @return
	 */
	protected int processDoStartTag(){
		TableTag parent = (TableTag) getParent();
		if (parent.getLoadingType() == "AJAX") {
			return EVAL_PAGE;
		} 
		else if (parent.getLoadingType() == "DOM") {
			if (getBodyContent() != null) {
				// System.out.println("BODYCONTENT EXISTE");
				return EVAL_BODY_BUFFERED;
			} else {
				// System.out.println("BODYCONTENT VIDE");
				if (property != null) {

					// AbstractTableTag parent = (AbstractTableTag) getParent();

					try {
						this.addColumn(
								false,
								PropertyUtils.getNestedProperty(parent.getCurrentObject(),
										this.property).toString());

					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
	 * @return
	 */
	protected int processDoEndTag(){
		TableTag parent = (TableTag) getParent();
		if (parent.getLoadingType() == "AJAX") {

			HtmlColumn column = new HtmlColumn(true, this.title);
			column.setProperty(this.property);
			column.setSortable(this.sortable);
			parent.getTable().getLastHeaderRow().addColumn(column);
			parent.getTable().getLastFooterRow().addColumn(new HtmlColumn());
			return EVAL_PAGE;
		} else if (parent.getLoadingType() == "DOM") {
			if (parent.isFirstRow()) {

				this.addColumn(true, this.title);
			}
			return EVAL_PAGE;
		}
		return SKIP_PAGE;
	}
	
	/**
	 * TODO
	 * @param isHeader
	 * @param content
	 */
	protected void addColumn(Boolean isHeader, String content){
		
		HtmlColumn column = new HtmlColumn(isHeader, content);

		column.setSortable(this.sortable);
		
		AbstractTableTag parent = (AbstractTableTag) getParent();
		
		if(!isHeader){
			if(StringUtils.isNotBlank(this.cssCellClass)){
				column.setCssCellClass(this.cssCellClass);
			}
			if(StringUtils.isNotBlank(this.cssCellStyle)){
				column.setCssCellStyle(this.cssCellStyle);
			}

			parent.getTable().getLastRow().addColumn(column);			
		}
		// Column in 1st row
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