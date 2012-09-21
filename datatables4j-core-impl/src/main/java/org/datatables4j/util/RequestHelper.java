package org.datatables4j.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * TODO
 * @author tduchate
 *
 */
public class RequestHelper {

	/**
	 * TODO
	 * @param pageContext
	 * @return
	 */
	public static String getBaseUrl(PageContext pageContext){
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		return request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath());
	}
}
