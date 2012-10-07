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
package org.datatables4j.core.util;

import java.io.IOException;
import java.util.Iterator;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

/**
 * TODO
 *
 * @author Thibault Duchateau
 */
public class JsonUtils {

	private static ObjectMapper mapper;
	
	/**
	 * TODO
	 */
	static{
		mapper = new ObjectMapper();
//		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, false);
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, false);
		mapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, true);
		mapper.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, false);
//		mapper.configure(SerializationConfig.Feature.QUOTE_FIELD_NAMES, true);
		
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
			tmpRetval.append(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(config));
			System.out.println("tmpRetval= " + tmpRetval);
			System.out.println("tmpRetval = " + tmpRetval.toString());
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
