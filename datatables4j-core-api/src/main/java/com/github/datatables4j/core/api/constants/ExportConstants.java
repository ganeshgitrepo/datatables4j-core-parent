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
package com.github.datatables4j.core.api.constants;

/**
 * DataTables4j export related constants.
 * 
 * @author Thibault Duchateau
 */
public class ExportConstants {

	/** Request attributes */

	// Export properties
	public static final String DT4J_EXPORT_PROPERTIES = "dt4j-export-properties";

	// Export content
	public static final String DT4J_EXPORT_CONTENT = "dt4j-export-content";

	/** Request parameters */

	// Table'is being exported
	public static final String DT4J_EXPORT_ID = "dt4ji";
		
	// Type of current export
	public static final String DT4J_EXPORT_TYPE = "dt4jt";
}