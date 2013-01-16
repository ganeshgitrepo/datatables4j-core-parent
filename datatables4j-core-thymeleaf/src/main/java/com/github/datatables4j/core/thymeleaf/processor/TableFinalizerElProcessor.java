package com.github.datatables4j.core.thymeleaf.processor;

import java.io.IOException;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.Arguments;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.IElementNameProcessorMatcher;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.element.AbstractElementProcessor;

import com.github.datatables4j.core.api.constants.CdnConstants;
import com.github.datatables4j.core.api.constants.ExportConstants;
import com.github.datatables4j.core.api.exception.BadConfigurationException;
import com.github.datatables4j.core.api.exception.CompressionException;
import com.github.datatables4j.core.api.exception.DataNotFoundException;
import com.github.datatables4j.core.api.exception.DataTables4jException;
import com.github.datatables4j.core.api.exception.ExportException;
import com.github.datatables4j.core.api.export.ExportProperties;
import com.github.datatables4j.core.api.export.ExportType;
import com.github.datatables4j.core.api.model.CssResource;
import com.github.datatables4j.core.api.model.HtmlColumn;
import com.github.datatables4j.core.api.model.HtmlTable;
import com.github.datatables4j.core.api.model.JsResource;
import com.github.datatables4j.core.api.model.WebResources;
import com.github.datatables4j.core.base.aggregator.ResourceAggregator;
import com.github.datatables4j.core.base.compressor.ResourceCompressor;
import com.github.datatables4j.core.base.export.ExportDelegate;
import com.github.datatables4j.core.base.feature.FilteringFeature;
import com.github.datatables4j.core.base.generator.WebResourceGenerator;
import com.github.datatables4j.core.base.util.RequestHelper;
import com.github.datatables4j.core.thymeleaf.util.DomUtils;
import com.github.datatables4j.core.thymeleaf.util.Utils;

/**
 * <p>
 * Element processor applied to the internal HTML <code>div</code> tag.
 * <p>
 * The <code>div</code> is added by the TableInitializerProcessor after the
 * <code>table</code> in order to be processed after all the "table" processors.
 * 
 * @author Thibault Duchateau
 */
public class TableFinalizerElProcessor extends AbstractElementProcessor {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(TableFinalizerElProcessor.class);

	private HtmlTable htmlTable;

	public TableFinalizerElProcessor(IElementNameProcessorMatcher matcher) {
		super(matcher);
	}

	@Override
	public int getPrecedence() {
		return 50000;
	}

	@Override
	protected ProcessorResult processElement(Arguments arguments, Element element) {

		// Get the HTTP request
		HttpServletRequest request = ((IWebContext) arguments.getContext()).getHttpServletRequest();

		// HtmlTable htmlTable = (HtmlTable)
		// arguments.getLocalVariable("htmlTable");
		HtmlTable htmlTable = Utils.getTable(arguments);
		this.htmlTable = htmlTable;

		if (this.htmlTable != null) {

			// The table is being exported
			if (RequestHelper.isTableBeingExported(request, this.htmlTable)) {
				setupExport(arguments);
			}
			// The table must be generated and displayed
			else {
				setupHtmlGeneration(arguments, element, request);
			}
		}

		// The "finalizing div" can now be removed
		element.getParent().removeChild(element);

		return ProcessorResult.OK;
	}

	private void registerFeatures(Element element, Arguments arguments, HtmlTable htmlTable) {
		if (htmlTable.hasOneFilterableColumn()) {
			logger.info("Feature detected : select with filter");

			// Duplicate header row in the footer
			generateFooter(element, arguments);

			htmlTable.registerFeature(new FilteringFeature());
		}
	}

	/**
	 * Set up the export properties, before the filter intercepts the response.
	 * 
	 * @return allways SKIP_PAGE, because the export filter will override the
	 *         response with the exported data instead of displaying the page.
	 * @throws JspException
	 *             if something went wrong during export.
	 */
	private void setupExport(Arguments arguments) {
		logger.debug("Setting export up ...");

		HttpServletRequest request = ((IWebContext) arguments.getContext()).getHttpServletRequest();
		HttpServletResponse response = ((IWebContext) arguments.getContext())
				.getHttpServletResponse();

		// Init the export properties
		ExportProperties exportProperties = new ExportProperties();

		ExportType currentExportType = getCurrentExportType(request);

		exportProperties.setCurrentExportType(currentExportType);
		exportProperties.setExportConf(this.htmlTable.getExportConfMap().get(currentExportType));
		exportProperties.setFileName(this.htmlTable.getExportConfMap().get(currentExportType)
				.getFileName());

		this.htmlTable.setExportProperties(exportProperties);
		this.htmlTable.setExporting(true);

		try {
			// Call the export delegate
			ExportDelegate exportDelegate = new ExportDelegate(this.htmlTable, exportProperties,
					request);
			exportDelegate.setupExport();

		} catch (ExportException e) {
			logger.error("Something went wront with the DataTables4j export configuration.");
			// throw new JspException(e);
			e.printStackTrace();
		}

		response.reset();
	}

	/**
	 * Set up the HTML table generation.
	 * 
	 * @return allways EVAL_PAGE to keep evaluating the page.
	 * @throws JspException
	 *             if something went wrong during the processing.
	 */
	private void setupHtmlGeneration(Arguments arguments, Element element,
			HttpServletRequest request) {
		ServletContext servletContext = request.getServletContext();

		this.htmlTable.setExporting(false);

		// Plugins and themes are activated in their respective attribute
		// processor

		// Register all activated features
		registerFeatures(element, arguments, this.htmlTable);

		try {

			// Init the web resources generator
			WebResourceGenerator contentGenerator = new WebResourceGenerator();

			// Generate the web resources (JS, CSS) and wrap them into a
			// WebResources POJO
			WebResources webResources;
			webResources = contentGenerator.generateWebResources(htmlTable);

			// Aggregation
			if (htmlTable.getTableProperties().isAggregatorEnable()) {
				logger.debug("Aggregation enabled");
				ResourceAggregator.processAggregation(webResources, htmlTable);
			}

			// Compression
			if (htmlTable.getTableProperties().isCompressorEnable()) {
				logger.debug("Compression enabled");
				ResourceCompressor.processCompression(webResources, htmlTable);
			}

			// <link> HTML tag generation
			if (htmlTable.getCdn() != null && htmlTable.getCdn()) {
				DomUtils.addLinkTag(DomUtils.getParentAsElement(element), request,
						CdnConstants.CDN_DATATABLES_CSS);
			}
			for (Entry<String, CssResource> entry : webResources.getStylesheets().entrySet()) {
				servletContext.setAttribute(entry.getKey(), entry.getValue());
				DomUtils.addLinkTag(element, request, Utils.getBaseUrl(request)
						+ "/datatablesController/" + entry.getKey());
			}

			// <script> HTML tag generation
			if (htmlTable.getCdn() != null && htmlTable.getCdn()) {
				DomUtils.addScriptTag(DomUtils.getParentAsElement(element), request,
						CdnConstants.CDN_DATATABLES_JS_MIN);
			}
			for (Entry<String, JsResource> entry : webResources.getJavascripts().entrySet()) {
				servletContext.setAttribute(entry.getKey(), entry.getValue());
				DomUtils.addScriptTag(DomUtils.getParentAsElement(element), request,
						Utils.getBaseUrl(request) + "/datatablesController/" + entry.getKey());
			}
			servletContext.setAttribute(webResources.getMainJsFile().getName(),
					webResources.getMainJsFile());
			DomUtils.addScriptTag(DomUtils.getParentAsElement(element), request,
					Utils.getBaseUrl(request) + "/datatablesController/"
							+ webResources.getMainJsFile().getName());

			logger.debug("Web content generated successfully");
		} catch (CompressionException e) {
			logger.error("Something went wront with the compressor.");
			throw new DataTables4jException(e);
		} catch (IOException e) {
			throw new DataTables4jException(e);
		} catch (BadConfigurationException e) {
			logger.error("Something went wront with the DataTables4j configuration.");
			throw new DataTables4jException(e);
		} catch (DataNotFoundException e) {
			logger.error("Something went wront with the data provider.");
			throw new DataTables4jException(e);
		}
	}

	/**
	 * Return the current export type asked by the user on export link click.
	 * 
	 * @return An enum corresponding to the type of export.
	 */
	private ExportType getCurrentExportType(HttpServletRequest request) {

		// Get the URL parameter used to identify the export type
		String exportTypeString = request.getParameter(
				ExportConstants.DT4J_REQUESTPARAM_EXPORT_TYPE).toString();

		// Convert it to the corresponding enum
		ExportType exportType = ExportType.findByUrlParameter(Integer.parseInt(exportTypeString));

		return exportType;
	}

	/**
	 * TODO
	 * 
	 * @param element
	 */
	private void generateFooter(Element element, Arguments arguments) {
		Element tfoot = new Element("tfoot");

		Node tableNode = (Node)((IWebContext) arguments.getContext()).getHttpServletRequest().getAttribute("tableNode");
		
		for(HtmlColumn column : htmlTable.getLastHeaderRow().getColumns()){
			Element th = new Element("th");
			th.addChild(new Text(column.getContent()));
			tfoot.addChild(th);
		}

		((Element) tableNode).addChild(tfoot);
	}
}