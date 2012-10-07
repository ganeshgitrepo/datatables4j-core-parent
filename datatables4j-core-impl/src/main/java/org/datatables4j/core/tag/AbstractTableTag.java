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
package org.datatables4j.core.tag;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.datatables4j.core.aggregation.AggregationUtils;
import org.datatables4j.core.api.constants.CdnConstants;
import org.datatables4j.core.api.exception.BadConfigurationException;
import org.datatables4j.core.api.exception.CompressionException;
import org.datatables4j.core.api.exception.DataNotFoundException;
import org.datatables4j.core.api.model.CssResource;
import org.datatables4j.core.api.model.HtmlTable;
import org.datatables4j.core.api.model.JsResource;
import org.datatables4j.core.api.model.WebResources;
import org.datatables4j.core.compression.CompressionUtils;
import org.datatables4j.core.feature.ui.InputFilteringFeature;
import org.datatables4j.core.feature.ui.SelectFilteringFeature;
import org.datatables4j.core.generator.WebResourceGenerator;
import org.datatables4j.core.plugin.ui.ColReorderModule;
import org.datatables4j.core.plugin.ui.FixedHeaderModule;
import org.datatables4j.core.plugin.ui.ScrollerModule;
import org.datatables4j.core.properties.PropertiesLoader;
import org.datatables4j.core.util.RequestHelper;
import org.datatables4j.core.util.ResourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract table tag which contains the common configuration between all table
 * tags.
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

	// Awesome features
	protected String export;
	
	// Internal common attributes
	protected int rowNumber;
	protected HtmlTable table = new HtmlTable();
	protected Iterator<Object> iterator;
	protected Object currentObject;
	protected String loadingType;
	protected Boolean cdn = false;
	
	/**
	 * TODO
	 * @return
	 * @throws JspException
	 */
	protected int processDoStartTag() throws JspException{
		
		// Just used to identify the first row (header)
		rowNumber = 1;
		
		if("AJAX".equals(this.loadingType)){
			this.table = new HtmlTable(id, ResourceUtils.getRamdomNumber());
			this.table.addFooterRow();
			this.table.setDatasourceUrl(url);
			this.table.addHeaderRow();
			this.table.addRow();
			return EVAL_BODY_BUFFERED;
		}
		else if("DOM".equals(this.loadingType)){
			this.table = new HtmlTable(id, ResourceUtils.getRamdomNumber());
			this.table.addFooterRow();
			this.table.addHeaderRow();

			// Body management
			if (iterator.hasNext()) {
				Object object = iterator.next();
				if(row != null){
					pageContext.setAttribute(row, object);					
				}
				this.setCurrentObject(object);

				String rowId = this.getRowId();
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
		
		// Never reached
		return SKIP_BODY;
	}
	
	/**
	 * TODO
	 * @return
	 * @throws JspException
	 */
	protected int processDoAfterBody() throws JspException {
		
		if ("DOM".equals(this.loadingType) && iterator.hasNext()) {

//			BodyContent body = getBodyContent();
//			try {
//				body.writeOut(getPreviousOut());
//			} catch (IOException e) {
//				throw new JspTagException("IterationTag: " + e.getMessage());
//			}
//
//			// clear up so the next time the body content is empty
//			body.clearBody();

//			if (iterator.hasNext()) {
				Object object = iterator.next();
				this.setCurrentObject(object);
				if(row != null){
					pageContext.setAttribute(row, object);					
				}

				String rowId = this.getRowId();
				if (StringUtils.isNotBlank(rowId)) {
						this.table.addRow(rowId);
				} else {
					this.table.addRow();
				}
				
				this.rowNumber++;
				
				// System.out.println(rowNumber);
				return EVAL_BODY_AGAIN;
//			} else {
//				return EVAL_PAGE;
//			}
		}
		else{
			
			return SKIP_BODY;			
		}
	}
	
	/**
	 * TODO
	 * @return
	 * @throws JspException
	 */
	protected int processDoEndTag() throws JspException {
		
		// Getting DataTables4j properties
		PropertiesLoader properties = PropertiesLoader.getInstance();
		
		String baseUrl = RequestHelper.getBaseUrl(pageContext);
		ServletContext context = pageContext.getServletContext();
		
		// Update the HtmlTable object configuration with the attributes
		updateCommonConfiguration();

		// Check if extra features must be activated
		registerModules();

		registerFeatures();
		
		// Check if the table is being exported
		checkExport();
		
		try {
			//
			WebResourceGenerator contentGenerator = new WebResourceGenerator();
			
			// JS script generation according to the JSP tags configuration
			WebResources webResources = contentGenerator.generateWebResources(pageContext, this.table);

			// Aggregation
			if(properties.isAggregatorEnable()){
				AggregationUtils.processAggregation(webResources, table);
			}

			// Compression
			if(properties.isCompressorEnable()){
				CompressionUtils.processCompression(webResources, table);
			}
			
			// <link> HTML tag generation
			if(this.isCdnEnable()){
				pageContext.getOut().println("<link rel=\"stylesheet\" href=\"" + CdnConstants.CDN_CSS + "\">");				
			}
			for(Entry<String, CssResource> entry : webResources.getStylesheets().entrySet()){
				context.setAttribute(entry.getKey(), entry.getValue());
				pageContext.getOut().println(
						"<link href=\"" + baseUrl + "/datatablesController/" + entry.getKey() + "\" rel=\"stylesheet\">");
			}
						
			// HTML generation
			pageContext.getOut().println(this.table.toHtml());
						
			// <script> HTML tag generation
			if(this.isCdnEnable()){
				pageContext.getOut().println("<script src=\"" + CdnConstants.CDN_JS_MIN + "\"></script>");
			}
			for(Entry<String, JsResource> entry : webResources.getJavascripts().entrySet()){
				System.out.println("Fichier = " + entry.getKey());
				context.setAttribute(entry.getKey(), entry.getValue());
				pageContext.getOut().println(
						"<script src=\"" + baseUrl + "/datatablesController/" + entry.getKey() + "\"></script>");
			}
			
			logger.debug("Web content generated");
		} 
		catch (IOException e) {
			logger.error("Something went wront with the datatables tag");
			throw new JspException(e);
		} 
		catch (CompressionException e) {
			logger.error("Something went wront with the compressor.");
			throw new JspException(e);
		} 
		catch (BadConfigurationException e) {
			logger.error("Something went wront with the DataTables4j configuration. Please check your datatables4j.properties file");
			throw new JspException(e);
		} 
		catch (DataNotFoundException e) {
			logger.error("Something went wront with the data provider.");
			throw new JspException(e);
		}
		
		return EVAL_PAGE;
	}

	
	/**
	 * TOD
	 */
	private void updateCommonConfiguration() {
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
		if(this.cdn != null){
			this.table.setCdn(this.cdn);
		}
		if(StringUtils.isNotBlank(this.labels)){
			this.table.setLabels(RequestHelper.getBaseUrl(pageContext) + this.labels);
		}
		if(this.jqueryUI != null){
			this.table.setJqueryUI(this.jqueryUI);
		}

		// Extra features
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
	 * TODO
	 */
	private void registerModules() {

		if (this.fixedHeader) {
			logger.info("Internal module detected : fixedHeader");
			this.table.registerPlugin(new FixedHeaderModule());
		}
		
		if(this.scroller){
			logger.info("Internal module detected : scroller");
			this.table.registerPlugin(new ScrollerModule());
		}
		
		if(this.colReorder){
			logger.info("Internal module detected : colReorder");
			this.table.registerPlugin(new ColReorderModule());
		}
		
		//TODO Others modules 
	}
	
	private void registerFeatures() {

		if(table.hasOneFilterableColumn()){
			logger.info("Feature detected : select with filter");
			this.table.registerFeature(new InputFilteringFeature());
			this.table.registerFeature(new SelectFilteringFeature());
		} 
	}
	
	/**
	 * TODO il faut :
	 * 1) ajouter des boutons correspondant aux differents types d'export dispo
	 * 2) proposer une URL d'export
	 * 3) appeler la bonne classe de génération
	 */
	private void checkExport(){
		
		if(StringUtils.isNotBlank(this.export)){
			
			String[] exportTypes = this.export.split(",");
			// Ajouter le ou les lien(s) d'export
		}
	}
	
	/**
	 * TODO
	 * @return
	 * @throws JspException
	 */
	private String getRowId() throws JspException{
		
		StringBuffer rowId = new StringBuffer();
		if(this.rowIdPrefix != null){
			rowId.append(this.rowIdPrefix);
		}
		if(this.rowIdBase != null){
			try {
				rowId.append(PropertyUtils.getNestedProperty(this.currentObject, this.rowIdBase));
			} catch (IllegalAccessException e) {
				logger.error("Unable to get the value for rowIdBase");
				throw new JspException();
			} catch (InvocationTargetException e) {
				logger.error("Unable to get the value for rowIdBase");
				throw new JspException();
			} catch (NoSuchMethodException e) {
				logger.error("Unable to get the value for rowIdBase");
				throw new JspException();
			}
		}
		if(this.rowIdSufix != null){
			rowId.append(this.rowIdSufix);
		}
		
		return rowId.toString();
	}
	
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
	
	public String getExport() {
		return export;
	}

	public void setExport(String export) {
		this.export = export;
	}
	
	public String getLoadingType(){
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
	
	/**
	 * TODO
	 */
	public void release() {
		// TODO Auto-generated method stub
		super.release();
		
		// TODO
	}
}