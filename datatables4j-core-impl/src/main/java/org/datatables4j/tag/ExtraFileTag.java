package org.datatables4j.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.datatables4j.constants.InsertMode;
import org.datatables4j.model.ExtraFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO
 *
 * @author Thibault Duchateau
 */
public class ExtraFileTag extends TagSupport {
	private static final long serialVersionUID = -287813095911386884L;

	// Logger
	private static Logger logger = LoggerFactory.getLogger(AbstractTableTag.class);

	// Tag attributes
	private String src;
	private InsertMode insert = InsertMode.BEFOREALL;
	
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

	public InsertMode getInsert() {
		return insert;
	}

	public void setInsert(InsertMode insert) {
		this.insert = insert;
	}
}
