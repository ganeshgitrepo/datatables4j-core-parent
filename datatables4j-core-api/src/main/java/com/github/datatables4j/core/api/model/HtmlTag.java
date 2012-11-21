package com.github.datatables4j.core.api.model;

public abstract class HtmlTag {

	protected StringBuffer cssClass;
	protected StringBuffer cssStyle;
	
	public StringBuffer getCssClass() {
		return cssClass;
	}
	public void setCssClass(StringBuffer cssClass) {
		this.cssClass = cssClass;
	}
	public StringBuffer getCssStyle() {
		return cssStyle;
	}
	public void setCssStyle(StringBuffer cssStyle) {
		this.cssStyle = cssStyle;
	}
	public void addCssClass(String cssClass){
		if(this.cssClass == null){
			this.cssClass = new StringBuffer();
		}
		this.cssClass.append(cssClass);
	}
	public void addCssStyle(String cssStyle){
		if(this.cssStyle == null){
			this.cssStyle = new StringBuffer();
		}
		this.cssStyle.append(cssStyle);
	}
}
