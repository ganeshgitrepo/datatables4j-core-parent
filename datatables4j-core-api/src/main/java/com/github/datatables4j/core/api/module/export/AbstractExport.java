/*
 * DataTables4j, a JSP taglib to display table with jQuery and DataTables
 * Copyright (c) 2012, DataTables4j <datatables4j@gmail.com>
 *
 * This Program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License, version 2, as
 * published by the Free Software Foundation.
 * 
 * The Program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package com.github.datatables4j.core.api.module.export;

import com.github.datatables4j.core.api.exception.ExportException;
import com.github.datatables4j.core.api.model.HtmlTable;

/**
 * Contract for every export implementations.
 * 
 * @author Thibault Duchateau
 */
public abstract class AbstractExport {

	/**
	 * Initialize the implementation classes with all needed informations.
	 * Usually, only the HtmlTable is needed, because it already contains lots
	 * of information.
	 * 
	 * @param table
	 *            The HTML table containing all needed informations for the
	 *            export.
	 */
	public abstract void initExport(HtmlTable table);

	/**
	 * The main export method that is called by DataTables4j.
	 * 
	 * @return the exported content to send to the client.
	 * @throws ExportException
	 *             if something goes wrong during export.
	 */
	public abstract Object processExport() throws ExportException;
}
