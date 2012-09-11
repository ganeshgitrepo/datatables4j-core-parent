package org.datatables4j.tag;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyContent;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableTag extends AbstractTableTag {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(AbstractTableTag.class);

	private static final long serialVersionUID = 1L;

	public int doStartTag() {
		return processDoStartTag();
	}

	public int doAfterBody() throws JspTagException {

		if ("DOM".equals(this.loadingType)) {

			BodyContent body = getBodyContent();
			try {
				body.writeOut(getPreviousOut());
			} catch (IOException e) {
				throw new JspTagException("IterationTag: " + e.getMessage());
			}

			// clear up so the next time the body content is empty
			body.clearBody();

			if (iterator.hasNext()) {
				Object object = iterator.next();
				if (this.rowId != null) {
					try {
						this.table.addRow(String.valueOf(PropertyUtils.getNestedProperty(object,
								this.rowId)));
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					this.table.addRow();
				}
				pageContext.setAttribute(row, object);
				this.setCurrentObject(object);
				this.rowNumber++;
				// System.out.println(rowNumber);
				return EVAL_BODY_AGAIN;
			} else {
				return EVAL_PAGE;
			}
		}
		return EVAL_PAGE;
		// return processBodyEvaluation();
	}

	public int doEndTag() throws JspException {
		return processDoEndTag();
	}
	

}