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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.github.datatables4j.core.api.exception.BadConfigurationException;
import com.github.datatables4j.core.api.generator.AbstractConfigurationGenerator;

/**
 * <p>
 * Abstract superclass for all extensions. An extension can be a plugin (e.g.
 * Scroller, ColReorder), a feature (e.g. Bootstrap pagination type, filtering
 * add-on) or a theme (e.g. Bootstrap 2 theme).
 * <p>
 * An extension can be composed of :
 * <ul>
 * <li>one or more JsResource, i.e. Javascript code externalized in a file</li>
 * <li>one or more CssResource, i.e. CSS code externalized in a file</li>
 * <li>one or more Configuration, i.e. one or more specific DataTables
 * parameters that will be used during the DataTables initialization</li>
 * <li>an AbstractConfigurationGenerator if the extension needs its proper
 * configuration generator. The one used for the main DataTables configuration
 * is the {@link MainGenerator}. You can also take a look at the
 * {@link ColumnFilteringGenerator} to see the configuration generated for the
 * Column Filtering add-on.</li>
 * <li>a potential Javascript function name that will be called after DataTables
 * initialization. <br>
 * Example : columnFilter <blockquote>
 * 
 * <pre>
 * oTable_myTableId = $('#myTableId').dataTable(oTable_myTableId_params).columnFilter({...});
 * </pre>
 * 
 * </blockquote></li>
 * <li>specific Javascript snippets to add in the main JS resource, i.e. the
 * resource that contains the DataTables initilization Javascript code. You can
 * add snippet at multiple locations in this file thanks to the following
 * attributes :
 * <ul>
 * <li>beforeAll</li>
 * <li>beforeStartDocumentReady</li>
 * <li>afterStartDocumentReady</li>
 * <li>beforeEndDocumentReady</li>
 * <li>afterAll</li>
 * </ul>
 * These attributes can be visualized in the following Javascript snippet :
 * <blockquote>
 * 
 * <pre>
 * => <b>BEFOREALL</b>
 * var oTable_tableId;
 * var oTable_tableId_params = {...};
 * => <b>BEFORESTARTDOCUMENTREADY</b>
 * $(document).ready(function(){
 *    => <b>AFTERSTARTDOCUMENTREADY</b>
 *    oTable_myTableId = $('#myTableId').dataTable(oTable_myTableId_params);
 *    => <b>BEFOREENDDOCUMENTREADY</b>
 * });
 * => <b>AFTERALL</b>
 * </pre>
 * 
 * </blockquote></li>
 * </ul>
 * 
 * @author Thibault Duchateau
 * @since 0.7.1
 */
public abstract class AbstractExtension {

	protected StringBuffer beforeAll;
	protected StringBuffer afterAll;
	protected StringBuffer beforeStartDocumentReady;
	protected StringBuffer afterStartDocumentReady;
	protected StringBuffer beforeEndDocumentReady;
	protected List<JsResource> jsResources;
	protected List<CssResource> cssResources;
	protected List<Configuration> confs;
	protected AbstractConfigurationGenerator configGenerator;
	protected Boolean appendRandomNumber = false;
	protected String function;

	/**
	 * Returns the extension's name.
	 */
	public abstract String getName();

	/**
	 * Returns the extension's version.
	 */
	public abstract String getVersion();

	/**
	 * Set the extension up.
	 * <p>
	 * The HtmlTable object is available if a particular configuration is
	 * needed.
	 * </p>
	 * 
	 * @param table
	 *            The HTML table.
	 */
	public abstract void setup(HtmlTable table) throws BadConfigurationException;

	public StringBuffer getBeforeAll() {
		return beforeAll;
	}

	public StringBuffer getAfterAll() {
		return afterAll;
	}

	public StringBuffer getAfterStartDocumentReady() {
		return afterStartDocumentReady;
	}

	public StringBuffer getBeforeEndDocumentReady() {
		return beforeEndDocumentReady;
	}

	public List<JsResource> getJsResources() {
		return jsResources;
	}

	public void setJsResources(List<JsResource> jsResources) {
		this.jsResources = jsResources;
	}

	public List<CssResource> getCssResources() {
		return cssResources;
	}

	public void setCssResources(List<CssResource> cssResources) {
		this.cssResources = cssResources;
	}

	public List<Configuration> getConfs() {
		return confs;
	}

	public void setConfs(List<Configuration> confs) {
		this.confs = confs;
	}

	public void addJsResource(JsResource resource) {
		if (this.jsResources == null) {
			this.jsResources = new LinkedList<JsResource>();
		}
		this.jsResources.add(resource);
	}

	public void addCssResource(CssResource resource) {
		if (this.cssResources == null) {
			this.cssResources = new LinkedList<CssResource>();
		}
		this.cssResources.add(resource);
	}

	public void addConfiguration(Configuration conf) {
		if (this.confs == null) {
			this.confs = new ArrayList<Configuration>();
		}
		this.confs.add(conf);
	}

	public AbstractConfigurationGenerator getConfigGenerator() {
		return configGenerator;
	}

	public void setConfigGenerator(AbstractConfigurationGenerator configGenerator) {
		this.configGenerator = configGenerator;
	}

	public Boolean getAppendRandomNumber() {
		return appendRandomNumber;
	}

	public void setAppendRandomNumber(Boolean appendRandomNumber) {
		this.appendRandomNumber = appendRandomNumber;
	}

	public void appendToBeforeAll(String beforeAll) {
		if (this.beforeAll == null) {
			this.beforeAll = new StringBuffer();
		}
		this.beforeAll.append(beforeAll);
	}

	public void appendToBeforeStartDocumentReady(String beforeStartDocumentReady) {
		if (this.beforeStartDocumentReady == null) {
			this.beforeStartDocumentReady = new StringBuffer();
		}
		this.beforeStartDocumentReady.append(beforeStartDocumentReady);
	}

	public void appendToAfterStartDocumentReady(String afterStartDocumentReady) {
		if (this.afterStartDocumentReady == null) {
			this.afterStartDocumentReady = new StringBuffer();
		}
		this.afterStartDocumentReady.append(afterStartDocumentReady);
	}

	public void appendToBeforeEndDocumentReady(String beforeEndDocumentReady) {
		if (this.beforeEndDocumentReady == null) {
			this.beforeEndDocumentReady = new StringBuffer();
		}
		this.beforeEndDocumentReady.append(beforeEndDocumentReady);
	}

	public void appendToAfterAll(String afterAll) {
		if (this.afterAll == null) {
			this.afterAll = new StringBuffer();
		}
		this.afterAll.append(afterAll);
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}
}