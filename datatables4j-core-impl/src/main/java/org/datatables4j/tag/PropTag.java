package org.datatables4j.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class PropTag extends TagSupport {
	private static final long serialVersionUID = -3453884184847355817L;

	// Tag attributes
	private String name;
	private String value;
	
	public int doStartTag() throws JspException {
		return SKIP_BODY;
	}
	
	public int doEndTag() throws JspException {
		
		AbstractTableTag parent = (AbstractTableTag) getParent();
		
		if(parent.isFirstRow()){
			parent.getTable().getProperties().setProperty(name, value);
		}
		return EVAL_PAGE;
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
}