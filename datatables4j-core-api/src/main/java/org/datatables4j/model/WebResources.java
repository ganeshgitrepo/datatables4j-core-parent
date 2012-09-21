package org.datatables4j.model;

import java.util.Map;
import java.util.TreeMap;

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

}
