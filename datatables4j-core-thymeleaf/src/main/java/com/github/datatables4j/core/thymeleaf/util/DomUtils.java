package com.github.datatables4j.core.thymeleaf.util;

import javax.servlet.http.HttpServletRequest;

import org.thymeleaf.dom.Document;
import org.thymeleaf.dom.Element;

public class DomUtils {


	public static void addScriptTag(Element element, HttpServletRequest request, String jsResourceName,
			Document document) {
		Element script = new Element("script");
		script.setAttribute("src", jsResourceName);
		System.out
				.println("=====> parent = " + ((Element) element.getParent()).getNormalizedName());
		element.getParent().insertAfter(element, script);
	}

	public static void addLinkTag(Element element, HttpServletRequest request, String cssResourceName,
			Document document) {
		Element link = new Element("link");
		link.setAttribute("href", cssResourceName);
		link.setAttribute("rel", "stylesheet");
		System.out.println(element.getNormalizedName());
		System.out.println(element.getChildren().size());
		// System.out.println(Utils.getParent(element).getNormalizedName());
		// System.out.println(element.getDocumentName());
		System.out.println(document.getChildren());
		element.getParent().insertBefore(element, link);
	}

}
