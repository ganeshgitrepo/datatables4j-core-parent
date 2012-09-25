package org.datatables4j.tag;

import javax.servlet.jsp.JspException;

/**
 * TODO
 * 
 * @author Thibault Duchateau
 */
public class TableTag extends AbstractTableTag {
	private static final long serialVersionUID = 4528524566511084446L;

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