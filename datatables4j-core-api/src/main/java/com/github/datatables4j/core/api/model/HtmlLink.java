package com.github.datatables4j.core.api.model;

public class HtmlLink extends HtmlTag {

	private String id;
	private String href;
	private String label;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public StringBuffer toHtml(){
		StringBuffer html = new StringBuffer();
		html.append("<a");
		
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
		
		if(this.href != null){
			html.append(" href=\"");
			html.append(this.href);
			html.append("\"");
		}
		
		html.append(">");
		html.append(this.label);
		html.append("</a>");
		
		return html;
	}
}
