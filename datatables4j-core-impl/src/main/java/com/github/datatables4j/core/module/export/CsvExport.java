package com.github.datatables4j.core.module.export;

import com.github.datatables4j.core.api.exception.ExportException;
import com.github.datatables4j.core.api.model.ExportType;
import com.github.datatables4j.core.api.model.HtmlColumn;
import com.github.datatables4j.core.api.model.HtmlRow;
import com.github.datatables4j.core.api.model.HtmlTable;
import com.github.datatables4j.core.api.module.export.AbstractExport;

public class CsvExport extends AbstractExport {

	private HtmlTable table;
	
	@Override
	public void initExport(HtmlTable table) {
		this.table = table;
	}

	@Override
	public Object processExport() throws ExportException {
	
		StringBuffer buffer = new StringBuffer();

		if(table.getExportConfMap().get(ExportType.CSV).getIncludeHeader()){
			for(HtmlRow row : table.getHeadRows()){
				for(HtmlColumn column : row.getColumns()){
					buffer.append(column.getContent()).append(";");
				}
				buffer.append("\n");
			}			
		}
		for(HtmlRow row : table.getBodyRows()){
			for(HtmlColumn column : row.getColumns()){
				buffer.append(column.getContent()).append(";");
			}
			
			buffer.append("\n");
		}
		
		return buffer.toString();
	}
}