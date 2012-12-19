/*
 * [The "BSD licence"]
 * Copyright (c) 2012 DataTables4j
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 3. Neither the name of DataTables4j nor the names of its contributors 
 * may be used to endorse or promote products derived from this software 
 * without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.github.datatables4j.core.base.export;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.datatables4j.core.api.constants.ExportConstants;
import com.github.datatables4j.core.api.exception.BadConfigurationException;
import com.github.datatables4j.core.api.exception.ExportException;
import com.github.datatables4j.core.api.model.ExportProperties;
import com.github.datatables4j.core.api.model.HtmlTable;
import com.github.datatables4j.core.base.util.ReflectHelper;

/**
 * Delegate class in charge of launching export.
 * 
 * @author Thibault Duchateau
 */
public class ExportDelegate {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(ExportDelegate.class);

	private HtmlTable htmlTable;
	private ExportProperties exportProperties;
	private HttpServletRequest request;

	public ExportDelegate(HtmlTable htmlTable, ExportProperties exportProperties,
			HttpServletRequest request) {
		this.htmlTable = htmlTable;
		this.exportProperties = exportProperties;
		this.request = request;
	}

	public void setupExport() throws ExportException {

		OutputStream stream = null;
		StringWriter writer = null;

		switch (htmlTable.getExportProperties().getCurrentExportType()) {
		case CSV:

			exportProperties.setIsBinaryExport(false);
			writer = new StringWriter();
			CsvExport csvExport = new CsvExport();
			csvExport.initExport(htmlTable);
			csvExport.processExport(writer);

			break;
		case HTML:
			break;
		case PDF:

			checkPdfDependencies();
			exportProperties.setIsBinaryExport(true);
			stream = new ByteArrayOutputStream();

			try {

				// Get the class
				Class<?> klass = ReflectHelper
						.getClass("com.github.datatables4j.plugins.export.itext.PdfExport");

				// Get new instance of this class
				Object obj = ReflectHelper.getNewInstance(klass);

				// Invoke methods
				ReflectHelper.invokeMethod(obj, "initExport", new Object[] { htmlTable });
				ReflectHelper.invokeMethod(obj, "processExport", new Object[] { stream });

			} catch (BadConfigurationException e) {
				throw new ExportException(e);
			}

			break;
		case XLS:

			checkXlsDependencies();
			exportProperties.setIsBinaryExport(true);
			stream = new ByteArrayOutputStream();

			try {

				// Get the class
				Class<?> klass = ReflectHelper.getClass(htmlTable.getTableProperties()
						.getDefaultXlsExportClassName());

				// Get new instance of this class
				Object obj = ReflectHelper.getNewInstance(klass);

				// Invoke methods
				ReflectHelper.invokeMethod(obj, "initExport", new Object[] { htmlTable });
				ReflectHelper.invokeMethod(obj, "processExport", new Object[] { stream });

			} catch (BadConfigurationException e) {
				throw new ExportException(e);
			}

			break;

		case XLSX:

			checkXlsxDependencies();
			exportProperties.setIsBinaryExport(true);
			stream = new ByteArrayOutputStream();

			try {

				// Get the class
				// Get the class
				Class<?> klass = ReflectHelper.getClass(htmlTable.getTableProperties()
						.getDefaultXlsxExportClassName());

				// Get new instance of this class
				Object obj = ReflectHelper.getNewInstance(klass);

				// Invoke methods
				ReflectHelper.invokeMethod(obj, "initExport", new Object[] { htmlTable });
				ReflectHelper.invokeMethod(obj, "processExport", new Object[] { stream });

			} catch (BadConfigurationException e) {
				throw new ExportException(e);
			}

			break;

		case XML:

			exportProperties.setIsBinaryExport(false);
			writer = new StringWriter();
			XmlExport xmlExport = new XmlExport();
			xmlExport.initExport(htmlTable);
			xmlExport.processExport(writer);

			break;
		default:
			break;

		}

		// System.out.println("stream = " + stream.toString());
		// Fill the request so that the filter will intercept it and
		// override the response with the export configuration
		if (exportProperties.isBinaryExport()) {
			request.setAttribute(ExportConstants.DT4J_EXPORT_CONTENT,
					((ByteArrayOutputStream) stream).toByteArray());
		} else {
			request.setAttribute(ExportConstants.DT4J_EXPORT_CONTENT, writer.toString());
		}
		request.setAttribute(ExportConstants.DT4J_EXPORT_PROPERTIES, exportProperties);
	}

	/**
	 * Check if the default xls export class exists in the classpath. Throws an
	 * exception otherwise.
	 * 
	 * @throws ExportException
	 *             if the class cannot be instanciated.
	 */
	private void checkXlsDependencies() throws ExportException {

		if (!ReflectHelper.canBeUsed(htmlTable.getTableProperties().getDefaultXlsExportClassName())) {
			logger.error("Did you forget to add a dependency ?");
			throw new ExportException("Unable to export in XLS format");
		}
	}

	/**
	 * Check if the default xlsx export class exists in the classpath. Throws an
	 * exception otherwise.
	 * 
	 * @throws ExportException
	 *             if the class cannot be instanciated.
	 */
	private void checkXlsxDependencies() throws ExportException {

		if (!ReflectHelper
				.canBeUsed(htmlTable.getTableProperties().getDefaultXlsxExportClassName())) {
			logger.error("Did you forget to add a dependency ?");
			throw new ExportException("Unable to export in XLSX format");
		}
	}

	/**
	 * Check if the default pdf export classes exists in the classpath. Throws
	 * an exception otherwise.
	 * 
	 * @throws ExportException
	 *             if the class cannot be instanciated.
	 */
	private void checkPdfDependencies() throws ExportException {

		if (!ReflectHelper
				.canBeUsed(htmlTable.getTableProperties().getDefaultPdfExportClassName1())) {
			if (!ReflectHelper.canBeUsed(htmlTable.getTableProperties()
					.getDefaultPdfExportClassName2())) {
				logger.error("Did you forget to add a dependency ?");
				throw new ExportException("Unable to export in PDF format");
			}
		}
	}
}