package org.datatables4j.datasource;

import java.util.Map;

import org.datatables4j.exception.DataNotFoundException;

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
public interface DataProvider {

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
	public Map<String, Object> getData(String wsUrl) throws DataNotFoundException;
}