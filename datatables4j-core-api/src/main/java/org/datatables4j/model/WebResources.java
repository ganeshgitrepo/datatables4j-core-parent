package org.datatables4j.model;

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
