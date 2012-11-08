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
package com.github.datatables4j.core.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.datatables4j.core.api.model.CssResource;
import com.github.datatables4j.core.api.model.JsResource;

/**
 * Main DataTables4j servlet which serves web resources.
 * 
 * @author Thibault Duchateau
 */
@WebServlet(name="datatablesController", urlPatterns={"/datatablesController/*"})
public class DatatablesServlet extends HttpServlet {
	private static final long serialVersionUID = 4971523176859296399L;

	// Logger
	private static Logger logger = LoggerFactory.getLogger(DatatablesServlet.class);

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.debug("DataTables servlet captured GET request {}", request.getRequestURI());
		
		// Common response header
		// TODO adapt caching behaviour depending on the file nature (e.g. plugin)
		response.setHeader("Cache-Control","no-cache");
		
		// Get requested file name
		StringBuffer resourceUrl = request.getRequestURL();
		String resourceName = resourceUrl.substring(resourceUrl.lastIndexOf("/") + 1);
		String fileContent = null;
		
		// Depending on its type, different content is served
		if(resourceName.endsWith("js")){
			
			// Set header properties
			response.setContentType("application/javascript");
			
			fileContent = ((JsResource) getServletContext().getAttribute(resourceName)).getContent();
		}
		else if(resourceName.endsWith("css")){
			
			// Set header properties
			response.setContentType("text/css");
			
			fileContent = ((CssResource) getServletContext().getAttribute(resourceName)).getContent();
		}
				
		// Write the content in the response
		response.getWriter().write(fileContent);

		// Clear the servlet context
		getServletContext().removeAttribute(resourceName);
	}
}