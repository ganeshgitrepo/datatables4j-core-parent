package org.datatables4j.model;

public class ExtraConf {

	private String src;
	private String include;
	
	public ExtraConf(String src, String include){
		this.src = src;
		this.include = include;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getInclude() {
		return include;
	}
	public void setInclude(String include) {
		this.include = include;
	}
}
