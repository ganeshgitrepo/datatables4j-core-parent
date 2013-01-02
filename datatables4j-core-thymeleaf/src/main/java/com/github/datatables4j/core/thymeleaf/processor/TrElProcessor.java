package com.github.datatables4j.core.thymeleaf.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.IElementNameProcessorMatcher;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.element.AbstractElementProcessor;

import com.github.datatables4j.core.api.model.HtmlRow;
import com.github.datatables4j.core.api.model.HtmlTable;
import com.github.datatables4j.core.thymeleaf.util.Utils;

/**
 * TODO
 *
 * @author Thibault Duchateau
 */
public class TrElProcessor extends AbstractElementProcessor {
	
	// Logger
	private static Logger logger = LoggerFactory.getLogger(TrElProcessor.class);
		
	public TrElProcessor(IElementNameProcessorMatcher matcher) {
		super(matcher);
	}

	@Override
	public int getPrecedence() {
		return 4001;
	}

	@Override
	protected ProcessorResult processElement(Arguments arguments, Element element) {
		
		// Get HtmlTable POJO from local variables
		HtmlTable htmlTable = Utils.getTable(arguments);
		
		if(htmlTable != null){
			htmlTable.getBodyRows().add(new HtmlRow());
		}
		
		if(element.hasAttribute("dt:data")){
			element.removeAttribute("dt:data");
		}
		
		return ProcessorResult.OK;
	}
}