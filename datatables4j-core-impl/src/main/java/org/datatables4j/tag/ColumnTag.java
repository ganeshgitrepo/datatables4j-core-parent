package org.datatables4j.tag;

import javax.servlet.jsp.JspException;

/**
 * TODO
 *
 * @author Thibault Duchateau
 */
public class ColumnTag extends AbstractColumnTag {

	private static final long serialVersionUID = -8928415196287387948L;

	/**
	 * TODO
	 */
	public int doStartTag() throws JspException {
		return processDoStartTag();
	}

	/**
	 * TODO
	 */
	public int doEndTag() throws JspException {
		return processDoEndTag();
	}

	/**
	 * TODO
	 */
	public int doAfterBody() throws JspException {

		TableTag parent = (TableTag) getParent();
		if (parent.getLoadingType() == "AJAX") {
			
		}
		else{
			if (getBodyContent() != null) {
				String bodyString = getBodyContent().getString();
				this.addColumn(false, bodyString);
			} else {
				// System.out.println("BODY VIDE !");
			}
		}

		return EVAL_PAGE;
	}
}
