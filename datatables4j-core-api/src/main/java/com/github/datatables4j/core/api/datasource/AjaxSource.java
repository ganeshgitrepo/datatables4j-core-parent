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
package com.github.datatables4j.core.api.datasource;

import com.github.datatables4j.core.api.exception.DataNotFoundException;

/**
 * <p>
 * Interface for the data provider.
 * </p>
 * <p>
 * By default, DataTables4j embed a JerseyDataProvider for retrieving data.
 * </p>
 * 
 * @author Thibault Duchateau
 */
public interface AjaxSource {

	/**
	 * <p>
	 * Return the data from db in order to populate the DataTables.
	 * </p>
	 * TODO
	 * 
	 * @param wsUrl
	 * @return Map<String, Object> data
	 * @throws DataNotFoundException
	 */
	public Object getData(String wsUrl) throws DataNotFoundException;
}