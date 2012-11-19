package com.github.datatables4j.core.module.export;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.lang.StringUtils;

import com.github.datatables4j.core.api.exception.ExportException;
import com.github.datatables4j.core.api.model.HtmlColumn;
import com.github.datatables4j.core.api.model.HtmlRow;
import com.github.datatables4j.core.api.model.HtmlTable;
import com.github.datatables4j.core.api.module.export.AbstractExport;

public class XmlExport extends AbstractExport {

	private HtmlTable table;

	@Override
	public void initExport(HtmlTable table) {
		this.table = table;
	}

	@Override
	public Object processExport() throws ExportException {

		// Build headers list for attributes name
		List<String> headers = new ArrayList<String>();
		
		for(HtmlRow row : table.getHeadRows()){
			for(HtmlColumn column : row.getColumns()){
				headers.add(StringUtils.uncapitalize(column.getContent()));
			}
		}

		XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
		XMLStreamWriter writer = null;
		StringWriter sw = new StringWriter();
		
		try {
			writer = outputFactory.createXMLStreamWriter(sw);
			writer.writeStartDocument("1.0");
			writer.writeStartElement(table.getObjectType().toLowerCase() + "s");

			for (HtmlRow row : table.getBodyRows()) {
				writer.writeStartElement(table.getObjectType().toLowerCase());

				int i = 0;
				for (HtmlColumn column : row.getColumns()) {
					writer.writeAttribute(headers.get(i), column.getContent());
					i++;
				}

				writer.writeEndElement();
			}

			writer.writeEndElement();
			writer.writeEndDocument();
			writer.flush();

		} catch (XMLStreamException e) {
			throw new ExportException(e);
		} finally {
			try {
				writer.close();
			} catch (XMLStreamException e) {
				throw new ExportException(e);
			}
		}

		return sw.toString();
	}
}