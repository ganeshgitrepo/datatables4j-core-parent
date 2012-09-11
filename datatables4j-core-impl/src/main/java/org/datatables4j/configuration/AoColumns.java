package org.datatables4j.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AoColumns extends HashMap<String, List<Map<String, Object>>> {
	
	private static final long serialVersionUID = 1L;

	private List<Map<String, Object>> content;

	public AoColumns() {
		this.content = new ArrayList<Map<String, Object>>();
	}

	public void addValue(String name, Object value) {
		Map<String, Object> tmp = new HashMap<String, Object>();
		tmp.put(name, value);
		tmp.put("mData", "address.street1");
		this.content.add(tmp);
	}
	
	public String getName(){
		return "aoColumns";
	}
	
	public List<Map<String, Object>> getContent(){
		return this.content;
	}
}