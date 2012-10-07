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
package org.datatables4j.core.api.exception;

public class DataNotFoundException extends Throwable {

	private static final long serialVersionUID = 7240738976355836256L;

	public DataNotFoundException() {
	};

	/**
	 * Crée une nouvelle instance de NombreNonValideException
	 * 
	 * @param message
	 *            Le message détaillant exception
	 */
	public DataNotFoundException(String message) {
		super(message);
	}

	/**
	 * Crée une nouvelle instance de NombreNonValideException
	 * 
	 * @param cause
	 *            L'exception à l'origine de cette exception
	 */
	public DataNotFoundException(Throwable cause) {
		super(cause);
	}

	/**
	 * Crée une nouvelle instance de NombreNonValideException
	 * 
	 * @param message
	 *            Le message détaillant exception
	 * @param cause
	 *            L'exception à l'origine de cette exception
	 */
	public DataNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
