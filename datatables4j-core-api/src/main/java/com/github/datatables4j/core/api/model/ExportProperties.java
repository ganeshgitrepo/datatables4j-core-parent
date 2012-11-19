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

/**
 * TODO
 *
 * @author Thibault Duchateau
 */
public class ExportProperties {

	private String fileName = "export";
	private ExportType currentExportType;
	private ExportConf exportConf;

	public ExportType getCurrentExportType() {
		return currentExportType;
	}

	public void setCurrentExportType(ExportType currentExportType) {
		this.currentExportType = currentExportType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public ExportConf getExportConf() {
		return exportConf;
	}

	public void setExportConf(ExportConf exportConf) {
		this.exportConf = exportConf;
	}
}
