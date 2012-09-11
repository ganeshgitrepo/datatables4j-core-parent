package org.datatables4j.model;

import java.util.ArrayList;
import java.util.List;

public class HtmlRow {

	private String id;
	private String cssClass;
	private List<HtmlColumn> columns = new ArrayList<HtmlColumn>();
	private Object rowData;
	
	public HtmlRow() {

	}

	public HtmlRow(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public List<HtmlColumn> getColumns() {
		return this.columns;
	}

	public void setColumns(List<HtmlColumn> columns) {
		this.columns = columns;
	}

	
	public HtmlColumn addHeaderColumn(String columnContent){
		HtmlColumn newColumn = new HtmlColumn(true, columnContent);
		this.columns.add(newColumn);
		return newColumn;
	}
	
	public HtmlColumn addColumn(HtmlColumn column){
		this.columns.add(column);
		return column;
	}
	
	public HtmlColumn addColumn(String columnContent) {
		HtmlColumn newColumn = new HtmlColumn(false, columnContent);
		this.columns.add(newColumn);
		return newColumn;
	}

	public HtmlRow addHeaderColumns(String... columns) {
		for (String columnContent : columns) {
			this.columns.add(new HtmlColumn(true, columnContent));
		}
		return this;
	}
	
	public HtmlRow addColumns(String... columns) {
		for (String columnContent : columns) {
			this.columns.add(new HtmlColumn(false, columnContent));
		}
		return this;
	}

	public String toHtml() {

		StringBuffer tmpRetval = new StringBuffer("<tr");
		if(this.id != null){
			tmpRetval.append(" id=\"");
			tmpRetval.append(this.id);
			tmpRetval.append("\"");
		}
		tmpRetval.append(">");
		
		for (HtmlColumn column : this.columns) {
			tmpRetval.append(column.toHtml());
		}
		tmpRetval.append("</tr>");

		return tmpRetval.toString();
	}

	public Object getRowData() {
		return rowData;
	}

	public void setRowData(Object rowData) {
		this.rowData = rowData;
	}
}
