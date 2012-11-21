package com.github.datatables4j.core.api.model;

public class HtmlDiv extends HtmlTag {

	private String id;
	private StringBuffer content = new StringBuffer();
	
	public HtmlDiv(){
		
	}
	
	public HtmlDiv(String id){
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public StringBuffer getContent() {
		return content;
	}
	public void setContent(StringBuffer content) {
		this.content = content;
	}
	public void addContent(StringBuffer contentToAdd){
		this.content.append(contentToAdd); 
	}
	public StringBuffer toHtml(){
		StringBuffer html = new StringBuffer();
		html.append("<div");
		
		if(this.id != null){
			html.append(" id=\"");
			html.append(this.id);
			html.append("\"");
		}
		
		if(this.cssClass != null){
			html.append(" class=\"");
			html.append(this.cssClass);
			html.append("\"");
		}
		
		if(this.cssStyle != null){
			html.append(" style=\"");
			html.append(this.cssStyle);
			html.append("\"");
		}
		
		html.append(">");
		html.append(this.content);
		html.append("</div>");
		return html;
	}
}
