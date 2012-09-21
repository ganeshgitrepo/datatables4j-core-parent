package org.datatables4j.datasource;

import java.util.Map;

import org.datatables4j.exception.BadConfigurationException;
import org.datatables4j.model.HtmlTable;
import org.datatables4j.util.ReflecUtils;

/**
 * TODO
 * @author Thibault Duchateau
 */
public class DataProviderHelper {

	/**
	 * Main application DataTables4j context.
	 */
//	private DataTablesContext context = DataTablesContext.getInstance();
	
	
	/**
	 * TODO
	 * @param webServiceUrl
	 * @return
	 * @throws BadConfigurationException
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getData(HtmlTable table, String webServiceUrl) throws BadConfigurationException {

		// Get DataProvider class from the properties file
		Class<?> klass = ReflecUtils.getClass(table.getProperties().getDatasourceClassName());
		
		// Get new instance of this class
		Object obj = ReflecUtils.getNewInstance(klass);
		
		// Inovke getData method and return result
		return (Map<String, Object>) ReflecUtils.invokeMethod(obj, "getData", new Object[]{webServiceUrl});
	}
}
