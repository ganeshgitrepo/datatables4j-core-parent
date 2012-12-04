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
package com.github.datatables4j.core.datasource;


import com.github.datatables4j.core.api.exception.BadConfigurationException;
import com.github.datatables4j.core.api.exception.DataNotFoundException;
import com.github.datatables4j.core.api.model.HtmlTable;
import com.github.datatables4j.core.util.ReflectHelper;

import java.lang.reflect.InvocationTargetException;

/**
 * Class that will instanciate the chosen implementation class for retrieving
 * data using AJAX. The implementation class is referenced in the DataTables4j
 * properties file.
 *
 * @author Thibault Duchateau
 */
public class DataProvider {

    /**
     * Retrieve the data performing a GET request on the webServiceURL, using
     * the implementation class stored in HtmlTable.
     *
     * @param table         The table, containing among others, all properties.
     * @param webServiceUrl The REST WS called to retrieve data.
     * @return Map<String, Object> the data used to populate the table.
     * @throws BadConfigurationException if the implementation class cannot be found.
     * @throws DataNotFoundException     no data was found
     */
    public Object getData(HtmlTable table, String webServiceUrl) throws BadConfigurationException, DataNotFoundException {

        // Get DataProvider class from the properties file
        Class<?> klass = ReflectHelper.getClass(table.getTableProperties().getDatasourceClassName());

        // Get new instance of this class
        Object obj = ReflectHelper.getNewInstance(klass);

        // Invoke getData method and return result
        try {
            return ReflectHelper.invokeMethod(obj, "getData", new Object[]{webServiceUrl});
        } catch (BadConfigurationException e) {
            if (e.getCause() instanceof InvocationTargetException) {
                throw (DataNotFoundException) ((InvocationTargetException) e.getCause()).getTargetException();
            } else {
                throw e;
            }
        }
    }
}