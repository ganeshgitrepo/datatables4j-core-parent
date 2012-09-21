package org.datatables4j.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.datatables4j.model.ExtraFile;
import org.datatables4j.util.JsConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtraFileTag extends TagSupport {
	private static final long serialVersionUID = -287813095911386884L;

	// Logger
	private static Logger logger = LoggerFactory.getLogger(AbstractTableTag.class);

	// Tag attributes
	private String src;
	private String insert = JsConstants.BEFOREALL;
	
	public int doStartTag() throws JspException {
		return SKIP_BODY;
	}
	
	public int doEndTag() throws JspException {
		
		AbstractTableTag parent = (AbstractTableTag) getParent();
		
		if(parent.isFirstRow()){
			parent.getTable().getExtraFiles().add(new ExtraFile(getRealSource(this.src), this.insert));
		}
		return EVAL_PAGE;
	}
	
	private String getRealSource(String tmpSource){
		logger.debug("getRealSource = {}", pageContext.getServletContext().getRealPath(tmpSource));
		return pageContext.getServletContext().getRealPath(tmpSource);
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
