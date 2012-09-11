package org.datatables4j.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

public class RequestHelper {

	public static String getBaseUrl(PageContext pageContext){
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		return request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath());
	}
}
