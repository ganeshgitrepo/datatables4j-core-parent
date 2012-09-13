package org.datatables4j.model;

public class ModuleConf {

	private String name;
	private String value;	
	private Mode mode;
	public enum Mode {
		OVERRIDE, APPEND, PREPEND
	}

	public ModuleConf(String name, String value){
		this.name = name;
		this.value = value;
		this.mode = Mode.OVERRIDE;
	}
	public ModuleConf(String name, String value, ModuleConf.Mode mode){
		this.name = name;
		this.value = value;
		this.setMode(mode);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Mode getMode() {
		return mode;
	}
	public void setMode(Mode mode) {
		this.mode = mode;
	}
}