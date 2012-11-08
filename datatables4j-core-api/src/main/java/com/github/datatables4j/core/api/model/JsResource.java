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

import com.github.datatables4j.core.api.constants.ResourceType;

/**
 * TODO
 *
 * @author Thibault Duchateau
 */
public class JsResource  {

	private String name;
	private String content;
	private StringBuffer beforeAll = new StringBuffer();
	private StringBuffer beforeStartDocumentReady = new StringBuffer();
	private StringBuffer afterStartDocumentReady = new StringBuffer();
	private StringBuffer beforeEndDocumentReady = new StringBuffer();
	private StringBuffer afterAll = new StringBuffer();
	private StringBuffer dataTablesConf = new StringBuffer();
	private String tableId;
	private ResourceType type;

	public JsResource() {
	}

	public JsResource(String name) {
		this.name = name;
	}

	public JsResource(ResourceType type, String name) {
		this.type = type;
		this.name = name;
	}
	
	public JsResource(ResourceType type, String name, String tableId) {
		this.type = type;
		this.name = name;
		this.tableId = tableId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		
		StringBuffer retval = new StringBuffer();
		
		switch(type){
		case MAIN : 
			
			retval.append(this.beforeAll);
			
			retval.append("var oTable_");
			retval.append(tableId);
			retval.append(";");
			
			retval.append("var oTable_");
			retval.append(tableId);
			retval.append("_params = ");
			retval.append(this.dataTablesConf);
			retval.append(";");
			
			retval.append(this.beforeStartDocumentReady);
			
			retval.append("$(document).ready(function(){");
			retval.append(this.afterStartDocumentReady);
			
			retval.append("oTable_");
			retval.append(this.tableId);
			retval.append("=$('#");
			retval.append(this.tableId);
			retval.append("').dataTable(oTable_");
			retval.append(tableId);
			retval.append("_params);");
					
			retval.append(this.beforeEndDocumentReady);
			retval.append("});");		
			
			retval.append(this.afterAll);
			
			break;
			
		case PLUGIN :
			retval.append(this.content);
			
			break;
		
		case AGGREGATE :
			retval.append(this.content);
			break;
			
		default :
			retval.append(this.content);
			break;
		}
		
		return retval.toString();
	}

	public void setContent(String content) {
		this.content = content;
	}

	public StringBuffer getBeforeAll() {
		return beforeAll;
	}

	public void appendToBeforeAll(String beforeAll) {
		this.beforeAll.append(beforeAll);
	}

	public StringBuffer getBeforeStartDocumentReady() {
		return beforeStartDocumentReady;
	}

	public void appendToBeforeStartDocumentReady(String beforeStartDocumentReady) {
		this.beforeStartDocumentReady.append(beforeStartDocumentReady);
	}
	
	public StringBuffer getAfterStartDocumentReady() {
		return afterStartDocumentReady;
	}

	public void appendToAfterStartDocumentReady(String afterStartDocumentReady) {
		this.afterStartDocumentReady.append(afterStartDocumentReady);
	}

	public StringBuffer getBeforeEndDocumentReady() {
		return beforeEndDocumentReady;
	}

	public void appendToBeforeEndDocumentReady(String beforeEndDocumentReady) {
		this.beforeEndDocumentReady.append(beforeEndDocumentReady);
	}

	public StringBuffer getAfterAll() {
		return afterAll;
	}

	public void appendToAfterAll(String afterAll) {
		this.afterAll.append(afterAll);
	}

	public StringBuffer getDataTablesConf() {
		return dataTablesConf;
	}

	public void appendToDataTablesConf(String dataTablesConf) {
		this.dataTablesConf.append(dataTablesConf);
	}

	public ResourceType getType() {
		return type;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
}
