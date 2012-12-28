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
package com.github.datatables4j.core.thymeleaf.dialect;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.AttributeNameProcessorMatcher;
import org.thymeleaf.processor.IProcessor;

import com.github.datatables4j.core.thymeleaf.processor.attribute.EnablingDatatableAttrProcessor;
import com.github.datatables4j.core.thymeleaf.processor.attribute.TableAutoWidthAttrProcessor;
import com.github.datatables4j.core.thymeleaf.processor.attribute.TableCdnAttrProcessor;
import com.github.datatables4j.core.thymeleaf.processor.attribute.TableExportAttrProcessor;
import com.github.datatables4j.core.thymeleaf.processor.attribute.TableFilterAttrProcessor;
import com.github.datatables4j.core.thymeleaf.processor.attribute.TableInfoAttrProcessor;
import com.github.datatables4j.core.thymeleaf.processor.attribute.TablePaginateAttrProcessor;
import com.github.datatables4j.core.thymeleaf.processor.attribute.TableSortAttrProcessor;
import com.github.datatables4j.core.thymeleaf.processor.attribute.TableThemeAttrProcessor;
import com.github.datatables4j.core.thymeleaf.processor.attribute.ThFilterTypeAttrProcessor;
import com.github.datatables4j.core.thymeleaf.processor.attribute.ThFilterableAttrProcessor;
import com.github.datatables4j.core.thymeleaf.processor.attribute.ThSortableAttrProcessor;
import com.github.datatables4j.core.thymeleaf.processor.attribute.TheadScrollerAttrProcessor;
import com.github.datatables4j.core.thymeleaf.processor.element.ColumnInitializerElProcessor;
import com.github.datatables4j.core.thymeleaf.processor.element.DivElProcessor;
import com.github.datatables4j.core.thymeleaf.processor.element.TableFinalizerElProcessor;
import com.github.datatables4j.core.thymeleaf.processor.element.TableInitializerElProcessor;
import com.github.datatables4j.core.thymeleaf.processor.element.TbodyElProcessor;
import com.github.datatables4j.core.thymeleaf.processor.element.TdElProcessor;
import com.github.datatables4j.core.thymeleaf.processor.element.TrElProcessor;
import com.github.datatables4j.core.thymeleaf.processor.matcher.ElementNameWithoutPrefixProcessorMatcher;

/**
 * DataTables4j dialect.
 * 
 * @author Thibault Duchateau
 */
public class DataTablesDialect extends AbstractDialect {

	public static final String DIALECT_PREFIX = "dt";
	public static final String LAYOUT_NAMESPACE = "http://github.com/datatables4j/thymeleaf/dt4j";
	public static final int DT_HIGHEST_PRECEDENCE = 3500;
	
	/**
	 * DataTablesDialect has no prefix, in order to be able to match native HTML
	 * tags as <code>table</code> or <code>tbody</code>.
	 */
	public String getPrefix() {
		return DIALECT_PREFIX;
	}

	public boolean isLenient() {
		return false;
	}

	/*
	 * The processors.
	 */
	@Override
	public Set<IProcessor> getProcessors() {
		final Set<IProcessor> processors = new HashSet<IProcessor>();
		
		processors.add(new TableInitializerElProcessor(new ElementNameWithoutPrefixProcessorMatcher("table", DIALECT_PREFIX + ":table", "true")));
//		processors.add(new TableFinalizerElProcessor(new ElementNameWithoutPrefixProcessorMatcher("tbody")));
		processors.add(new TableFinalizerElProcessor(new ElementNameWithoutPrefixProcessorMatcher("div", DIALECT_PREFIX + ":tmp", "internalUse")));
		processors.add(new ColumnInitializerElProcessor(new ElementNameWithoutPrefixProcessorMatcher("th")));

//		processors.add(new DivElProcessor(new ElementNameWithoutPrefixProcessorMatcher("div", DIALECT_PREFIX + ":tmp", "internalUse")));
		
		processors.add(new TbodyElProcessor(new ElementNameWithoutPrefixProcessorMatcher("tbody")));
		processors.add(new TrElProcessor(new ElementNameWithoutPrefixProcessorMatcher("tr", DIALECT_PREFIX + ":data", "internalUse")));
		processors.add(new TdElProcessor(new ElementNameWithoutPrefixProcessorMatcher("td", DIALECT_PREFIX + ":data", "internalUse")));
		
//		processors.add(new CustomEachAttrProcessor(new AttributeNameProcessorMatcher("tr", "each")));
		
		processors.add(new EnablingDatatableAttrProcessor(new AttributeNameProcessorMatcher("table", "table")));
		processors.add(new TableAutoWidthAttrProcessor(new AttributeNameProcessorMatcher("autoWidth", "table")));
		processors.add(new TableCdnAttrProcessor(new AttributeNameProcessorMatcher("cdn", "table")));
		processors.add(new TableFilterAttrProcessor(new AttributeNameProcessorMatcher("filter", "table")));
		processors.add(new TableInfoAttrProcessor(new AttributeNameProcessorMatcher("info", "table")));
//		processors.add(new TableLengthChangeAttrProcessor(new AttributeNameProcessorMatcher("lengthPaginate", "table")));
		processors.add(new TablePaginateAttrProcessor(new AttributeNameProcessorMatcher("paginate", "table")));
//		processors.add(new TablePaginationTypeAttrProcessor(new AttributeNameProcessorMatcher("paginationType", "table")));
		processors.add(new TableSortAttrProcessor(new AttributeNameProcessorMatcher("sort", "table")));
		processors.add(new TableThemeAttrProcessor(new AttributeNameProcessorMatcher("theme", "table")));

		processors.add(new TableExportAttrProcessor(new AttributeNameProcessorMatcher("export", "table")));

		// Plugins attributes
		processors.add(new TheadScrollerAttrProcessor(new AttributeNameProcessorMatcher("scroller", "thead")));
		
		// Header column attributes
		processors.add(new ThSortableAttrProcessor(new AttributeNameProcessorMatcher("sortable", "th")));
		processors.add(new ThFilterableAttrProcessor(new AttributeNameProcessorMatcher("filterable", "th")));
		processors.add(new ThFilterTypeAttrProcessor(new AttributeNameProcessorMatcher("filterType", "th")));
		
		return processors;
	}
}
