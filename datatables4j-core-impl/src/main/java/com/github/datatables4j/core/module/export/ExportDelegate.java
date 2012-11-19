package com.github.datatables4j.core.module.export;

import com.github.datatables4j.core.api.exception.ExportException;
import com.github.datatables4j.core.api.model.ExportProperties;
import com.github.datatables4j.core.api.model.HtmlTable;

/**
 * TODO
 *
 * @author Thibault Duchateau
 */
public class ExportDelegate {

	private HtmlTable htmlTable;
	
	public ExportDelegate(HtmlTable htmlTable){
		this.htmlTable = htmlTable;
	}
	
	/**
	 * TODO
	 * 
	 * @param properties
	 * @return
	 * @throws ExportException
	 */
	public Object getExportContent(ExportProperties properties) throws ExportException{
	
		Object content = null;
		
		switch (htmlTable.getExportProperties().getCurrentExportType()) {
		case CSV:
			
			CsvExport csvExport = new CsvExport();
			csvExport.initExport(htmlTable);
			content = String.valueOf(csvExport.processExport());
			
			break;
		case HTML:
			break;
		case PDF:
			break;
		case XLS:
			break;
		case XML:
			XmlExport xmlExport = new XmlExport();
			xmlExport.initExport(htmlTable);
			content = String.valueOf(xmlExport.processExport());
			break;
		default:
			break;
		
		}

		return content;
	}
}
