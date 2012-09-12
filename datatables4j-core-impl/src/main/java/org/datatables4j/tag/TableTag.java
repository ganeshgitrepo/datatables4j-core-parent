package org.datatables4j.tag;

import javax.servlet.jsp.JspException;

public class TableTag extends AbstractTableTag {

	private static final long serialVersionUID = 1L;

	public int doStartTag() throws JspException {
		return processDoStartTag();
	}

	public int doAfterBody() throws JspException {
		return processDoAfterBody();
	}

	public int doEndTag() throws JspException {
		return processDoEndTag();
	}
}