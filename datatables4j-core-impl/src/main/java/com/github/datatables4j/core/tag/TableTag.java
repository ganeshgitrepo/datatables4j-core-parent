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
package com.github.datatables4j.core.tag;

import java.io.IOException;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.datatables4j.core.aggregator.ResourceAggregator;
import com.github.datatables4j.core.api.constants.CdnConstants;
import com.github.datatables4j.core.api.constants.ExportConstants;
import com.github.datatables4j.core.api.exception.BadConfigurationException;
import com.github.datatables4j.core.api.exception.BadExportConfigurationException;
import com.github.datatables4j.core.api.exception.CompressionException;
import com.github.datatables4j.core.api.exception.DataNotFoundException;
import com.github.datatables4j.core.api.exception.ExportException;
import com.github.datatables4j.core.api.model.CssResource;
import com.github.datatables4j.core.api.model.ExportProperties;
import com.github.datatables4j.core.api.model.ExportType;
import com.github.datatables4j.core.api.model.HtmlTable;
import com.github.datatables4j.core.api.model.JsResource;
import com.github.datatables4j.core.api.model.WebResources;
import com.github.datatables4j.core.compressor.ResourceCompressor;
import com.github.datatables4j.core.generator.WebResourceGenerator;
import com.github.datatables4j.core.module.export.ExportDelegate;
import com.github.datatables4j.core.properties.PropertiesLoader;
import com.github.datatables4j.core.util.RequestHelper;
import com.github.datatables4j.core.util.ResourceHelper;

/**
 * Tag used to generate a HTML table.
 * 
 * @author Thibault Duchateau
 */
public class TableTag extends AbstractTableTag {
	private static final long serialVersionUID = 4528524566511084446L;

	// Logger
	private static Logger logger = LoggerFactory.getLogger(TableTag.class);

	/**
	 * TODO
	 */
	public int doStartTag() throws JspException {
		// Just used to identify the first row (header)
		rowNumber = 1;

		// Init the table with its DOM id and a generated random number
		table = new HtmlTable(id, ResourceHelper.getRamdomNumber());
		table.setCurrentUrl(RequestHelper.getCurrentUrl((HttpServletRequest)pageContext.getRequest()));
		
		try {
			// Load table properties
			PropertiesLoader.load(this.table);
		} catch (BadConfigurationException e) {
			throw new JspException("Unable to load DataTables4j configuration");
		}

		// The table data are loaded using AJAX source
		if ("AJAX".equals(this.loadingType)) {

			this.table.addFooterRow();
			this.table.setDatasourceUrl(url);
			this.table.addHeaderRow();
			this.table.addRow();

			return EVAL_BODY_BUFFERED;
		}
		// The table data are loaded using a DOM source (Collection)
		else if ("DOM".equals(this.loadingType)) {
			this.table.addFooterRow();
			this.table.addHeaderRow();

			return processIteration();
		}

		// Never reached
		return SKIP_BODY;
	}

	/**
	 * TODO
	 */
	public int doAfterBody() throws JspException {

		this.rowNumber++;

		return processIteration();
	}

	/**
	 * TODO
	 */
	public int doEndTag() throws JspException {
		System.out.println(" ========================== DEBUT doEndTag");

		// Update the HtmlTable object configuration with the attributes
		registerBasicConfiguration();

		// Update the HtmlTable object with the export configuration
		try {
			registerExportConfiguration();
		} catch (BadExportConfigurationException e) {
			throw new JspException(e);
		}
		
		// The table is being exported
		if (isExporting()) {
			return setupExport();
		}
		// The table must be generated and displayed
		else {
			return setupHtmlGeneration();
		}
	}

	/**
	 * Set up the export.
	 * 
	 * @return allways SKIP_PAGE, because the export filter will override the
	 *         response with the exported data.
	 * @throws JspException
	 *             if something went wrong during export.
	 */
	private int setupExport() throws JspException {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();

		// Init the export properties
		ExportProperties exportProperties = new ExportProperties();

		ExportType currentExportType = getCurrentExportType();
		exportProperties.setCurrentExportType(currentExportType);
		exportProperties.setExportConf(table.getExportConfMap().get(currentExportType));
		exportProperties.setFileName(table.getExportConfMap().get(currentExportType).getFileName());			
		
		this.table.setExportProperties(exportProperties);
		this.table.setExporting(true);

		try {
			// Call the export delegate
			ExportDelegate exportDelegate = new ExportDelegate(table);
			Object content = exportDelegate.getExportContent(exportProperties);

			// Fill the request so that the filter will intercept it and
			// override the response
			request.setAttribute(ExportConstants.DT4J_EXPORT_CONTENT, content);
			request.setAttribute(ExportConstants.DT4J_EXPORT_PROPERTIES, exportProperties);

		} catch (ExportException e) {
			logger.error("Something went wront with the DataTables4j export configuration.");
			throw new JspException(e);
		}

		response.reset();

		return SKIP_PAGE;
	}
	
	/**
	 * Set up the HTML table generation.
	 * 
	 * @return allways EVAL_PAGE to keep evaluating the page.
	 * @throws JspException
	 *             if something went wrong during the processing.
	 */
	private int setupHtmlGeneration() throws JspException {
		String baseUrl = RequestHelper.getBaseUrl(pageContext);
		ServletContext servletContext = pageContext.getServletContext();

		this.table.setExporting(false);

		// Register all activated modules
		registerModules();

		// Register all activated features
		registerFeatures();

		try {
			// Init the web resources generator
			WebResourceGenerator contentGenerator = new WebResourceGenerator();

			// Generate the web resources (JS, CSS) and wrap them into a WebResources POJO
			WebResources webResources = contentGenerator.generateWebResources(pageContext,
					this.table);

			// Aggregation
			if (this.table.getTableProperties().isAggregatorEnable()) {
				logger.debug("Aggregation enabled");
				ResourceAggregator.processAggregation(webResources, table);
			}

			// Compression
			if (this.table.getTableProperties().isCompressorEnable()) {
				logger.debug("Compression enabled");
				ResourceCompressor.processCompression(webResources, table);
			}

			// <link> HTML tag generation
			if (this.isCdnEnable()) {
				pageContext.getOut().println(
						"<link rel=\"stylesheet\" href=\"" + CdnConstants.CDN_CSS + "\">");
			}
			for (Entry<String, CssResource> entry : webResources.getStylesheets().entrySet()) {
				servletContext.setAttribute(entry.getKey(), entry.getValue());
				pageContext.getOut().println(
						"<link href=\"" + baseUrl + "/datatablesController/" + entry.getKey()
								+ "\" rel=\"stylesheet\">");
			}

			// HTML generation
			pageContext.getOut().println(this.table.toHtml());

			// <script> HTML tag generation
			if (this.isCdnEnable()) {
				pageContext.getOut().println(
						"<script src=\"" + CdnConstants.CDN_JS_MIN + "\"></script>");
			}
			for (Entry<String, JsResource> entry : webResources.getJavascripts().entrySet()) {
				servletContext.setAttribute(entry.getKey(), entry.getValue());
				pageContext.getOut().println(
						"<script src=\"" + baseUrl + "/datatablesController/" + entry.getKey()
								+ "\"></script>");
			}

			logger.debug("Web content generated successfully");
		} catch (IOException e) {
			logger.error("Something went wront with the datatables tag");
			throw new JspException(e);
		} catch (CompressionException e) {
			logger.error("Something went wront with the compressor.");
			throw new JspException(e);
		} catch (BadConfigurationException e) {
			logger.error("Something went wront with the DataTables4j configuration. Please check your datatables4j.properties file");
			throw new JspException(e);
		} catch (DataNotFoundException e) {
			logger.error("Something went wront with the data provider.");
			throw new JspException(e);
		}

		return EVAL_PAGE;
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