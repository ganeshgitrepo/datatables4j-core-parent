package org.datatables4j.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.datatables4j.model.ExtraFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtraFileTag extends TagSupport {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(AbstractTableTag.class);
		
	private static final long serialVersionUID = -287813095911386884L;

	// Tag attribute
	private String src;
	private String include;
	
	// Internal attribute
	private Integer counter;
	
	@Override
	public int doStartTag() throws JspException {
		counter = 0;
		logger.debug("ExtraFileTag DEBUT doStartTag");
		logger.debug("********** counter = {}", counter);
		if(counter == 1){
			counter++;
			logger.debug("FIN doStartTag");
			return SKIP_BODY;
		}
		else{
			logger.debug("ExtraFileTag FIN doStartTag");
			return EVAL_PAGE;
		}
	}
	
	public int doEndTag() throws JspException {
		
		logger.debug("ExtraFileTag DEBUT doEndTag");
		logger.debug("********** counter = {}", counter);
		AbstractTableTag parent = (AbstractTableTag) getParent();
		parent.getTable().getExtraFiles().add(new ExtraFile(this.src, this.include));
		
		logger.debug("src = {}", src);
		
		
		logger.debug("ExtraFileTag FIN doEndTag");
		return EVAL_PAGE;
	}
	
	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getInclude() {
		return include;
	}

	public void setInclude(String include) {
		this.include = include;
	}
}
