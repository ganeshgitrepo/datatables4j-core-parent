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

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.datatables4j.core.api.constants.ExportConstants;
import com.github.datatables4j.core.api.model.ExportProperties;

/**
 * TODO
 * 
 * Problematique : si le tableau genere avec DT4J est en cours d'export, la
 * reponse ne doit pas renvoyer du HTML mais des donnees dans le format souhaite
 * par l'utilisateur (CSV, XML, ...).<br />
 * Si on surcharge la response avec les donnees (en mettant a jour le
 * content-type et en recuperant un writer pour reecrire le contenu de l'export
 * dedans, on tombe sur l'erreur java.lang.IllegalStateException:
 * getOutputStream()|getWriter() has already been called for this response.<br />
 * 
 * @author Thibault Duchateau
 */
@WebFilter(filterName = "DataTables4jFilter", value = { "/*" })
public class DatatablesFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
		// TODO configuration dynamique de l'urlPattern en fonction de la conf
		// Datatables4j ?
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {

		// Le param "exporting" est mis en request par la classe
		// AbstractTableTag si l'attribut export est a true dans la JSP
		// Si pas de parametre dans l'URL, le filtre ne doit rien faire

		if (servletRequest instanceof HttpServletRequest) {

			HttpServletRequest request = (HttpServletRequest) servletRequest;

			// Don't filter anything
			 if (request.getParameter(ExportConstants.DT4J_EXPORT_ID) == null) {

				chain.doFilter(servletRequest, servletResponse);
				
			} else {

				// Flag set in request to tell the taglib to export the table instead of displaying it
				request.setAttribute(ExportConstants.DT4J_EXPORT_ID, request.getParameter(ExportConstants.DT4J_EXPORT_ID));
				
				HttpServletResponse response = (HttpServletResponse) servletResponse;
				DatatablesResponseWrapper resWrapper = new DatatablesResponseWrapper(response);

				chain.doFilter(request, resWrapper);

				ExportProperties exportProperties = (ExportProperties) request
						.getAttribute(ExportConstants.DT4J_EXPORT_PROPERTIES);
				String fileName = exportProperties.getFileName() + "."
						+ exportProperties.getCurrentExportType().getExtension();
				System.out.println("fileName = " + fileName);

				// TODO : variabiliser : configuration DT4J ?
				response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName
						+ "\"");

				// TODO : recuperer en fonction du lien d'export
				response.setContentType(exportProperties.getCurrentExportType().getMimeType());

				// TODO : utiliser des constantes
				String content = String.valueOf(servletRequest
						.getAttribute(ExportConstants.DT4J_EXPORT_CONTENT));

				// TODO : printWriter pour flux text, outputStream pour flux
				// binaire
				PrintWriter out = servletResponse.getWriter();
				out.write(content);
				out.flush();
				out.close();
			}

		} else {
			chain.doFilter(servletRequest, servletResponse);
		}
	}

	@Override
	public void destroy() {
		// Nothing to do
	}
}