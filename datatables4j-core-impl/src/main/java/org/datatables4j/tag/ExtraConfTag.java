/**
 * 
 */
package org.datatables4j.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.datatables4j.model.ExtraConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO
 *
 * @author Thibault Duchateau
 */
public class ExtraConfTag extends TagSupport {

	private static final long serialVersionUID = 7656056513635184779L;

	// Logger
	private static Logger logger = LoggerFactory.getLogger(AbstractTableTag.class);

	// Tag attributes
	private String src;

	public int doStartTag() throws JspException {
		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {

		AbstractTableTag parent = (AbstractTableTag) getParent();

		if (parent.isFirstRow()) {
			parent.getTable().getExtraConfs().add(new ExtraConf(getRealSource(this.src)));
		}
		return EVAL_PAGE;
	}

	private String getRealSource(String tmpSource) {
		logger.debug("getRealSource = {}", pageContext.getServletContext().getRealPath(tmpSource));
		return pageContext.getServletContext().getRealPath(tmpSource);
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}
}