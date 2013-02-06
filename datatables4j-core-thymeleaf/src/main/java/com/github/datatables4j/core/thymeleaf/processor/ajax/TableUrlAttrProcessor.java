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
package com.github.datatables4j.core.thymeleaf.processor.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.Arguments;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.IAttributeNameProcessorMatcher;
import org.thymeleaf.processor.ProcessorResult;

import com.github.datatables4j.core.api.model.HtmlTable;
import com.github.datatables4j.core.base.feature.AjaxFeature;
import com.github.datatables4j.core.thymeleaf.processor.AbstractDatatableAttrProcessor;
import com.github.datatables4j.core.thymeleaf.util.Utils;

/**
 * <p>
 * Attribute processor applied to the <tt>table</tt> tag for the <tt>url</tt>
 * attribute.
 * 
 * @author Thibault Duchateau
 */
public class TableUrlAttrProcessor extends AbstractDatatableAttrProcessor {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(TableUrlAttrProcessor.class);
		
	public TableUrlAttrProcessor(IAttributeNameProcessorMatcher matcher) {
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
		
		// Get the request
		HttpServletRequest request = ((IWebContext) arguments.getContext()).getHttpServletRequest();
		
		// Get HtmlTable POJO from the HttpServletRequest
		HtmlTable htmlTable = Utils.getTable(arguments);
				
		// Get attribute value
		String attrValue = element.getAttributeValue(attributeName).toLowerCase().trim();
		logger.debug("Extracted value : {}", attrValue);
		
		if(htmlTable != null && StringUtils.isNotBlank(attrValue)){
			// Same domain AJAX request
			if(attrValue.startsWith("/")){
				htmlTable.setDatasourceUrl(Utils.getBaseUrl(request) + attrValue);				
			}
			// Cross domain AJAX request
			else{
				htmlTable.setDatasourceUrl(attrValue);
			}
			
			// Thanks to the precedence of the serverside attribute processor,
			// we can already test if the server-side processing has been
			// enabled using the htmlTable bean
			if(htmlTable.getServerSide() == null || !htmlTable.getServerSide()){
				htmlTable.registerFeature(new AjaxFeature());
			}
		}
		
        return nonLenientOK(element, attributeName);
	}
}