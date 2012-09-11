package org.datatables4j.model;

public class ExternalModule {

	private String name;
	private String initCode;
	private String locationInJavascript;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInitCode() {
		return initCode;
	}
	public void setInitCode(String locationInClasspath) {
		this.initCode = locationInClasspath;
	}
	public String getLocationInJavascript() {
		return locationInJavascript;
	}
	public void setLocationInJavascript(String locationInJavascript) {
		this.locationInJavascript = locationInJavascript;
	}
	
	
}
