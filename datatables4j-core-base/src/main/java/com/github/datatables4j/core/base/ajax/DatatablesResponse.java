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

import java.util.List;

/**
 * <p>
 * Bean that wraps a response that must be sent back to Datatables to update the
 * table when server-side processing is enabled.
 * <p>
 * Since Datatables only support JSON at the moment, this bean must be converted
 * to JSON by the server.
 * 
 * @author Thibault Duchateau
 * @since 0.8.2
 */
public class DatatablesResponse<T> {

	private final List<T> aaData;
	private final Long iTotalRecords;
	private final Long iTotalDisplayRecords;
	private final Integer sEcho;

	private DatatablesResponse(DataSet<T> dataSet, DatatablesCriterias criterias) {
		this.aaData = dataSet.getRows();
		this.iTotalRecords = dataSet.getTotalRecords();
		this.iTotalDisplayRecords = dataSet.getTotalDisplayRecords();
		this.sEcho = criterias.getInternalCounter();
	}

	public List<T> getAaData() {
		return aaData;
	}

	public Long getiTotalRecords() {
		return iTotalRecords;
	}

	public Long getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public Integer getsEcho() {
		return sEcho;
	}

	public static <T> DatatablesResponse<T> build(DataSet<T> dataSet, DatatablesCriterias criterias) {
		return new DatatablesResponse<T>(dataSet, criterias);
	}
}
