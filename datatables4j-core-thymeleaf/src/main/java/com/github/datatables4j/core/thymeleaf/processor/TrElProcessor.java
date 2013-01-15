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
import com.github.datatables4j.core.thymeleaf.dialect.DataTablesDialect;
import com.github.datatables4j.core.thymeleaf.util.Utils;

/**
 * Element processor applied to the <tt>tr</tt> HTML tag. Whenever Thymeleaf
 * meets a <tt>tr</tt> tag, a HtmlRow is added to the current table. So each
 * <tt>td</tt> tag will be able to update it.
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
		logger.debug("{} element found", element.getNormalizedName());

		// Get HtmlTable POJO from local variables
		HtmlTable htmlTable = Utils.getTable(arguments);

		if (htmlTable != null) {
			htmlTable.getBodyRows().add(new HtmlRow());
		}

		if (element.hasAttribute(DataTablesDialect.DIALECT_PREFIX + ":data")) {
			element.removeAttribute(DataTablesDialect.DIALECT_PREFIX + ":data");
		}

		return ProcessorResult.OK;
	}
}