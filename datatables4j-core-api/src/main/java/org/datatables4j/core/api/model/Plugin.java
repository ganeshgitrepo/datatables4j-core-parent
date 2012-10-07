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
package org.datatables4j.core.api.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Abstract UI plugin.
 * 
 * @author Thibault Duchateau
 */
public abstract class Plugin {

	protected String beforeAllScript;
	protected String afterAllScript;
	protected String afterStartDocumentReady;
	protected String beforeEndDocumentReady;
	protected List<JsResource> jsResources = new LinkedList<JsResource>();
	protected List<CssResource> cssResources = new LinkedList<CssResource>();
	protected List<PluginConf> pluginConfs = new ArrayList<PluginConf>();

	/**
	 * Returns the plugin's name.
	 */
	public abstract String getPluginName();

	/**
	 * Returns the plugin's version.
	 */
	public abstract String getPluginVersion();

	/**
	 * Setup the plugin (web resources, DataTables configuration).
	 * <p>
	 * The HtmlTable object is available if a particular configuration is
	 * needed.
	 * </p>
	 * 
	 * @param table
	 *            The HTML table.
	 */
	public abstract void setup(HtmlTable table);

	public String getBeforeAllScript() {
		return beforeAllScript;
	}

	public void setBeforeAllScript(String beforeAllScript) {
		this.beforeAllScript = beforeAllScript;
	}

	public String getAfterAllScript() {
		return afterAllScript;
	}

	public void setAfterAllScript(String afterAllScript) {
		this.afterAllScript = afterAllScript;
	}

	public String getAfterStartDocumentReady() {
		return afterStartDocumentReady;
	}

	public void setAfterStartDocumentReady(String afterStartDocumentReady) {
		this.afterStartDocumentReady = afterStartDocumentReady;
	}

	public String getBeforeEndDocumentReady() {
		return beforeEndDocumentReady;
	}

	public void setBeforeEndDocumentReady(String beforeEndDocumentReady) {
		this.beforeEndDocumentReady = beforeEndDocumentReady;
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

	public void addJsResource(JsResource resource) {
		this.jsResources.add(resource);
	}

	public void addCssResource(CssResource resource) {
		this.cssResources.add(resource);
	}
	
	public void addPluginConf(PluginConf pluginConf) {
		this.pluginConfs.add(pluginConf);
	}

	public List<PluginConf> getPluginConfs() {
		return this.pluginConfs;
	}
}