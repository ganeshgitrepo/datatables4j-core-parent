package com.github.datatables4j.core.thymeleaf.util;

import javax.servlet.http.HttpServletRequest;

import org.thymeleaf.dom.Element;

public class Utils {

	/**
	 * <p>
	 * Return the base URL (context path included).
	 * 
	 * <p>
	 * Example : with an URL like http://domain.com:port/context/anything, this
	 * function returns http://domain.com:port/context.
	 * 
	 * @param pageContext
	 *            Context of the current JSP.
	 * @return the base URL of the current JSP.
	 */
	public static String getBaseUrl(HttpServletRequest request) {
		return request.getRequestURL().toString()
				.replace(request.getRequestURI(), request.getContextPath());
	}
	
//	protected void addColumn(Boolean isHeader, String content) {
//
//		// Init the column
//		HtmlColumn column = new HtmlColumn(isHeader, content);
//
//		// Common configuration (between header and non-header columns)
//		column.setSortable(this.sortable);
//
//		AbstractTableTag parent = (AbstractTableTag) getParent();
//
//		// Enabled display types
//		List<DisplayType> enabledDisplayTypes = new ArrayList<DisplayType>();
//		if (StringUtils.isNotBlank(this.display)) {
//			String[] displayTypes = this.display.trim().toUpperCase().split(",");
//
//			for (String displayType : displayTypes) {
//				try {
//					enabledDisplayTypes.add(DisplayType.valueOf(displayType));
//				} catch (IllegalArgumentException e) {
//					logger.error("{} is not a valid value among {}. Please choose a valid one.",
//							displayType, DisplayType.values());
//					throw new JspException(e);
//				}
//			}
//		} else {
//			// All display types are added
//			for (DisplayType type : DisplayType.values()) {
//				enabledDisplayTypes.add(type);
//			}
//		}
//		column.setEnabledDisplayTypes(enabledDisplayTypes);
//
//		// Non-header columns
//		if (!isHeader) {
//			if (StringUtils.isNotBlank(this.cssCellClass)) {
//				column.setCssCellClass(this.cssCellClass);
//			}
//			if (StringUtils.isNotBlank(this.cssCellStyle)) {
//				column.setCssCellStyle(this.cssCellStyle);
//			}
//
//			parent.getTable().getLastRow().addColumn(column);
//		}
//		// Header columns
//		else {
//			if (StringUtils.isNotBlank(this.cssClass)) {
//				column.setCssClass(new StringBuffer(this.cssClass));
//			}
//			if (StringUtils.isNotBlank(this.cssStyle)) {
//				column.setCssStyle(new StringBuffer(this.cssStyle));
//			}
//
//			column.setSortDirection(this.sortDirection);
//			column.setSortInit(this.sortInit);
//			column.setFilterable(this.filterable);
//
//			if (StringUtils.isNotBlank(this.filterType)) {
//
//				FilterType filterType = null;
//				try {
//					filterType = FilterType.valueOf(this.filterType);
//				} catch (IllegalArgumentException e) {
//					logger.error("{} is not a valid value among {}. Please choose a valid one.",
//							filterType, FilterType.values());
//					throw new JspException(e);
//				}
//				column.setFilterType(filterType);
//			} else {
//				column.setFilterType(FilterType.INPUT);
//			}
//
//			column.setFilterCssClass(this.filterCssClass);
//			column.setFilterPlaceholder(this.filterPlaceholder);
//
//			parent.getTable().getLastHeaderRow().addColumn(column);
//		}
//	}
	
	public static Element getParentAsElement(Element element){
		return (Element) element.getParent();
	}
	
	public static Element getGrandParentAsElement(Element element){
		return (Element) element.getParent().getParent();
	}
	
	public static Element getParent(Element element){
		if(element.hasParent()) {
			return (Element) getParent(element);
		}
		else{
			return element;
		}
	}
}
