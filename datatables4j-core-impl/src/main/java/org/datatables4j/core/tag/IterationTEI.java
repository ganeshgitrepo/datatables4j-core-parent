/*
 * DataTables4j, a JSP taglib to display table with jQuery and DataTables
 * Copyright (c) 2012, DataTables4j <datatables4j@gmail.com>
 *
 * This Program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License, version 2, as
 * published by the Free Software Foundation.
 * 
 * The Program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.datatables4j.core.tag;

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
