package org.datatables4j.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HtmlTable {

	// HTML attributes
	private String id;
	private String cssStyle;
	private String cssClass;
	private Boolean autoWidth;
	private Boolean deferRender;
	private Boolean info;
	private Boolean filterable;
	private Boolean paginate;
	private String paginationStyle;
	private String lengthPaginate;
	private Boolean processing;
	private Boolean sort;
	private Boolean stateSave;

	// Extra features
	private String scrollY;
	private String fixedPosition;

	// Internal attributes
	private HtmlTableProperties properties = new HtmlTableProperties();
	private List<HtmlRow> head = new LinkedList<HtmlRow>();
	private List<HtmlRow> body = new LinkedList<HtmlRow>();
	private List<HtmlRow> foot = new LinkedList<HtmlRow>();
	private Map<String, String> attributes = new HashMap<String, String>();
	private String datasourceUrl;
	private List<Module> modules = new ArrayList<Module>();
	private List<ExtraFile> extraFiles = new ArrayList<ExtraFile>();
	private List<ExtraConf> extraConfs = new ArrayList<ExtraConf>();
	private Boolean cdn;

	public HtmlTable() {
	};

	public HtmlTable(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
		this.attributes.put("id", id);
	}

	public HtmlRow addHeaderRow() {
		HtmlRow row = new HtmlRow();
		this.head.add(row);
		return row;
	}

	public HtmlRow addRow() {
		HtmlRow row = new HtmlRow();
		this.body.add(row);
		return row;
	}

	public HtmlRow addRow(String rowId) {
		HtmlRow row = new HtmlRow(rowId);
		this.body.add(row);
		return row;
	}

	public HtmlTable addRows(HtmlRow... rows) {
		for (HtmlRow row : rows) {
			this.body.add(row);
		}
		return this;
	}

	public void addAttribute(String name, String value) {
		this.attributes.put(name, value);
	}

	public HtmlRow getLastHeaderRow() {
		return ((LinkedList<HtmlRow>) this.head).getLast();
	}

	public HtmlRow getLastRow() {
		return ((LinkedList<HtmlRow>) this.body).getLast();
	}

	public String toHtml() {
		StringBuffer tmpRetval = new StringBuffer();
		tmpRetval.append("<table id=\"");
		tmpRetval.append(this.id);
		tmpRetval.append("\"");

		for (Map.Entry<String, String> entry : this.attributes.entrySet()) {
			tmpRetval.append(" ");
			tmpRetval.append(entry.getKey());
			tmpRetval.append("=\"");
			tmpRetval.append(entry.getValue());
			tmpRetval.append("\"");
		}

		tmpRetval.append(">");
		tmpRetval.append("<thead>");

		for (HtmlRow row : this.head) {
			tmpRetval.append(row.toHtml());
		}
		tmpRetval.append("</thead>");
		tmpRetval.append("<tbody>");

		for (HtmlRow row : this.body) {
			tmpRetval.append(row.toHtml());
		}

		tmpRetval.append("</tbody>");

		if (!this.foot.isEmpty()) {
			tmpRetval.append("<tfoot>");
			for (HtmlRow row : this.foot) {
				tmpRetval.append(row.toHtml());
			}

			tmpRetval.append("</tfoot>");
		}
		tmpRetval.append("</table>");

		return tmpRetval.toString();
	}

	public void registerModule(Module module) {
		this.modules.add(module);
	}

	public String getCssStyle() {
		return cssStyle;
	}

	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
		this.attributes.put("style", cssStyle);
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
		this.attributes.put("class", cssClass);
	}

	public Boolean getAutoWidth() {
		return autoWidth;
	}

	public void setAutoWidth(Boolean autoWidth) {
		this.autoWidth = autoWidth;
	}

	public Boolean getDeferRender() {
		return deferRender;
	}

	public void setDeferRender(Boolean deferRender) {
		this.deferRender = deferRender;
	}

	public Boolean getInfo() {
		return info;
	}

	public void setInfo(Boolean info) {
		this.info = info;
	}

	public Boolean getFilterable() {
		return filterable;
	}

	public void setFilterable(Boolean filterable) {
		this.filterable = filterable;
	}

	public Boolean getPaginate() {
		return paginate;
	}

	public void setPaginate(Boolean paginate) {
		this.paginate = paginate;
	}

	public String getPaginationStyle() {
		return paginationStyle;
	}

	public void setPaginationStyle(String paginationStyle) {
		this.paginationStyle = paginationStyle;
	}

	public String getLengthPaginate() {
		return lengthPaginate;
	}

	public void setLengthPaginate(String lengthPaginate) {
		this.lengthPaginate = lengthPaginate;
	}

	public Boolean getProcessing() {
		return processing;
	}

	public void setProcessing(Boolean processing) {
		this.processing = processing;
	}

	public Boolean getSort() {
		return sort;
	}

	public void setSort(Boolean sort) {
		this.sort = sort;
	}

	public Boolean getStateSave() {
		return stateSave;
	}

	public void setStateSave(Boolean stateSave) {
		this.stateSave = stateSave;
	}

	public String getDatasourceUrl() {
		return datasourceUrl;
	}

	public void setDatasourceUrl(String datasourceUrl) {
		this.datasourceUrl = datasourceUrl;
	}

	public String getScrollY() {
		return scrollY;
	}

	public void setScrollY(String scrollY) {
		this.scrollY = scrollY;
	}

	public List<ExtraFile> getExtraFiles() {
		return extraFiles;
	}

	public void setExtraFiles(List<ExtraFile> extraFiles) {
		this.extraFiles = extraFiles;
	}

	public List<ExtraConf> getExtraConfs() {
		return extraConfs;
	}

	public void setExtraConfs(List<ExtraConf> extraConfs) {
		this.extraConfs = extraConfs;
	}

	public List<Module> getModules() {
		return modules;
	}

	public void setModules(List<Module> modules) {
		this.modules = modules;
	}

	public String getFixedPosition() {
		return fixedPosition;
	}

	public void setFixedPosition(String fixedPosition) {
		this.fixedPosition = fixedPosition;
	}

	public HtmlTableProperties getProperties() {
		return properties;
	}

	public void setProperties(HtmlTableProperties properties) {
		this.properties = properties;
	}

	public Boolean getCdn() {
		return cdn;
	}

	public void setCdn(Boolean cdn) {
		this.cdn = cdn;
	}

}