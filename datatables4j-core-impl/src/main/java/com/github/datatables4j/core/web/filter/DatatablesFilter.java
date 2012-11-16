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
import javax.servlet.http.HttpServletResponse;

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
		System.out.println(" ===================== DEBUT init");

		// TODO configuration dynamique de l'urlPattern en fonction de la conf
		// Datatables4j ?

		System.out.println(" ===================== FIN init");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println(" ===================== DEBUT doFilter");

		// Le param "exporting" est mis en request par la classe
		// AbstractTableTag si l'attribut export est a true dans la JSP

		// Don't filter anything
		if (request.getParameter("exporting") == null) {

			chain.doFilter(request, response);
		} else {

			// TODO : utiliser des constantes
			request.setAttribute("isExporting", true);

			HttpServletResponse res = (HttpServletResponse) response;
			DatatablesResponseWrapper resWrapper = new DatatablesResponseWrapper(res);

			chain.doFilter(request, resWrapper);

			// TODO : variabiliser : configuration DT4J ?
			res.setHeader("Content-Disposition", "attachment; filename=\"" + "toto.csv" + "\"");

			// TODO : recuperer en fonction du lien d'export
			res.setContentType("text/csv");

			// TODO : utiliser des constantes
			String content = String.valueOf(request.getAttribute("data"));
			System.out.println("content = " + content);

			// TODO : printWriter pour flux text, outputStream pour flux binaire
			PrintWriter out = response.getWriter();
			out.write(content);
			out.flush();
		}

		System.out.println(" ===================== FIN doFilter");
	}

	@Override
	public void destroy() {

	}
}