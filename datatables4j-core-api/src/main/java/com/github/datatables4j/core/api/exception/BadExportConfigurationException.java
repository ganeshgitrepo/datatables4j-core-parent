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
package com.github.datatables4j.core.api.exception;

/**
 * Raised if there's something wrong in the datatables4j export configuration file.
 * 
 * @author Thibault Duchateau
 */
public class BadExportConfigurationException extends BadConfigurationException {

	private static final long serialVersionUID = 3243845798907773547L;

	public BadExportConfigurationException() {
	};

	public BadExportConfigurationException(String message) {
		super(message);
	}

	public BadExportConfigurationException(Throwable cause) {
		super(cause);
	}

	public BadExportConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}
}
