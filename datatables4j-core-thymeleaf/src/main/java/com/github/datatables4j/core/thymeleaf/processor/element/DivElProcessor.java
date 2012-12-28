package com.github.datatables4j.core.thymeleaf.processor.element;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.Arguments;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.processor.IElementNameProcessorMatcher;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.element.AbstractElementProcessor;

import com.github.datatables4j.core.api.exception.BadConfigurationException;
import com.github.datatables4j.core.api.model.HtmlTable;
import com.github.datatables4j.core.base.properties.PropertiesLoader;
import com.github.datatables4j.core.base.util.RequestHelper;
import com.github.datatables4j.core.base.util.ResourceHelper;
import com.github.datatables4j.core.thymeleaf.dialect.DataTablesDialect;

/**
 * TODO
 *
 * @author Thibault Duchateau
 */
public class DivElProcessor extends AbstractElementProcessor {
	
	// Logger
	private static Logger logger = LoggerFactory.getLogger(DivElProcessor.class);
		
	public DivElProcessor(IElementNameProcessorMatcher matcher) {
		super(matcher);
	}

	@Override
	public int getPrecedence() {
		return 50001;
	}

	@Override
	protected ProcessorResult processElement(Arguments arguments, Element element) {
		System.out.println("div");

		element.removeAttribute("dt:test");
		element.getParent().removeChild(element);
		return ProcessorResult.OK;
	}
}