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
package com.github.datatables4j.core.api.model;

import com.github.datatables4j.core.api.constants.ResourceType;

/**
 * POJO that symbolizes a JS file.
 *
 * @author Thibault Duchateau
 */
public class JsResource  {

	private static final String INDENTATION = "   ";
	private String name;
	private String content;
	private StringBuffer beforeAll;
	private StringBuffer beforeStartDocumentReady;
	private StringBuffer afterStartDocumentReady;
	private StringBuffer beforeEndDocumentReady;
	private StringBuffer afterAll;
	private StringBuffer dataTablesConf;
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
			
			if(this.beforeAll != null){
				retval.append(this.beforeAll);
				retval.append(";\n");
			}
			
			retval.append("var oTable_");
			retval.append(tableId);
			retval.append(";\n");
			
			retval.append("var oTable_");
			retval.append(tableId);
			retval.append("_params = ");
			retval.append(this.dataTablesConf);
			retval.append(";\n");
			
			if(this.beforeStartDocumentReady != null){
				retval.append(this.beforeStartDocumentReady);				
			}
			
			retval.append("$(document).ready(function(){\n");
			if(this.afterStartDocumentReady != null){
				retval.append("\n");
				retval.append(INDENTATION);
				retval.append(this.afterStartDocumentReady);
			}
			retval.append(INDENTATION);
			retval.append("oTable_");
			retval.append(this.tableId);
			retval.append(" = $('#");
			retval.append(this.tableId);
			retval.append("').dataTable(oTable_");
			retval.append(tableId);
			retval.append("_params);");
								
			if(this.beforeEndDocumentReady != null){
				retval.append("\n");
				retval.append(INDENTATION);
				retval.append(this.beforeEndDocumentReady);				
			}
			
			retval.append("\n});");		
			
			if(this.afterAll != null){
				retval.append("\n");
				retval.append(this.afterAll);
			}
			
			break;
			
		case PLUGIN :
			retval.append(this.content);
			
			break;
		
		case AGGREGATE :
			retval.append(this.content);
			break;
		
		case MINIMIFIED :
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
		if(this.beforeAll == null){
			this.beforeAll = new StringBuffer();
		}
		this.beforeAll.append(beforeAll);
	}

	public StringBuffer getBeforeStartDocumentReady() {
		return beforeStartDocumentReady;
	}

	public void appendToBeforeStartDocumentReady(String beforeStartDocumentReady) {
		if(this.beforeStartDocumentReady == null){
			this.beforeStartDocumentReady = new StringBuffer();
		}
		this.beforeStartDocumentReady.append(beforeStartDocumentReady);
	}
	
	public StringBuffer getAfterStartDocumentReady() {
		return afterStartDocumentReady;
	}

	public void appendToAfterStartDocumentReady(String afterStartDocumentReady) {
		if(this.afterStartDocumentReady == null){
			this.afterStartDocumentReady = new StringBuffer();
		}
		this.afterStartDocumentReady.append(afterStartDocumentReady);
	}

	public StringBuffer getBeforeEndDocumentReady() {
		return beforeEndDocumentReady;
	}

	public void appendToBeforeEndDocumentReady(String beforeEndDocumentReady) {
		if(this.beforeEndDocumentReady == null){
			this.beforeEndDocumentReady = new StringBuffer();
		}
		this.beforeEndDocumentReady.append(beforeEndDocumentReady);
	}

	public StringBuffer getAfterAll() {
		return afterAll;
	}

	public void appendToAfterAll(String afterAll) {
		if(this.afterAll == null){
			this.afterAll = new StringBuffer();
		}
		this.afterAll.append(afterAll);
	}

	public StringBuffer getDataTablesConf() {
		return dataTablesConf;
	}

	public void appendToDataTablesConf(String dataTablesConf) {
		if(this.dataTablesConf == null){
			this.dataTablesConf = new StringBuffer();
		}
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
