package com.github.datatables4j.spring3.ajax;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import com.github.datatables4j.core.base.ajax.DatatablesCriterias;

/**
 * TODO essayer de mettre @Component et virer la conf XML
 *
 * @author Thibault Duchateau
 */
public class DatatablesCriteriasResolver implements WebArgumentResolver {

	public Object resolveArgument(MethodParameter methodParam, NativeWebRequest nativeWebRequest) throws Exception {
		DatatablesParams tableParamAnnotation = methodParam.getParameterAnnotation(DatatablesParams.class);

		if (tableParamAnnotation != null) {
			HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();
			return DatatablesCriterias.getFromRequest(request);
		}

		return WebArgumentResolver.UNRESOLVED;
	}
}