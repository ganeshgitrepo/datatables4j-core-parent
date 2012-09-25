package org.datatables4j.tag;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.datatables4j.model.HtmlColumn;

/**
 * TODO
 *
 * @author Thibault Duchateau
 */
public abstract class AbstractColumnTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;

	// Tag attributes
	protected String title;
	protected String property;
	protected String cssStyle;
	protected String cssCellStyle;
	protected String cssClass;
	protected String cssCellClass;
	protected Boolean sortable = true;
	
	/**
	 * 
	 * @return
	 */
	protected int processDoStartTag(){
		TableTag parent = (TableTag) getParent();
		if (parent.getLoadingType() == "AJAX") {
			return EVAL_PAGE;
		} 
		else if (parent.getLoadingType() == "DOM") {
			if (getBodyContent() != null) {
				// System.out.println("BODYCONTENT EXISTE");
				return EVAL_BODY_BUFFERED;
			} else {
				// System.out.println("BODYCONTENT VIDE");
				if (property != null) {

					// AbstractTableTag parent = (AbstractTableTag) getParent();

					try {
						this.addColumn(
								false,
								PropertyUtils.getNestedProperty(parent.getCurrentObject(),
										this.property).toString());

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
				}
				return EVAL_PAGE;
			}
		}
		
		// Never reached
		return SKIP_BODY;
	}
	
	/**
	 * TODO
	 * @return
	 */
	protected int processDoEndTag(){
		TableTag parent = (TableTag) getParent();
		if (parent.getLoadingType() == "AJAX") {

			HtmlColumn column = new HtmlColumn(true, this.title);
			column.setProperty(this.property);
			column.setSortable(this.sortable);
			parent.getTable().getLastHeaderRow().addColumn(column);

			System.out.println("************************ FIN AjaxColumnTag doEndTag");
			return EVAL_PAGE;
		} else if (parent.getLoadingType() == "DOM") {
			// System.out.println("parent.isFirstRow() = " +
			// parent.isFirstRow());
			// System.out.println(parent.getRowNumber());
			if (parent.isFirstRow()) {

				this.addColumn(true, this.title);
			}
			return EVAL_PAGE;
		}
		return SKIP_PAGE;
	}
	
	/**
	 * TODO
	 * @param isHeader
	 * @param content
	 */
	protected void addColumn(Boolean isHeader, String content){
		
		HtmlColumn column = new HtmlColumn(isHeader, content);
//		System.out.println("=========== sortable = " + sortable);
		column.setSortable(this.sortable);
		
		AbstractTableTag parent = (AbstractTableTag) getParent();
		
		if(!isHeader){
			if(StringUtils.isNotBlank(this.cssCellClass)){
				column.setCssCellClass(this.cssCellClass);
			}
			if(StringUtils.isNotBlank(this.cssCellStyle)){
				column.setCssCellStyle(this.cssCellStyle);
			}
//			System.out.println("parent = " + parent);
//			System.out.println("parent.getTable() = " + parent.getTable());
			parent.getTable().getLastRow().addColumn(column);			
		}
		else{
			if(StringUtils.isNotBlank(this.cssClass)){
				column.setCssClass(this.cssClass);
			}
			if(StringUtils.isNotBlank(this.cssStyle)){
				column.setCssStyle(this.cssStyle);
			}
			
			parent.getTable().getLastHeaderRow().addColumn(column);
		}
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCssStyle() {
		return cssStyle;
	}

	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public Boolean getSortable() {
		return sortable;
	}

	public void setSortable(Boolean sortable) {
		this.sortable = sortable;
	}

	public String getCssCellStyle() {
		return cssCellStyle;
	}

	public void setCssCellStyle(String cssCellStyle) {
		this.cssCellStyle = cssCellStyle;
	}

	public String getCssCellClass() {
		return cssCellClass;
	}

	public void setCssCellClass(String cssCellClass) {
		this.cssCellClass = cssCellClass;
	}
}
