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
package com.github.datatables4j.core.web.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * TODO
 *
 * @author Thibault Duchateau
 */
public class DatatablesResponseWrapper extends HttpServletResponseWrapper {

	protected HttpServletResponse originalResponse = null;
	protected ServletOutputStream stream = null;

	public DatatablesResponseWrapper(HttpServletResponse response) {
		super(response);
		originalResponse = response;
	}

	public ServletOutputStream createOutputStream() throws IOException {
		return (new BasicServletOutputStream());
	}

	public ServletOutputStream getOutputStream() throws IOException {
		stream = createOutputStream();
		return stream;
	}

	public PrintWriter getWriter() throws IOException {
		stream = createOutputStream();
		return new PrintWriter(stream);
	}
}