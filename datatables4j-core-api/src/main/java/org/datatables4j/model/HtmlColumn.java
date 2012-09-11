package org.datatables4j.model;

import java.util.HashMap;
import java.util.Map;

public class HtmlColumn {

	private Boolean isHeaderColumn = true;
	private String id;
	private String cssClass;
	private String cssCellClass;
	private String cssStyle;
	private String cssCellStyle;
	private String content;
	private Boolean sortable;
	private Map<String, String> attributes = new HashMap<String, String>();
	private String property;

	public HtmlColumn() {
	};

	public HtmlColumn(String content) {
		this.content = content;
	}

	public HtmlColumn(Boolean isHeader, String content) {
		this.isHeaderColumn = isHeader;
		this.content = content;
	}
	
	public String getCssClass() {
		return cssClass;
	}

	public HtmlColumn setCssClass(String cssClass) {
		this.cssClass = cssClass;
		this.attributes.put("class", cssClass);
		return this;
	}

	public String getCssStyle() {
		return cssStyle;
	}

	public HtmlColumn setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
		this.attributes.put("style", cssStyle);
		return this;
	}

	public Boolean getIsHeaderColumn() {
		return isHeaderColumn;
	}

	public HtmlColumn setIsHeaderColumn(Boolean isHeaderColumn) {
		this.isHeaderColumn = isHeaderColumn;
		return this;
	}

	public String getContent() {
		return content;
	}

	public HtmlColumn setContent(String content) {
		this.content = content;
		return this;
	}

	public String toHtml() {
		StringBuffer tmpRetval = new StringBuffer();
		if (this.isHeaderColumn) {
			tmpRetval.append("<th");
		} else {
			tmpRetval.append("<td");
		}
		
		for(Map.Entry<String, String> entry : this.attributes.entrySet()){
			tmpRetval.append(" ");
			tmpRetval.append(entry.getKey());
			tmpRetval.append("=\"");
			tmpRetval.append(entry.getValue());
			tmpRetval.append("\"");
		}
		
		tmpRetval.append(">");
		tmpRetval.append(content);
		if (this.isHeaderColumn) {
			tmpRetval.append("</th>");
		} else {
			tmpRetval.append("</td>");
		}

		return tmpRetval.toString();
	}

	public String getCssCellClass() {
		return this.cssCellClass;
	}

	public HtmlColumn setCssCellClass(String cssCellClass) {
		this.cssCellClass = cssCellClass;
		this.attributes.put("class", cssCellClass);
		return this;
	}

	public String getCssCellStyle() {
		return this.cssCellStyle;
	}

	public HtmlColumn setCssCellStyle(String cssCellStyle) {
		this.cssCellStyle = cssCellStyle;
		this.attributes.put("style", cssCellStyle);
		return this;
	}

	public Boolean getSortable() {
		return this.sortable;
	}

	public void setSortable(Boolean sortable) {
		this.sortable = sortable;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
