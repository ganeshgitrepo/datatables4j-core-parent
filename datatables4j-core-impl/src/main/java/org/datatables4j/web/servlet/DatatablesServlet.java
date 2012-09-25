package org.datatables4j.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.datatables4j.model.DataTablesResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main DataTables4j servlet which serve web resources.
 * 
 * @author Thibault Duchateau
 */
@WebServlet(urlPatterns = {"/datatablesController/*"}, name = "datatablesController")
public class DatatablesServlet extends HttpServlet {
	private static final long serialVersionUID = 4971523176859296399L;

	// Logger
	private static Logger logger = LoggerFactory.getLogger(DatatablesServlet.class);

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.debug("DataTables servlet captured GET request");
		
		// Common response header
		response.setHeader("Cache-Control","no-cache");
		
		// Get requested file name
		StringBuffer resourceUrl = request.getRequestURL();
		String resourceName = resourceUrl.substring(resourceUrl.lastIndexOf("/") + 1);
		
		// Depending on its type, different content is served
		if(resourceName.endsWith("js")){
			
			// Set header properties
			response.setContentType("application/javascript");
		}
		else if(resourceName.endsWith("css")){
			
			// Set header properties
			response.setContentType("text/css");
		}
		
		String fileContent = ((DataTablesResource) getServletContext().getAttribute(resourceName)).getContent();
		logger.debug("fileContent = {}", fileContent);
		
		// Write the content in the response
		response.getWriter().write(fileContent);

		// Clear the servlet context
		getServletContext().removeAttribute(resourceName);
		
		// TODO if removed, other resources won't be returned back
//		getServletContext().removeAttribute("webResources");
	}
}