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
package com.github.datatables4j.core.base.feature;

import com.github.datatables4j.core.api.constants.DTConstants;
import com.github.datatables4j.core.api.exception.BadConfigurationException;
import com.github.datatables4j.core.api.model.AbstractFeature;
import com.github.datatables4j.core.api.model.Configuration;
import com.github.datatables4j.core.api.model.HtmlTable;
import com.github.datatables4j.core.api.model.JavascriptSnippet;

/**
 * TODO
 * 
 * @author Thibault Duchateau
 * @since 0.8.2
 */
public class AjaxFeature extends AbstractFeature {

	@Override
	public String getName() {
		return null;
	}

	@Override
	public String getVersion() {
		return null;
	}

	@Override
	public void setup(HtmlTable table) throws BadConfigurationException {
		addConfiguration(new Configuration(DTConstants.DT_B_DEFER_RENDER, true));
		addConfiguration(new Configuration(DTConstants.DT_S_AJAX_SOURCE, table.getDatasourceUrl()));
		addConfiguration(new Configuration("fnInitComplete", new JavascriptSnippet("function() { oTable_" + table.getId() + ".fnAdjustColumnSizing(true);}")));
	}
}