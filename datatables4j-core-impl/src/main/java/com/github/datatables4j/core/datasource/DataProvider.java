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
package com.github.datatables4j.core.datasource;


import com.github.datatables4j.core.api.exception.BadConfigurationException;
import com.github.datatables4j.core.api.model.HtmlTable;
import com.github.datatables4j.core.util.ReflectHelper;

/**
 * Class that will instanciate the chosen implementation class for retrieving
 * data using AJAX. The implementation class is referenced in the DataTables4j
 * properties file.
 * 
 * @author Thibault Duchateau
 */
public class DataProvider {
	
	/**
	 * Retrieve the data performing a GET request on the webServiceURL, using
	 * the implementation class stored in HtmlTable.
	 * 
	 * @param table
	 *            The table, containing among others, all properties.
	 * @param webServiceUrl
	 *            The REST WS called to retrieve data.
	 * @return Map<String, Object> the data used to populate the table.
	 * @throws BadConfigurationException
	 *             if the implementation class cannot be found.
	 */
	public Object getData(HtmlTable table, String webServiceUrl) throws BadConfigurationException {
		
		// Get DataProvider class from the properties file
		Class<?> klass = ReflectHelper.getClass(table.getTableProperties().getDatasourceClassName());
		
		// Get new instance of this class
		Object obj = ReflectHelper.getNewInstance(klass);
		
		// Inovke getData method and return result
		return ReflectHelper.invokeMethod(obj, "getData", new Object[]{webServiceUrl});
	}
}