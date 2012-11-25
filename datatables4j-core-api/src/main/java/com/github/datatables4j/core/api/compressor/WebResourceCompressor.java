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
package com.github.datatables4j.core.api.compressor;

import com.github.datatables4j.core.api.exception.CompressionException;
import com.github.datatables4j.core.api.model.HtmlTable;

/**
 * Compressor for web resources (Javascript, Stylesheets).
 * 
 * @author Thibault Duchateau
 */
public interface WebResourceCompressor {

	/**
	 * Return as String a compressed version of the given Javascript code.
	 * 
	 * @param table
	 *            The table containing, among others, the compression options.
	 * @param input
	 *            The Javascript code to compress.
	 * @return The Javascript code compressed.
	 * @throws CompressionException
	 *             if the String containing Javascript code is malformed or
	 *             cannot be evaluated.
	 */
	public String getCompressedJavascript(HtmlTable table, String input)
			throws CompressionException;

	/**
	 * Return as String a compressed version of the given CSS code.
	 * 
	 * @param input
	 *            The CSS code to compress.
	 * @return The CSS code compressed.
	 * @throws CompressionException
	 *             if the String containing CSS is malformed.
	 */
	public String getCompressedCss(String input) throws CompressionException;
}