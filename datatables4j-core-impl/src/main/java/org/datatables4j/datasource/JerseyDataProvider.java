package org.datatables4j.datasource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.datatables4j.exception.DataNotFoundException;
import org.datatables4j.util.DTConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


public class JerseyDataProvider {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(JerseyDataProvider.class);
		
	public Map<String, Object> getData(String wsUrl) throws DataNotFoundException {

		logger.debug("Retrieving data with JerseyDataProvider");
		
		Client client = Client.create();

		// TODO ENLEVER URL EN DUR
		WebResource webResource = client.resource(wsUrl);
		
		logger.debug("Web service call ...");
		ClientResponse response = webResource.accept("application/json").get(
				ClientResponse.class);
		if (response.getStatus() != 200) {
			throw new DataNotFoundException("No data found : " + String.valueOf(response.getStatus()));
		}
		
		String output = response.getEntity(String.class);

		ObjectMapper mapper = new ObjectMapper();

		Map<String, Object> map = new HashMap<String, Object>();
		JsonNode df;

		try {
			logger.debug("Converting response to JSON ...");
			df = mapper.readValue(output, JsonNode.class);
			
			logger.debug("Convertion OK");
			map.put(DTConstants.DT_DS_DATA, df);
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return map;
	}
}