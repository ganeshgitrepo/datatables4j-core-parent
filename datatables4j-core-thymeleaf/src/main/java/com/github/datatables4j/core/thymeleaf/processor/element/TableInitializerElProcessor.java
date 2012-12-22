package com.github.datatables4j.core.thymeleaf.processor.element;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.Arguments;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.IElementNameProcessorMatcher;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.element.AbstractElementProcessor;

import com.github.datatables4j.core.api.exception.BadConfigurationException;
import com.github.datatables4j.core.api.model.HtmlTable;
import com.github.datatables4j.core.base.properties.PropertiesLoader;
import com.github.datatables4j.core.base.util.RequestHelper;
import com.github.datatables4j.core.base.util.ResourceHelper;

/**
 * TODO
 *
 * @author Thibault Duchateau
 */
public class TableInitializerElProcessor extends AbstractElementProcessor {
	
	// Logger
	private static Logger logger = LoggerFactory.getLogger(TableInitializerElProcessor.class);
		
	public TableInitializerElProcessor(IElementNameProcessorMatcher matcher) {
		super(matcher);
	}

	@Override
	public int getPrecedence() {
		return 8000;
	}

	@Override
	protected ProcessorResult processElement(Arguments arguments, Element element) {
		String tableId = element.getAttributeValue("id");
		logger.debug("{} element found with id {}", element.getNormalizedName(), tableId);
		
		HttpServletRequest request = ((IWebContext) arguments.getContext()).getHttpServletRequest();
		
		if(tableId == null){
			// TODO throw an exception
			System.out.println("ERREUR, table id obligatoire");
			return null;
		}
		else{
			HtmlTable htmlTable = new HtmlTable(tableId, ResourceHelper.getRamdomNumber());
			System.out.println("RequestHelper.getCurrentUrl(request) = " + RequestHelper.getCurrentUrl(request));
			htmlTable.setCurrentUrl(RequestHelper.getCurrentUrl(request));
			
			try {
				// Load table properties
				PropertiesLoader.load(htmlTable);
			} catch (BadConfigurationException e) {
				logger.error("Unable to load DataTables4j configuration");
				e.printStackTrace();
			}
			
			// Add default footer and header row
			htmlTable.addHeaderRow();
			htmlTable.addFooterRow();

			// HtmlTable POJO is made available during the table element processing
			Map<String, Object> newVariable = new HashMap<String,Object>();
	        newVariable.put("htmlTable", htmlTable);
	        return ProcessorResult.setLocalVariables(newVariable);
		}
	}
}