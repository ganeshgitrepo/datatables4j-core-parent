package org.datatables4j.model;

import org.datatables4j.constants.ResourceType;

public class JsResource extends DataTablesResource {

	private String name;
	private String content;
	private ResourceType type;
	private StringBuffer beforeAll = new StringBuffer();
	private StringBuffer afterStartDocumentReady = new StringBuffer();
	private StringBuffer beforeEndDocumentReady = new StringBuffer();
	private StringBuffer afterAll = new StringBuffer();
	private StringBuffer dataTablesConf = new StringBuffer();

	public JsResource() {
	}

	public JsResource(String name) {
		this.name = name;
	}

	public JsResource(ResourceType type, String name) {
		this.type = type;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return this.content != null ? this.content : this.toString();
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

	public String toString() {
		StringBuffer tmpRetval = new StringBuffer();

		tmpRetval.append(this.beforeAll);
		tmpRetval.append("$(document).ready(function(){");
		tmpRetval.append(this.afterStartDocumentReady);
		tmpRetval.append(this.dataTablesConf);
		tmpRetval.append(this.beforeEndDocumentReady);
		tmpRetval.append("});");
		tmpRetval.append(this.afterAll);
		this.content = tmpRetval.toString();
		return tmpRetval.toString();
	}

	public ResourceType getType() {
		return type;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}
}
