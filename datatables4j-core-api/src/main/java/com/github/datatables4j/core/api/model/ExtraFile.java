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
package com.github.datatables4j.core.api.model;

import com.github.datatables4j.core.api.constants.InsertMode;

/**
 * TODO
 *
 * @author Thibault Duchateau
 */
public class ExtraFile {

	private String src;
	private InsertMode insert;

	public ExtraFile(String src, InsertMode insert){
		this.src = src;
		this.insert = insert;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public InsertMode getInsert() {
		return insert;
	}
	public void setInsert(InsertMode insert) {
		this.insert = insert;
	}
}
