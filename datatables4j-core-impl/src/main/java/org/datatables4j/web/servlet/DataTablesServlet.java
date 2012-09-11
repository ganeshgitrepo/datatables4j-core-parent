package org.datatables4j.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class TestJavascript
 */
@WebServlet(urlPatterns = {"/datatablesController/datatables4j.js"}, name = "datatablesController")
public class DataTablesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger logger = LoggerFactory.getLogger(DataTablesServlet.class);
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Keep in mind that all of those classes are from SLF4J package!
		logger.debug("DataTables servlet captured GET request");
		
		// Set header properties
		response.setHeader("Cache-Control","no-cache");
		response.setContentType("application/javascript");
		
		logger.debug("Serving datatables4j.js ...");
		
		// Write the Javascript DataTables configuration in the response
        response.getWriter().write(getServletContext().getAttribute("jsToLoad").toString());
        
        // Clear the servlet context
        getServletContext().removeAttribute("jsToLoad");
        
        logger.debug("Servlet context cleaned");
	}
}