package org.datatables4j.javascript;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JavascriptFile {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(JavascriptFile.class);
		
	private StringBuffer beforeAll = new StringBuffer();
	private StringBuffer afterStartDocumentReady = new StringBuffer();
	private StringBuffer beforeEndDocumentReady = new StringBuffer();
	private StringBuffer afterAll = new StringBuffer();
	private StringBuffer dataTablesConf = new StringBuffer();

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
	
	public String toString(){
		StringBuffer tmpRetval = new StringBuffer();
		
		tmpRetval.append(this.beforeAll);
		tmpRetval.append("$(document).ready(function(){");
		tmpRetval.append(this.afterStartDocumentReady);
		tmpRetval.append(this.dataTablesConf);
		tmpRetval.append(this.beforeEndDocumentReady);
		tmpRetval.append("});");
		tmpRetval.append(this.afterAll);
		
		return tmpRetval.toString();
	}
}