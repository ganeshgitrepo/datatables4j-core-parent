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
package com.github.datatables4j.core.base.export;

import java.io.IOException;
import java.io.Writer;

import com.github.datatables4j.core.api.exception.ExportException;
import com.github.datatables4j.core.api.model.DisplayType;
import com.github.datatables4j.core.api.model.ExportType;
import com.github.datatables4j.core.api.model.HtmlColumn;
import com.github.datatables4j.core.api.model.HtmlRow;
import com.github.datatables4j.core.api.model.HtmlTable;
import com.github.datatables4j.core.api.module.export.AbstractCharExport;

/**
 * 
 *
 * @author Thibault Duchateau
 */
public class CsvExport extends AbstractCharExport {

	private static final DisplayType CURRENT_DISPLAY_TYPE = DisplayType.CSV;
	private static final String SEPARATOR_CHAR = ";";
	private HtmlTable table;

	@Override
	public void initExport(HtmlTable table) {
		this.table = table;
	}

	@Override
	public void processExport(Writer output) throws ExportException {
		StringBuffer buffer = new StringBuffer();

		if(table.getExportConfMap().get(ExportType.CSV).getIncludeHeader()){
			for(HtmlRow row : table.getHeadRows()){
				for(HtmlColumn column : row.getColumns()){
					if(column.getEnabledDisplayTypes().contains(DisplayType.ALL) || column.getEnabledDisplayTypes().contains(CURRENT_DISPLAY_TYPE)){
						buffer.append(column.getContent()).append(SEPARATOR_CHAR);						
					}
				}
				buffer.append("\n");
			}			
		}
		for(HtmlRow row : table.getBodyRows()){
			for(HtmlColumn column : row.getColumns()){
				if(column.getEnabledDisplayTypes().contains(DisplayType.ALL) || column.getEnabledDisplayTypes().contains(CURRENT_DISPLAY_TYPE)){
					buffer.append(column.getContent()).append(SEPARATOR_CHAR);					
				}
			}
			
			buffer.append("\n");
		}
		
		try {
			output.write(buffer.toString());
		} catch (IOException e) {
			throw new ExportException(e);
		}
	}
}