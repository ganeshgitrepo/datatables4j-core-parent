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