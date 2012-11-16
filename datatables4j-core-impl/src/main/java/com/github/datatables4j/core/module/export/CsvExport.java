package com.github.datatables4j.core.module.export;

import java.io.OutputStream;

import com.github.datatables4j.core.api.model.HtmlColumn;
import com.github.datatables4j.core.api.model.HtmlRow;
import com.github.datatables4j.core.api.model.HtmlTable;
import com.github.datatables4j.core.api.module.export.BaseExport;

public class CsvExport implements BaseExport {

	private HtmlTable table;
	
	@Override
	public void initExport(HtmlTable table) {
		this.table = table;
	}

	@Override
	public Object processExport(OutputStream out) {
		
		StringBuffer buffer = new StringBuffer();
		
		for(HtmlRow row : table.getBodyRows()){
			
			for(HtmlColumn column : row.getColumns()){
				buffer.append(column.getContent()).append(";");
			}
			
			buffer.append("\n");
		}
		
		return buffer.toString();
	}
}