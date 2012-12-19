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
package com.github.datatables4j.core.thymeleaf.processor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.thymeleaf.Arguments;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.attr.AbstractAttrProcessor;

public class TableIdAttrProcessor extends AbstractAttrProcessor {

	public TableIdAttrProcessor() {
		// Only execute this processor for 'sayto' attributes.
		super("tableid");
	}

	public int getPrecedence() {
		// A value of 10000 is higher than any attribute in the
		// SpringStandard dialect. So this attribute will execute
		// after all other attributes from that dialect, if in the
		// same tag.
		return 10000;
	}

	@Override
	protected ProcessorResult processAttribute(Arguments arguments, Element element,
			String attributeName) {
		
		// Comment recuperer le context web
		final IContext context = arguments.getContext();
		final IWebContext webContext = (IWebContext) context;
        
        final HttpServletRequest request = webContext.getHttpServletRequest();
        final ServletContext servletContext = webContext.getServletContext();
        
		System.out.println("arguments = " + arguments);
		System.out.println("element = " + element.getOriginalName());
		System.out.println("attributeName = " + attributeName);
		
		String attributeValue = element.getAttributeValue(attributeName);
		System.out.println("attributeValue = " + attributeValue);
		
		System.out.println("Table id = " + ((Element)element.getParent()).getAttributeValue("id"));
		
		Element script = new Element("script");
		script.addChild(new Text("$(document).ready(function() {$('#myTable').dataTable();});", false));
        element.getParent().insertAfter(element, script);
		
		// Housekeeping
        element.removeAttribute(attributeName);
        
		return ProcessorResult.OK;
	}
}
