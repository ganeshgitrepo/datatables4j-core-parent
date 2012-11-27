/*
 * [The "BSD licence"]
 * Copyright (c) 2012 DataTables4j
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 3. Neither the name of DataTables4j nor the names of its contributors 
 * may be used to endorse or promote products derived from this software 
 * without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.github.datatables4j.core.tag;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.datatables4j.core.api.constants.ExportConstants;
import com.github.datatables4j.core.api.model.ExportConf;
import com.github.datatables4j.core.api.model.ExportLinkPosition;
import com.github.datatables4j.core.api.model.ExportType;
import com.github.datatables4j.core.api.model.HtmlTable;
import com.github.datatables4j.core.feature.ui.InputFilteringFeature;
import com.github.datatables4j.core.feature.ui.SelectFilteringFeature;
import com.github.datatables4j.core.plugin.ui.ColReorderModule;
import com.github.datatables4j.core.plugin.ui.FixedHeaderModule;
import com.github.datatables4j.core.plugin.ui.ScrollerModule;
import com.github.datatables4j.core.util.RequestHelper;

/**
 * Abstract class which contains :<br />
 * <ul>
 * <li>all the boring technical stuff needed by Java tags (getters and setters
 * for all Table tag attributes)</li>
 * <li>helper methods used to init the table</li>
 * </ul>
 * 
 * @author Thibault Duchateau
 */
public abstract class AbstractTableTag extends BodyTagSupport {

	private static final long serialVersionUID = 4788079931487986884L;

	// Logger
	private static Logger logger = LoggerFactory.getLogger(AbstractTableTag.class);

	protected Object data;
	protected String url;
	protected String row;

	// Tag attributes
	protected String id;
	protected String cssStyle;
	protected String cssClass;
	protected String rowIdBase;
	protected String rowIdPrefix;
	protected String rowIdSufix;

	// Basic features
	protected Boolean autoWidth;
	protected Boolean filter;
	protected Boolean info;
	protected Boolean paginate;
	protected Boolean lengthChange;
	protected String paginationType;
	protected Boolean sort;

	// Advanced features
	protected Boolean deferRender;
	protected Boolean stateSave;
	protected Boolean processing;
	protected String labels;
	protected Boolean jqueryUI;

	// Extra features
	protected Boolean fixedHeader = false;
	protected String fixedPosition;
	protected Integer fixedOffsetTop;
	protected Boolean scroller = false;
	protected String scrollY = "300px";
	protected Boolean colReorder = false;

	// Export
	protected Boolean export;
	protected String exportLinks;

	// Internal common attributes
	protected int rowNumber;
	protected HtmlTable table;
	protected Iterator<Object> iterator;
	protected Object currentObject;
	protected String loadingType;
	protected Boolean cdn = false;

	/**
	 * Register all common configuration with the table.
	 */
	protected void registerBasicConfiguration() {

		if (StringUtils.isNotBlank(this.cssClass)) {
			this.table.setCssClass(this.cssClass);
		}
		if (StringUtils.isNotBlank(this.cssStyle)) {
			this.table.setCssStyle(this.cssStyle);
		}
		if (this.autoWidth != null) {
			this.table.setAutoWidth(this.autoWidth);
		}
		if (this.deferRender != null) {
			this.table.setDeferRender(this.deferRender);
		}
		if (this.filter != null) {
			this.table.setFilterable(this.filter);
		}
		if (this.info != null) {
			this.table.setInfo(this.info);
		}
		if (this.paginate != null) {
			this.table.setPaginate(this.paginate);
		}
		if (this.lengthChange != null) {
			this.table.setLengthChange(this.lengthChange);
		}
		if (StringUtils.isNotBlank(this.paginationType)) {
			this.table.setPaginationStyle(this.paginationType);
		}
		if (this.processing != null) {
			this.table.setProcessing(this.processing);
		}
		if (this.sort != null) {
			this.table.setSort(this.sort);
		}
		if (this.stateSave != null) {
			this.table.setStateSave(this.stateSave);
		}
		if (this.cdn != null) {
			this.table.setCdn(this.cdn);
		}
		if (StringUtils.isNotBlank(this.labels)) {
			this.table.setLabels(RequestHelper.getBaseUrl(pageContext) + this.labels);
		}
		if (this.jqueryUI != null) {
			this.table.setJqueryUI(this.jqueryUI);
		}

	}

	/**
	 * Register activated modules with the table.
	 */
	protected void registerModules() {

		// Modules activation
		if (this.fixedHeader) {
			logger.info("Internal module detected : fixedHeader");
			this.table.registerPlugin(new FixedHeaderModule());
		}

		if (this.scroller) {
			logger.info("Internal module detected : scroller");
			this.table.registerPlugin(new ScrollerModule());
		}

		if (this.colReorder) {
			logger.info("Internal module detected : colReorder");
			this.table.registerPlugin(new ColReorderModule());
		}

		// Modules configuration
		if (StringUtils.isNotBlank(this.scrollY)) {
			this.table.setScrollY(this.scrollY);
		}

		if (StringUtils.isNotBlank(this.fixedPosition)) {
			this.table.setFixedPosition(this.fixedPosition);
		}

		if (this.fixedOffsetTop != null) {
			this.table.setFixedOffsetTop(this.fixedOffsetTop);
		}
	}

	/**
	 * Register activated features with the table.
	 */
	protected void registerFeatures() {

		if (table.hasOneFilterableColumn()) {
			logger.info("Feature detected : select with filter");
			this.table.registerFeature(new InputFilteringFeature());
			this.table.registerFeature(new SelectFilteringFeature());
		}
	}

	/**
	 * Register export configuration.
	 * 
	 * @throws BadExportConfigurationException
	 */
	protected void registerExportConfiguration() throws JspException {

		if (this.export != null && this.export) {

			table.setIsExportable(true);

			// Export links position
			List<ExportLinkPosition> positionList = new ArrayList<ExportLinkPosition>();
			if (StringUtils.isNotBlank(this.exportLinks)) {
				String[] positions = this.exportLinks.trim().toUpperCase().split(",");

				for (String position : positions) {
					try {
						positionList.add(ExportLinkPosition.valueOf(position));
					} catch (IllegalArgumentException e) {
						logger.error("The export cannot be activated for the table {}. ",
								table.getId());
						logger.error("{} is not a valid value among {}", position,
								ExportLinkPosition.values());
						throw new JspException(e);
					}
				}
			} else {
				positionList.add(ExportLinkPosition.TOP_RIGHT);
			}
			this.table.setExportLinkPositions(positionList);

			// // Export links
			// The exportConfMap hasn't been filled by ExportTag
			// So we use the default configuration
			if (table.getExportConfMap().size() == 0) {

				for (ExportType exportType : table.getTableProperties().getExportTypes()) {
					ExportConf conf = new ExportConf();

					conf.setFileName("export");
					conf.setType(exportType.toString());
					conf.setLabel(exportType.toString());
					conf.setPosition(ExportLinkPosition.TOP_MIDDLE);
					conf.setIncludeHeader(true);
					conf.setArea("ALL");
					conf.setUrl(table.getCurrentUrl() + "?" + ExportConstants.DT4J_EXPORT_ID + "="
							+ table.getId() + "&" + ExportConstants.DT4J_EXPORT_TYPE + "="
							+ ExportType.valueOf(conf.getType()).getUrlParameter());

					table.getExportConfMap().put(exportType, conf);
				}
			}
		}
	}

	/**
	 * Process the iteration over the data (only for DOM source).
	 * 
	 * @return EVAL_BODY_BUFFERED if some data remain in the Java Collection,
	 *         SKIP_BODY otherwise.
	 * @throws JspException
	 *             if something went wrong during the row id generation.
	 */
	protected int processIteration() throws JspException {

		if ("DOM".equals(this.loadingType) && iterator.hasNext()) {

			Object object = iterator.next();

			this.setCurrentObject(object);
			table.setObjectType(object.getClass().getSimpleName());

			if (row != null) {
				pageContext.setAttribute(row, object);
			}

			String rowId = getRowId();
			if (StringUtils.isNotBlank(rowId)) {
				this.table.addRow(rowId);
			} else {
				this.table.addRow();
			}

			return EVAL_BODY_BUFFERED;
		} else {
			return SKIP_BODY;
		}
	}

	/**
	 * <p>
	 * Test if the table if being exported using the request
	 * ExportConstants.DT4J_EXPORT_ID attribute.
	 * 
	 * <p>
	 * The table's id must be tested in case of multiple tables are displayed on
	 * the same page and exportables.
	 * 
	 * @return true if the table is being exported, false otherwise.
	 */
	protected Boolean isTableBeingExported() {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		return request.getAttribute(ExportConstants.DT4J_EXPORT_ID) != null ? request
				.getAttribute(ExportConstants.DT4J_EXPORT_ID).toString().toLowerCase()
				.equals(table.getId().toLowerCase()) : false;
	}

	/**
	 * Return the current export type asked by the user on export link click.
	 * 
	 * @return An enum corresponding to the type of export.
	 */
	protected ExportType getCurrentExportType() {

		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

		// Get the URL parameter used to identify the export type
		String exportTypeString = request.getParameter(ExportConstants.DT4J_EXPORT_TYPE).toString();

		// Convert it to the corresponding enum
		ExportType exportType = ExportType.findByUrlParameter(Integer.parseInt(exportTypeString));

		return exportType;
	}

	/**
	 * Test if the user want his table to be exported.
	 * 
	 * @return true if the table can be exported, false otherwise.
	 */
	protected Boolean canBeExported() {
		return this.export != null ? this.export : false;
	}

	/**
	 * Return the row id using prefix, base and suffix. Prefix and sufix are
	 * just prepended and appended strings. Base is extracted from the current
	 * iterated object.
	 * 
	 * @return return the row id using prefix, base and suffix.
	 * @throws JspException
	 *             is the rowIdBase doesn't have a corresponding property
	 *             accessor method.
	 */
	protected String getRowId() throws JspException {

		StringBuffer rowId = new StringBuffer();

		if (StringUtils.isNotBlank(this.rowIdPrefix)) {
			rowId.append(this.rowIdPrefix);
		}

		if (StringUtils.isNotBlank(this.rowIdBase)) {
			try {
				rowId.append(PropertyUtils.getNestedProperty(this.currentObject, this.rowIdBase));
			} catch (IllegalAccessException e) {
				logger.error("Unable to get the value for the given rowIdBase {}", this.rowIdBase);
				throw new JspException(e);
			} catch (InvocationTargetException e) {
				logger.error("Unable to get the value for the given rowIdBase {}", this.rowIdBase);
				throw new JspException(e);
			} catch (NoSuchMethodException e) {
				logger.error("Unable to get the value for the given rowIdBase {}", this.rowIdBase);
				throw new JspException(e);
			}
		}

		if (StringUtils.isNotBlank(this.rowIdSufix)) {
			rowId.append(this.rowIdSufix);
		}

		return rowId.toString();
	}

	/** Getters and setters for all attributes */

	public HtmlTable getTable() {
		return this.table;
	}

	public void setRow(String row) {
		this.row = row;
	}

	public Boolean isFirstRow() {
		return this.rowNumber == 1;
	}

	public Integer getRowNumber() {
		return this.rowNumber;
	}

	public Object getCurrentObject() {
		return this.currentObject;
	}

	public void setCurrentObject(Object currentObject) {
		this.currentObject = currentObject;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCssStyle() {
		return this.cssStyle;
	}

	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}

	public String getCssClass() {
		return this.cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getRowIdBase() {
		return this.rowIdBase;
	}

	public void setRowIdBase(String rowIdBase) {
		this.rowIdBase = rowIdBase;
	}

	public String getRowIdPrefix() {
		return this.rowIdPrefix;
	}

	public void setRowIdPrefix(String rowIdPrefix) {
		this.rowIdPrefix = rowIdPrefix;
	}

	public String getRowIdSufix() {
		return this.rowIdSufix;
	}

	public void setRowIdSufix(String rowIdSufix) {
		this.rowIdSufix = rowIdSufix;
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

	public Boolean getFilter() {
		return filter;
	}

	public void setFilter(Boolean filterable) {
		this.filter = filterable;
	}

	public Boolean getInfo() {
		return info;
	}

	public void setInfo(Boolean info) {
		this.info = info;
	}

	public Boolean getPaginate() {
		return paginate;
	}

	public void setPaginate(Boolean paginate) {
		this.paginate = paginate;
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

	public String getPaginationType() {
		return paginationType;
	}

	public void setPaginationType(String paginationType) {
		this.paginationType = paginationType;
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

	public Boolean getFixedHeader() {
		return fixedHeader;
	}

	public void setFixedHeader(Boolean fixedHeader) {
		this.fixedHeader = fixedHeader;
	}

	public Boolean getScroller() {
		return scroller;
	}

	public void setScroller(Boolean scroller) {
		this.scroller = scroller;
	}

	public Boolean getColReorder() {
		return colReorder;
	}

	public void setColReorder(Boolean colReorder) {
		this.colReorder = colReorder;
	}

	public String getScrollY() {
		return scrollY;
	}

	public void setScrollY(String scrollY) {
		this.scrollY = scrollY;
	}

	public String getFixedPosition() {
		return fixedPosition;
	}

	public void setFixedPosition(String fixedPosition) {
		this.fixedPosition = fixedPosition;
	}

	public String getLabels() {
		return labels;
	}

	public void setLabels(String labels) {
		this.labels = labels;
	}

	public Integer getOffsetTop() {
		return fixedOffsetTop;
	}

	public void setOffsetTop(Integer fixedOffsetTop) {
		this.fixedOffsetTop = fixedOffsetTop;
	}

	public Boolean isCdnEnable() {
		return cdn;
	}

	public void setCdn(Boolean cdn) {
		this.cdn = cdn;
	}

	public Boolean getExport() {
		return export;
	}

	public void setExport(Boolean export) {
		this.export = export;
	}

	public String getLoadingType() {
		return this.loadingType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.loadingType = "AJAX";
		this.url = url;
	}

	public Boolean getJqueryUI() {
		return jqueryUI;
	}

	public void setJqueryUI(Boolean jqueryUI) {
		this.jqueryUI = jqueryUI;
	}

	public String getExportLinks() {
		return exportLinks;
	}

	public void setExportLinks(String exportButtons) {
		this.exportLinks = exportButtons;
	}

	public void setData(Collection<Object> data) {
		this.loadingType = "DOM";
		this.data = data;

		Collection<Object> dataTmp = (Collection<Object>) data;
		if (dataTmp.size() > 0) {
			iterator = dataTmp.iterator();
		} else {
			// TODO afficher un message d'erreur
			// TODO afficher une alerte javascript
		}
	}
}