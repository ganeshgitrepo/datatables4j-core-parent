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

import java.util.HashMap;
import java.util.Map;

/**
 * All the available export types.
 *
 * @author Thibault Duchateau
 */
public enum ExportType {
	
	CSV("csv", "text/csv", 1), 
	HTML("html", "text/html", 2), 
	XML("xml", "text/xml", 3), 
	RTF("rtf", "text/rtf", 4),
	PDF("pdf", "application/pdf", 5), 
	XLS("xls", "application/vnd.ms-exce", 6),
	JSON("json", "", 7); 
	
	private String extension;
	private String mimeType;
	private Integer urlParameter;
	
	private ExportType(String extension, String mimeType, Integer urlParameter){
		this.extension = extension;
		this.mimeType = mimeType;
		this.urlParameter = urlParameter;
	}
	
	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public Integer getUrlParameter() {
		return urlParameter;
	}

	public void setUrlParameter(Integer urlParameter) {
		this.urlParameter = urlParameter;
	}
	
	private static final Map<Integer,ExportType> map;
    static {
        map = new HashMap<Integer,ExportType>();
        for (ExportType v : ExportType.values()) {
            map.put(v.getUrlParameter(), v);
        }
    }
    
    public static ExportType findByUrlParameter(Integer i) {
        return map.get(i);
    }
}