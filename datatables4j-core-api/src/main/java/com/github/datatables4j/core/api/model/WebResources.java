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

import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class WebResources {

	private Map<String, JsResource> javascripts = new TreeMap<String, JsResource>();
	private Map<String, CssResource> stylesheets = new TreeMap<String, CssResource>();

	public Map<String, JsResource> getJavascripts() {
		return javascripts;
	}

	public void setJavascripts(Map<String, JsResource> javascripts) {
		this.javascripts = javascripts;
	}

	public Map<String, CssResource> getStylesheets() {
		return stylesheets;
	}

	public void setStylesheets(Map<String, CssResource> stylesheets) {
		this.stylesheets = stylesheets;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("JS:");
		for(Entry<String, JsResource> entry : javascripts.entrySet()){
			buffer.append(entry.getKey());
			buffer.append(",");
		}
		buffer.append("|CSS:");
		for(Entry<String, CssResource> entry : stylesheets.entrySet()){
			buffer.append(entry.getKey());
			buffer.append(",");
		}
		return buffer.toString();
	}

	
}
