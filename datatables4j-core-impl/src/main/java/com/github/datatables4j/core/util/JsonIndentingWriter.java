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
package com.github.datatables4j.core.util;

import java.io.StringWriter;

/**
 * Custom writer that pretty prints a JSON object as a String.
 * 
 * @author Elad Tabak
 * @since 28-Nov-2011
 * @version 0.1
 */
public class JsonIndentingWriter extends StringWriter {

	private int indent = 0;

	@Override
	public void write(int c) {
		if (((char) c) == '[' || ((char) c) == '{') {
			super.write(c);
			super.write('\n');
			indent++;
			writeIndentation();
		} else if (((char) c) == ',') {
			super.write(c);
			super.write('\n');
			writeIndentation();
		} else if (((char) c) == ']' || ((char) c) == '}') {
			super.write('\n');
			indent--;
			writeIndentation();
			super.write(c);
		} else {
			super.write(c);
		}

	}

	private void writeIndentation() {
		for (int i = 0; i < indent; i++) {
			super.write("   ");
		}
	}
}
