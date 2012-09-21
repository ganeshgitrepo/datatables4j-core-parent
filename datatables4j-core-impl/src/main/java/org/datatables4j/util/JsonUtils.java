package org.datatables4j.util;

import java.io.IOException;
import java.util.Iterator;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

public class JsonUtils {

	private static ObjectMapper mapper;
	
	/**
	 * TODO
	 */
	static{
		mapper = new ObjectMapper();
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
	}
	
	/**
	 * TODO
	 * @param mainNode
	 * @param updateNode
	 * @return
	 */
	public static JsonNode merge(JsonNode mainNode, JsonNode updateNode) {

	    Iterator<String> fieldNames = updateNode.getFieldNames();
	    while (fieldNames.hasNext()) {

	        String fieldName = fieldNames.next();
	        JsonNode jsonNode = mainNode.get(fieldName);
	        // if field doesn't exist or is an embedded object
	        if (jsonNode != null && jsonNode.isObject()) {
	            merge(jsonNode, updateNode.get(fieldName));
	        }
	        else {
	            if (mainNode instanceof ObjectNode) {
	                // Overwrite field
	                JsonNode value = updateNode.get(fieldName);
	                ((ObjectNode) mainNode).put(fieldName, value);
	            }
	        }

	    }

	    return mainNode;
	}
	
	/**
	 * TODO
	 * @param jsonString
	 * @return
	 */
	public static JsonNode convertStringToJsonNode(String jsonString){
	
//		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode jsonNode = null;
		try {
			jsonNode = mapper.readTree(jsonString);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonNode;
	}
	
	public static JsonNode convertObjectToJsonNode(Object object){
		return mapper.valueToTree(object);
	}
	
	public static Object convertJsonNodeToObject(JsonNode nodeToConvert, Class<?> objectClass){
	
		Object retval = null;

		 try {
			 retval = mapper.readValue(nodeToConvert, objectClass);
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
		return retval;
	}
	
	public static String convertObjectToJsonString(Object config){
				
		StringBuffer tmpRetval = new StringBuffer();
		// write JSON to a file
		try {
			tmpRetval.append(mapper.writeValueAsString(config));
			// System.out.println(mapper.writeValueAsString(aoColumns));
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// return tmpRetval.toString();
		return tmpRetval.toString();
	}
}
