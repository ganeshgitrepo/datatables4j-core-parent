package org.datatables4j.tag;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

/**
 * TODO
 *
 * @author Thibault Duchateau
 */
public class IterationTEI extends TagExtraInfo {

	public VariableInfo[] getVariableInfo(TagData data) {
		System.out.println("getId =" + data.getId());
		
		List<VariableInfo> variables = new ArrayList<VariableInfo>();
		
		if(data.getAttributeString("row") != null){
			variables.add(new VariableInfo(data.getAttributeString("row"), "java.lang.Object",
					true, VariableInfo.NESTED));		
		}

		return (VariableInfo[]) variables.toArray(new VariableInfo[]{});
	}
}
