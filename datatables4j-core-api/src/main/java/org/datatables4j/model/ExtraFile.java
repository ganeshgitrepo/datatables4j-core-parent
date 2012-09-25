package org.datatables4j.model;

import org.datatables4j.constants.InsertMode;

/**
 * TODO
 *
 * @author Thibault Duchateau
 */
public class ExtraFile {

	private String src;
	private InsertMode insert;

	public ExtraFile(String src, InsertMode insert){
		this.src = src;
		this.insert = insert;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public InsertMode getInsert() {
		return insert;
	}
	public void setInsert(InsertMode insert) {
		this.insert = insert;
	}
}
