/*
 * [The "BSD licence"]
 * Copyright (c) 2012 DataTables4j
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 3. Neither the name of DataTables4j nor the names of its contributors 
 * may be used to endorse or promote products derived from this software 
 * without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.github.datatables4j.core.base.ajax;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.github.datatables4j.core.api.constants.DTConstants;
import com.github.datatables4j.core.base.ajax.ColumnDef.SortDirection;

/**
 * <p>
 * POJO that wraps all the parameters sent by Datatables to the server when
 * server-side processing is enabled. This bean can then be used to build SQL
 * queries.
 * 
 * @author Thibault Duchateau
 * @since 0.8.2
 */
public final class DatatablesCriterias {

	private final String search;
	private final Integer displayStart;
	private final Integer displaySize;
	private final List<ColumnDef> columnDefs;
	private final Integer internalCounter;

	public DatatablesCriterias(String search, Integer displayStart, Integer displaySize,
			List<ColumnDef> columnDefs, Integer internalCounter) {
		this.search = search;
		this.displayStart = displayStart;
		this.displaySize = displaySize;
		this.columnDefs = columnDefs;
		this.internalCounter = internalCounter;
	}

	public Integer getDisplayStart() {
		return displayStart;
	}

	public Integer getDisplaySize() {
		return displaySize;
	}

	public String getSearch() {
		return search;
	}

	public Integer getInternalCounter() {
		return internalCounter;
	}

	public List<ColumnDef> getColumnDefs() {
		return columnDefs;
	}

	/**
	 * @return true if a column is filterable, false otherwise.
	 */
	public Boolean hasOneFilterableColumn() {
		for (ColumnDef columnDef : this.columnDefs) {
			if (columnDef.isFilterable()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return true if a column is being sorted, false otherwise.
	 */
	public Boolean hasOneSortedColumn() {
		for (ColumnDef columnDef : this.columnDefs) {
			if (columnDef.isSorted()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Map the request parameters into a bean to ease the build of SQL queries.
	 * 
	 * @param request
	 *            The request sent by Datatables containing all the parameters.
	 * @return a DatatablesCriterias bean.
	 */
	public static DatatablesCriterias getFromRequest(HttpServletRequest request) {

		if (request != null) {

			String sSearch = request.getParameter(DTConstants.DT_S_SEARCH);
			String sEcho = request.getParameter(DTConstants.DT_S_ECHO);
			String sDisplayStart = request.getParameter(DTConstants.DT_I_DISPLAY_START);
			String sDisplayLength = request.getParameter(DTConstants.DT_I_DISPLAY_LENGTH);

			Integer iEcho = Integer.parseInt(sEcho);
			Integer iDisplayStart = Integer.parseInt(sDisplayStart);
			Integer iDisplayLength = Integer.parseInt(sDisplayLength);
			Integer colNumber = Integer.parseInt(request.getParameter(DTConstants.DT_I_COLUMNS));

			List<ColumnDef> columnDefs = new ArrayList<ColumnDef>();

			for (int i = 0; i < colNumber; i++) {

				ColumnDef columnDef = new ColumnDef();

				columnDef.setName(request.getParameter(DTConstants.DT_M_DATA_PROP + i));
				columnDef.setFilterable(Boolean.parseBoolean(request
						.getParameter(DTConstants.DT_B_SEARCHABLE + i)));
				columnDef.setSortable(Boolean.parseBoolean(request
						.getParameter(DTConstants.DT_B_SORTABLE + i)));

				String sortingCol = request.getParameter(DTConstants.DT_I_SORT_COL + i);
				if (sortingCol != null) {
					columnDef.setSorted(true);
				}

				String sortingColDirection = request.getParameter(DTConstants.DT_S_SORT_DIR + i);
				if (sortingColDirection != null) {
					columnDef.setSortDirection(SortDirection.valueOf(sortingColDirection
							.toUpperCase()));
				}

				columnDefs.add(columnDef);
			}

			return new DatatablesCriterias(sSearch, iDisplayStart, iDisplayLength, columnDefs,
					iEcho);
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		return "DatatablesCriterias [search=" + search + ", displayStart=" + displayStart
				+ ", displaySize=" + displaySize + ", internalCounter=" + internalCounter
				+ ", columnDefs=" + columnDefs + "]";
	}
}