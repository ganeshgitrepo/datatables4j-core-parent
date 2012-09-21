package org.datatables4j.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.datatables4j.model.WebResources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class TestJavascript
 */
@WebServlet(urlPatterns = {"/datatablesController/"}, name = "datatablesController")
public class DataTablesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Logger
	private static Logger logger = LoggerFactory.getLogger(DataTablesServlet.class);

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.debug("DataTables servlet captured GET request");

		// Get web resources from servlet context
		WebResources webResources = (WebResources) getServletContext().getAttribute("webResources");
		
		// Common response header
		response.setHeader("Cache-Control","no-cache");
		
		// Get requested file name
		String fileToServe = request.getParameter("file");
		logger.debug("fileToServe = {}", fileToServe);
		String fileContent = null;
		
		// Depending on its type, different content is served
		if(fileToServe.endsWith("js")){
			
			// Set header properties
			response.setContentType("application/javascript");
			
			// Write the content in the response
			fileContent = webResources.getJavascripts().get(fileToServe).getContent();
		}
		else if(fileToServe.endsWith("css")){
			
			// Set header properties
			response.setContentType("text/css");
			
			// Write the content in the response
			fileContent = webResources.getStylesheets().get(fileToServe).getContent();
		}
		
		logger.debug("fileContent = {}", fileContent);
		// Write the content in the response
		response.getWriter().write(fileContent);
		
//		response.flushBuffer();
	}
}