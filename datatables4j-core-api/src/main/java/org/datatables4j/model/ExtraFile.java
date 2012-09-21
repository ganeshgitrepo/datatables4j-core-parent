package org.datatables4j.model;

public class ExtraFile {

	private String src;
	private String insert;

	public ExtraFile(String src, String insert){
		this.src = src;
		this.insert = insert;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getInsert() {
		return insert;
	}
	public void setInsert(String insert) {
		this.insert = insert;
	}
	
	
}
