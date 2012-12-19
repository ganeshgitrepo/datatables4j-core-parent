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
 * Abstract class that defines an extension.
 * 
 * @author Thibault Duchateau
 */
public abstract class AbstractExtension {

	// TODO mettre a jour les accesseurs
	protected StringBuffer beforeAll;
	// TODO mettre a jour les accesseurs
	protected StringBuffer afterAll;
	protected StringBuffer beforeStartDocumentReady;
	protected StringBuffer afterStartDocumentReady;
	protected StringBuffer beforeEndDocumentReady;
	protected List<JsResource> jsResources;
	protected List<CssResource> cssResources;
	protected List<Configuration> confs;
	protected Boolean appendRandomNumber = false;
	protected AbstractConfigurationGenerator configGenerator;
	protected String function;
	
	public enum Type {
		MAIN, FEATURE, PLUGIN, THEME
	}
	
	public abstract Type getType();
	
	public String getFunction(){
		return null;
	}
	
	/**
	 * Returns the feature's name.
	 */
	public abstract String getName();

	/**
	 * Returns the feature's version.
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
	
	public StringBuffer getBeforeAllScript() {
		return beforeAll;
	}
	
	public StringBuffer getAfterAllScript() {
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
		if(this.beforeAll == null){
			this.beforeAll = new StringBuffer();
		}
		this.beforeAll.append(beforeAll);
	}

	public void appendToBeforeStartDocumentReady(String beforeStartDocumentReady) {
		if(this.beforeStartDocumentReady == null){
			this.beforeStartDocumentReady = new StringBuffer();
		}
		this.beforeStartDocumentReady.append(beforeStartDocumentReady);
	}
	
	public void appendToAfterStartDocumentReady(String afterStartDocumentReady) {
		if(this.afterStartDocumentReady == null){
			this.afterStartDocumentReady = new StringBuffer();
		}
		this.afterStartDocumentReady.append(afterStartDocumentReady);
	}

	public void appendToBeforeEndDocumentReady(String beforeEndDocumentReady) {
		if(this.beforeEndDocumentReady == null){
			this.beforeEndDocumentReady = new StringBuffer();
		}
		this.beforeEndDocumentReady.append(beforeEndDocumentReady);
	}

	public void appendToAfterAll(String afterAll) {
		if(this.afterAll == null){
			this.afterAll = new StringBuffer();
		}
		this.afterAll.append(afterAll);
	}
}