package com.github.datatables4j.core.thymeleaf.processor.element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.processor.IElementNameProcessorMatcher;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.element.AbstractElementProcessor;

import com.github.datatables4j.core.thymeleaf.dialect.DataTablesDialect;

/**
 * TODO
 *
 * @author Thibault Duchateau
 */
public class TbodyElProcessor extends AbstractElementProcessor {
	
	// Logger
	private static Logger logger = LoggerFactory.getLogger(TbodyElProcessor.class);
		
	public TbodyElProcessor(IElementNameProcessorMatcher matcher) {
		super(matcher);
	}

	@Override
	public int getPrecedence() {
		return 4000;
	}

	@Override
	protected ProcessorResult processElement(Arguments arguments, Element element) {

		for(Node child : element.getChildren()){
			
			if (child != null && child instanceof Element) {
				
				Element trChildTag = (Element) child;
				String trChildTagName = trChildTag.getNormalizedName();
				
				trChildTag.setProcessable(true);
				
				if (trChildTagName != null && trChildTagName.equals("tr")) {
					
					if (trChildTag.hasAttribute("th:each")) {
						
						trChildTag.setAttribute(DataTablesDialect.DIALECT_PREFIX + ":data", "internalUse");
						trChildTag.setRecomputeProcessorsImmediately(true);
						
						for(Node grandchild : trChildTag.getChildren()){
							
							if (grandchild != null && grandchild instanceof Element) {
								
								Element tdChildTag = (Element) grandchild;
								tdChildTag.setAttribute(DataTablesDialect.DIALECT_PREFIX + ":data", "internalUse");
								tdChildTag.setRecomputeProcessorsImmediately(true);
								
							}
						}
						
					} 
					
				} 
			}
		}
		
		return ProcessorResult.OK;
	}
}