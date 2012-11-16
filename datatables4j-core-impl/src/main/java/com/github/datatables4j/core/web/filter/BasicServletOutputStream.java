package com.github.datatables4j.core.web.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;

/**
 * TODO
 *
 * @author Thibault Duchateau
 */
public class BasicServletOutputStream extends ServletOutputStream {

	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	
	@Override
	public void write(int b) throws IOException {
		this.outputStream.write(b);
	}
}
