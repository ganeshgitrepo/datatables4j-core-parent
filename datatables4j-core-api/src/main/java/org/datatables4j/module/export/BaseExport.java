package org.datatables4j.module.export;

import java.io.OutputStream;

import org.datatables4j.model.HtmlTable;

public interface BaseExport {

	public void initExport(HtmlTable table);
	
	public void processExport(OutputStream out);
}
