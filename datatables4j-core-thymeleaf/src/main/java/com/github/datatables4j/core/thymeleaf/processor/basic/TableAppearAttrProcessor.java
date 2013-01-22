/*
 * [The "BSD licence"]
 * Copyright (c) 2012 DataTables4j
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 3. Neither the name of DataTables4j nor the names of its contributors 
 * may be used to endorse or promote products derived from this software 
 * without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.github.datatables4j.core.thymeleaf.processor.basic;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.IAttributeNameProcessorMatcher;
import org.thymeleaf.processor.ProcessorResult;

import com.github.datatables4j.core.api.model.HtmlTable;
import com.github.datatables4j.core.thymeleaf.processor.AbstractDatatableAttrProcessor;
import com.github.datatables4j.core.thymeleaf.util.Utils;

/**
 * <p>
 * Attribute processor applied to the <tt>table</tt> tag for the <tt>appear</tt>
 * attribute.
 * 
 * @author Thibault Duchateau
 */
public class TableAppearAttrProcessor extends AbstractDatatableAttrProcessor {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(TableAppearAttrProcessor.class);
		
	public TableAppearAttrProcessor(IAttributeNameProcessorMatcher matcher) {
		super(matcher);
	}

	@Override
	public int getPrecedence() {
		return 8000;
	}
	
	@Override
	protected ProcessorResult processAttribute(Arguments arguments, Element element,
			String attributeName) {
		logger.debug("{} attribute found", attributeName);
		
		// Get HtmlTable POJO from the HttpServletRequest
		HtmlTable htmlTable = Utils.getTable(arguments);
				
		// Get attribute value
		String attrValue = element.getAttributeValue(attributeName).toLowerCase().trim();
		logger.debug("Extracted value : {}", attrValue);
		
		if(htmlTable != null && StringUtils.isNotBlank(attrValue)){
			if(attrValue.contains(",") || "fadein".equals(attrValue.toLowerCase().trim())){
				String[] tmp = attrValue.toLowerCase().trim().split(",");
				htmlTable.setAppear("fadein");
				if(tmp.length > 1){
					htmlTable.setAppearDuration(tmp[1]);
				}
			}
			else{
				htmlTable.setAppear("block");
			}
		}
		
        return nonLenientOK(element, attributeName);
	}
}