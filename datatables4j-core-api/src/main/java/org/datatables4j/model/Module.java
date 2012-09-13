package org.datatables4j.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Abstract UI module.
 * 
 * @author Thibault Duchateau
 */
public abstract class Module {

	protected String beforeAllScript;
	protected String afterAllScript;
	protected String afterStartDocumentReady;
	protected String beforeEndDocumentReady;
	protected List<JsResource> jsResources = new LinkedList<JsResource>();
	protected List<CssResource> cssResources = new LinkedList<CssResource>();
	protected List<ModuleConf> moduleConfs = new ArrayList<ModuleConf>();

	/**
	 * Returns the module name.
	 */
	public abstract String getModuleName();

	/**
	 * Returns the module version.
	 */
	public abstract String getModuleVersion();

	/**
	 * Setup the module (web resources, DataTables configuration).
	 * <p>
	 * The HtmlTable object is available in case of particular configuration is
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

	public void addModuleConf(ModuleConf moduleConf) {
		this.moduleConfs.add(moduleConf);
	}

	public List<ModuleConf> getModuleConfs() {
		return this.moduleConfs;
	}
}