package org.datatables4j.datasource;

import java.util.Map;

import org.datatables4j.exception.BadConfigurationException;
import org.datatables4j.model.HtmlTable;
import org.datatables4j.util.ReflectUtils;

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
	@SuppressWarnings("unchecked")
	public Map<String, Object> getData(HtmlTable table, String webServiceUrl) throws BadConfigurationException {

		// Get DataProvider class from the properties file
		Class<?> klass = ReflectUtils.getClass(table.getProperties().getDatasourceClassName());
		
		// Get new instance of this class
		Object obj = ReflectUtils.getNewInstance(klass);
		
		// Inovke getData method and return result
		return (Map<String, Object>) ReflectUtils.invokeMethod(obj, "getData", new Object[]{webServiceUrl});
	}
}