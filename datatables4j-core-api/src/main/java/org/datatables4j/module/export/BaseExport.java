package org.datatables4j.module.export;

import java.io.OutputStream;

import org.datatables4j.model.HtmlTable;

/**
 * Contract for every export implementations.
 * 
 * @author Thibault Duchateau
 */
public interface BaseExport {

	/**
	 * Initialize the implementation classes with all needed informations.
	 * 
	 * @param table The HTML table containing all needed informations for the export.
	 */
	public void initExport(HtmlTable table);
	
	/**
	 * The main export method that is called by DataTables4j.
	 * 
	 * @param out The stream to send back to client.
	 */
	public void processExport(OutputStream out);
}
