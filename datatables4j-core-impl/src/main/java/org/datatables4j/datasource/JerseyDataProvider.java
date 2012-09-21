package org.datatables4j.datasource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.datatables4j.constants.DTConstants;
import org.datatables4j.exception.DataNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Jersey implementation of DataProvider for AJAX table.
 * 
 * @author Thibault Duchateau
 */
public class JerseyDataProvider implements DataProvider {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(JerseyDataProvider.class);
		
	/**
	 * TODO
	 */
	public Map<String, Object> getData(String wsUrl) throws DataNotFoundException {

		logger.debug("Retrieving data with JerseyDataProvider");
		
		Client client = Client.create();

		WebResource webResource = client.resource(wsUrl);
		
		logger.debug("Web service call ...");
		ClientResponse response = webResource.accept("application/json").get(
				ClientResponse.class);
		if (response.getStatus() != 200) {
			throw new DataNotFoundException("No data found : " + String.valueOf(response.getStatus()));
		}
		
		String output = response.getEntity(String.class);

		// Jackson object mapper
		ObjectMapper mapper = new ObjectMapper();

		Map<String, Object> map = new HashMap<String, Object>();
		JsonNode df;

		try {
			logger.debug("Converting response to JSON ...");
			df = mapper.readValue(output, JsonNode.class);
			
			logger.debug("Convertion OK");
			map.put(DTConstants.DT_DS_DATA, df);
			
		} catch (JsonParseException e) {
			throw new DataNotFoundException(e);
		} catch (JsonMappingException e) {
			throw new DataNotFoundException(e);
		} catch (IOException e) {
			throw new DataNotFoundException(e);
		}

		return map;
	}
}