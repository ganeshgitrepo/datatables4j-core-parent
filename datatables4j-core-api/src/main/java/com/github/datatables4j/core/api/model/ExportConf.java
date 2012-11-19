package com.github.datatables4j.core.api.model;

/**
 * TODO
 *
 * @author Thibault Duchateau
 */
public class ExportConf {

	private String id;
	private String fileName;
	private String type;
	private String label;
	private String cssStyle;
	private String cssClass;
	private String position;
	private Boolean includeHeader;
	private String area;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getCssStyle() {
		return cssStyle;
	}
	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}
	public String getCssClass() {
		return cssClass;
	}
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public Boolean getIncludeHeader() {
		return includeHeader;
	}
	public void setIncludeHeader(Boolean includeHeader) {
		this.includeHeader = includeHeader;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
}