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
package com.github.datatables4j.core.api.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HtmlTable {

	// HTML attributes
	private String domId;
	private String cssStyle;
	private String cssClass;
	private Boolean autoWidth;
	private Boolean deferRender;
	private Boolean info;
	private Boolean filterable;
	private Boolean paginate;
	private String paginationStyle;
	private Boolean lengthChange;
	private Boolean processing;
	private Boolean sort;
	private Boolean stateSave;
	private String labels;
	private Boolean cdn;
	private Boolean jqueryUI;
	
	// Extra features
	private String scrollY;
	private String fixedPosition;
	private Integer fixedOffsetTop;

	// Internal attributes
	private List<HtmlRow> head = new LinkedList<HtmlRow>();
	private List<HtmlRow> body = new LinkedList<HtmlRow>();
	private List<HtmlRow> foot = new LinkedList<HtmlRow>();
	private TableProperties tableProperties = new TableProperties();
	private Map<String, String> attributes = new HashMap<String, String>();
	private String datasourceUrl;
	private List<Plugin> plugins = new ArrayList<Plugin>();
	private List<Feature> features = new ArrayList<Feature>();
	private List<ExtraFile> extraFiles = new ArrayList<ExtraFile>();
	private List<ExtraConf> extraConfs = new ArrayList<ExtraConf>();
	private String randomId;
	private String objectType;
	private String currentUrl;
	
	// Export
	private ExportProperties exportProperties;
	private Boolean exporting;
	private Map<ExportType, ExportConf> exportConfMap = new HashMap<ExportType, ExportConf>();
	private List<ExportLinkPosition> exportLinkPositions;
	private Boolean isExportable = false;
	
	public HtmlTable() {
	};

	public HtmlTable(String domId) {
		this.domId = domId;
	}

	public HtmlTable(String domId, String randomId) {
		this.domId = domId;
		this.randomId = randomId;
	}
	
	public String getId() {
		return this.domId;
	}

	public void setDomId(String domId) {
		this.domId = domId;
		this.attributes.put("id", domId);
	}

	public List<HtmlRow> getHeadRows(){
		return head;
	}
	
	public List<HtmlRow> getBodyRows(){
		return body;
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

	public HtmlRow addFooterRow() {
		HtmlRow row = new HtmlRow();
		this.foot.add(row);
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

	public HtmlRow getLastFooterRow() {
		return ((LinkedList<HtmlRow>) this.foot).getLast();
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
		tmpRetval.append(this.domId);
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

	public void registerPlugin(Plugin plugin) {
		this.plugins.add(plugin);
	}

	public void registerFeature(Feature feature) {
		this.features.add(feature);
	}
	
	public Boolean hasOneFilterableColumn(){
		
		Boolean retval = false;
		
		for(HtmlRow headerRow : this.head){
			for(HtmlColumn headerColumn : headerRow.getHeaderColumns()){
				if(headerColumn.getFilterable()){
					retval = true;
					break;
				}
			}
		}
		
		return retval;
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

	public Boolean getLengthChange() {
		return lengthChange;
	}

	public void setLengthChange(Boolean lengthChange) {
		this.lengthChange = lengthChange;
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

	public List<Plugin> getPlugins() {
		return plugins;
	}

	public void setPlugins(List<Plugin> plugins) {
		this.plugins = plugins;
	}

	public List<Feature> getFeatures() {
		return features;
	}

	public void setFeatures(List<Feature> features) {
		this.features = features;
	}
	
	public String getFixedPosition() {
		return fixedPosition;
	}

	public void setFixedPosition(String fixedPosition) {
		this.fixedPosition = fixedPosition;
	}

	public Boolean getCdn() {
		return cdn;
	}

	public void setCdn(Boolean cdn) {
		this.cdn = cdn;
	}

	public Integer getFixedOffsetTop() {
		return fixedOffsetTop;
	}

	public void setFixedOffsetTop(Integer fixedOffsetTop) {
		this.fixedOffsetTop = fixedOffsetTop;
	}

	public String getLabels() {
		return labels;
	}

	public void setLabels(String labels) {
		this.labels = labels;
	}

	public String getRandomId() {
		return randomId;
	}

	public void setRandomId(String randomId) {
		this.randomId = randomId;
	}

	public Boolean getJqueryUI() {
		return jqueryUI;
	}

	public void setJqueryUI(Boolean jqueryUI) {
		this.jqueryUI = jqueryUI;
	}

	public TableProperties getTableProperties() {
		return tableProperties;
	}

	public void setTableProperties(TableProperties tableProperties) {
		this.tableProperties = tableProperties;
	}

	public Boolean getExporting() {
		return exporting;
	}

	public void setExporting(Boolean exporting) {
		this.exporting = exporting;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public ExportProperties getExportProperties() {
		return exportProperties;
	}

	public void setExportProperties(ExportProperties exportProperties) {
		this.exportProperties = exportProperties;
	}

	public Map<ExportType, ExportConf> getExportConfMap() {
		return exportConfMap;
	}

	public void setExportConfMap(Map<ExportType, ExportConf> exportConfs) {
		this.exportConfMap = exportConfs;
	}

	public Boolean isExportable() {
		return isExportable;
	}

	public void setIsExportable(Boolean isExportable) {
		this.isExportable = isExportable;
	}

	public List<ExportLinkPosition> getExportLinkPositions() {
		return exportLinkPositions;
	}

	public void setExportLinkPositions(List<ExportLinkPosition> exportLinkPositions) {
		this.exportLinkPositions = exportLinkPositions;
	}

	public String getCurrentUrl() {
		return currentUrl;
	}

	public void setCurrentUrl(String currentUrl) {
		this.currentUrl = currentUrl;
	}

	@Override
	public String toString() {
		return "HtmlTable [domId=" + domId + ", cssStyle=" + cssStyle + ", cssClass=" + cssClass
				+ ", autoWidth=" + autoWidth + ", deferRender=" + deferRender + ", info=" + info
				+ ", filterable=" + filterable + ", paginate=" + paginate + ", paginationStyle="
				+ paginationStyle + ", lengthChange=" + lengthChange + ", processing=" + processing
				+ ", sort=" + sort + ", stateSave=" + stateSave + ", labels=" + labels + ", cdn="
				+ cdn + ", jqueryUI=" + jqueryUI + ", scrollY=" + scrollY + ", fixedPosition="
				+ fixedPosition + ", fixedOffsetTop=" + fixedOffsetTop + ", head=" + head
				+ ", body=" + body + ", foot=" + foot + ", tableProperties=" + tableProperties
				+ ", attributes=" + attributes + ", datasourceUrl=" + datasourceUrl + ", plugins="
				+ plugins + ", features=" + features + ", extraFiles=" + extraFiles
				+ ", extraConfs=" + extraConfs + ", randomId=" + randomId + ", objectType="
				+ objectType + ", currentUrl=" + currentUrl + ", exportProperties="
				+ exportProperties + ", exporting=" + exporting + ", exportConfMap="
				+ exportConfMap + ", exportLinkPositions=" + exportLinkPositions
				+ ", isExportable=" + isExportable + "]";
	}
	
	
}