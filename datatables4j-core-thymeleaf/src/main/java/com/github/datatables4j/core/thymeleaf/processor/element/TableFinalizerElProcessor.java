package com.github.datatables4j.core.thymeleaf.processor.element;

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
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.IElementNameProcessorMatcher;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.element.AbstractElementProcessor;

import com.github.datatables4j.core.api.constants.CdnConstants;
import com.github.datatables4j.core.api.constants.ExportConstants;
import com.github.datatables4j.core.api.exception.BadConfigurationException;
import com.github.datatables4j.core.api.exception.CompressionException;
import com.github.datatables4j.core.api.exception.DataNotFoundException;
import com.github.datatables4j.core.api.exception.ExportException;
import com.github.datatables4j.core.api.export.ExportConf;
import com.github.datatables4j.core.api.export.ExportLinkPosition;
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
 * TODO
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
		return 8000;
	}

	@Override
	protected ProcessorResult processElement(Arguments arguments, Element element) {
		System.out.println("=====> TBODY ELEMENT !!!");

		System.out.println("arguments = " + arguments.toString());
		System.out.println("element = " + element.getNormalizedName());
		System.out.println("getParentAsElement = "
				+ Utils.getParentAsElement(element).getNormalizedName());
		HttpServletRequest request = ((IWebContext) arguments.getContext()).getHttpServletRequest();

		HtmlTable htmlTable = (HtmlTable) arguments.getLocalVariable("htmlTable");
		this.htmlTable = htmlTable;
		System.out.println("table = " + this.htmlTable);

		// // Export links
		// The exportConfMap hasn't been filled by ExportTag
		// So we use the default configuration
		if (this.htmlTable != null && this.htmlTable.getExportConfMap().size() == 0) {

			for (ExportType exportType : this.htmlTable.getTableProperties().getExportTypes()) {
				ExportConf conf = new ExportConf();

				conf.setFileName("export");
				conf.setType(exportType.toString());
				conf.setLabel(exportType.toString());
				conf.setPosition(ExportLinkPosition.TOP_RIGHT);
				conf.setIncludeHeader(true);
				conf.setArea("ALL");
				conf.setUrl(htmlTable.getCurrentUrl() + "?" + ExportConstants.DT4J_EXPORT_ID + "="
						+ htmlTable.getId() + "&" + ExportConstants.DT4J_EXPORT_TYPE + "="
						+ ExportType.valueOf(conf.getType()).getUrlParameter());

				this.htmlTable.getExportConfMap().put(exportType, conf);
			}
		}
		
		if (this.htmlTable != null) {

			// The table is being exported
			if (RequestHelper.isTableBeingExported(request, this.htmlTable)) {
				System.out.println("===============================================================");
				setupExport(arguments);
			}
			// The table must be generated and displayed
			else {
				setupHtmlGeneration(arguments, element, request);
			}
		}

		return ProcessorResult.OK;
	}

	private void registerFeatures(Element element, HtmlTable htmlTable) {
		if (htmlTable.hasOneFilterableColumn()) {
			logger.info("Feature detected : select with filter");
			// this.table.registerFeature(new InputFilteringFeature());
			// this.table.registerFeature(new SelectFilteringFeature());

			// Duplicate header row in the footer
			duplicateHeaderInFooter(element);
//			for (HtmlColumn column : htmlTable.getLastHeaderRow().getColumns()) {
//				htmlTable.getLastFooterRow().addColumn(column);
//			}

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
		HttpServletResponse response = ((IWebContext) arguments.getContext()).getHttpServletResponse();

		// Init the export properties
		ExportProperties exportProperties = new ExportProperties();

		ExportType currentExportType = getCurrentExportType(request);

		exportProperties.setCurrentExportType(currentExportType);
		exportProperties.setExportConf(this.htmlTable.getExportConfMap().get(currentExportType));
		exportProperties.setFileName(this.htmlTable.getExportConfMap().get(currentExportType).getFileName());

		this.htmlTable.setExportProperties(exportProperties);
		this.htmlTable.setExporting(true);

		try {
			// Call the export delegate
			ExportDelegate exportDelegate = new ExportDelegate(this.htmlTable, exportProperties, request);
			exportDelegate.setupExport();

		} catch (ExportException e) {
			logger.error("Something went wront with the DataTables4j export configuration.");
//			throw new JspException(e);
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
		registerFeatures(element, this.htmlTable);

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
				DomUtils.addLinkTag(Utils.getParentAsElement(element), request, CdnConstants.CDN_CSS,
						arguments.getDocument());
			}
			for (Entry<String, CssResource> entry : webResources.getStylesheets().entrySet()) {
				servletContext.setAttribute(entry.getKey(), entry.getValue());
				DomUtils.addLinkTag(Utils.getParentAsElement(element), request, Utils.getBaseUrl(request)
						+ "/datatablesController/" + entry.getKey(), arguments.getDocument());
			}

			// <script> HTML tag generation
			if (htmlTable.getCdn() != null && htmlTable.getCdn()) {
				DomUtils.addScriptTag(Utils.getParentAsElement(element), request, CdnConstants.CDN_JS_MIN,
						arguments.getDocument());
			}
			for (Entry<String, JsResource> entry : webResources.getJavascripts().entrySet()) {
				servletContext.setAttribute(entry.getKey(), entry.getValue());
				DomUtils.addScriptTag(Utils.getParentAsElement(element), request, Utils.getBaseUrl(request)
						+ "/datatablesController/" + entry.getKey(), arguments.getDocument());
			}
			servletContext.setAttribute(webResources.getMainJsFile().getName(),
					webResources.getMainJsFile());
			DomUtils.addScriptTag(Utils.getParentAsElement(element), request, Utils.getBaseUrl(request)
					+ "/datatablesController/" + webResources.getMainJsFile().getName(),
					arguments.getDocument());

			logger.debug("Web content generated successfully");
		} catch (CompressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Return the current export type asked by the user on export link click.
	 * 
	 * @return An enum corresponding to the type of export.
	 */
	private ExportType getCurrentExportType(HttpServletRequest request) {

		// Get the URL parameter used to identify the export type
		String exportTypeString = request.getParameter(ExportConstants.DT4J_EXPORT_TYPE).toString();

		// Convert it to the corresponding enum
		ExportType exportType = ExportType.findByUrlParameter(Integer.parseInt(exportTypeString));

		return exportType;
	}
	
	/**
	 * TODO
	 * @param element
	 */
	private void duplicateHeaderInFooter(Element element){
		Element tfoot = new Element("tfoot");
		
		for (HtmlColumn column : htmlTable.getLastHeaderRow().getColumns()) {
			Element th = new Element("th");
			th.addChild(new Text(""));
			tfoot.addChild(th);
		}
		element.getParent().insertAfter(element, tfoot);
	}
}