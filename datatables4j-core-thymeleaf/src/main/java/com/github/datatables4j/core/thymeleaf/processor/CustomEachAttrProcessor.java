package com.github.datatables4j.core.thymeleaf.processor;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.IAttributeNameProcessorMatcher;
import org.thymeleaf.processor.attr.AbstractIterationAttrProcessor;
import org.thymeleaf.standard.expression.Each;
import org.thymeleaf.standard.expression.Expression;
import org.thymeleaf.standard.expression.StandardExpressionProcessor;

import com.github.datatables4j.core.api.model.HtmlTable;

public class CustomEachAttrProcessor extends AbstractIterationAttrProcessor {

	public CustomEachAttrProcessor(String attributeName) {
		super(attributeName);
	}

	public CustomEachAttrProcessor(IAttributeNameProcessorMatcher matcher) {
		super(matcher);
	}

	@Override
	protected IterationSpec getIterationSpec(Arguments arguments, Element element,
			String attributeName) {

		// Get HtmlTable POJO from local variables
				HtmlTable htmlTable = (HtmlTable) arguments.getLocalVariable("htmlTable");
				System.out.println("test : " + htmlTable.toString());
				
		String attributeValue = element.getAttributeValue(attributeName);
        
        Each each = StandardExpressionProcessor.parseEach(arguments, attributeValue);

        Expression iterableExpression = each.getIterable();
        
        Object iteratedObject = StandardExpressionProcessor.executeExpression(arguments, iterableExpression);
        
        if (each.hasStatusVar()) {
            return new IterationSpec(each.getIterVar().getValue(), each.getStatusVar().getValue(), iteratedObject);
        }
        return new IterationSpec(each.getIterVar().getValue(), null, iteratedObject);
	}

	@Override
	protected void processClonedHostIterationElement(Arguments arguments, Element iteratedChild,
			String attributeName) {
		// Nothing to be done here, no additional processings for iterated elements
	}

	@Override
	public int getPrecedence() {
		return 5000;
	}
}
