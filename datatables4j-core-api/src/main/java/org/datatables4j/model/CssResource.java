package org.datatables4j.model;

public class CssResource extends DataTablesResource {
	
	private String name;
	private String location;
	private String content;
	private String type;
	
	public CssResource(String name){
		this.name = name;
	}
	
	public CssResource(String type, String name){
		this.type = type;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public void updateContent(String newContent){
		this.content = this.content + newContent;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
