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
package com.github.datatables4j.core.compressor;

import com.github.datatables4j.core.api.exception.BadConfigurationException;
import com.github.datatables4j.core.api.exception.CompressionException;
import com.github.datatables4j.core.api.model.HtmlTable;
import com.github.datatables4j.core.util.ReflectHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper class in charge of the instanciation of the compressor implementation
 * defined in the datatables4j configuration file.
 *
 * @author Thibault Duchateau
 */
public class ResourceCompressorDelegate {

    // Logger
    private static Logger logger = LoggerFactory.getLogger(ResourceCompressorDelegate.class);

    private String compressorClassName;

    /**
     * Private constructor which retrieve the compressor class from properties.
     */
    public ResourceCompressorDelegate(HtmlTable table) {

        this.compressorClassName = table.getTableProperties().getCompressorClassName();

        logger.debug("ResourceCompressor loaded. About to use {} implementation", this.compressorClassName);
    }


    /**
     * Compress the javascript input using the compressorClassName and return
     * it.
     *
     * @param input The stringified javascript to compress.
     * @return The compressed stringified javascript.
     * @throws BadConfigurationException if the compressorClassName is not present in the classPath.
     * @throws CompressionException      if a error append during the compression.
     */
    public String getCompressedJavascript(String input) throws BadConfigurationException, CompressionException {

        String compressedContent = null;

        Class<?> compressorClass = ReflectHelper.getClass(this.compressorClassName);

        logger.debug("Instancing the compressor class {}", compressorClass);
        Object obj = ReflectHelper.getNewInstance(compressorClass);

        logger.debug("Invoking method getCompressedJavascript");
        try {
            return (String) ReflectHelper.invokeMethod(obj, "getCompressedJavascript", new Object[]{input});
        } catch (BadConfigurationException e) {
            if (e.getCause() instanceof CompressionException) {
                throw (CompressionException) e.getCause();
            } else {
                throw e;
            }
        }
    }


    /**
     * Compress the CSS input using the compressorClassName and return
     * it.
     *
     * @param input The stringified CSS to compress.
     * @return The compressed stringified CSS.
     * @throws BadConfigurationException if the compressorClassName is not present in the classPath.
     * @throws CompressionException      if a error append during the compression.
     */
    public String getCompressedCss(String input) throws BadConfigurationException, CompressionException {

        Class<?> compressorClass = ReflectHelper.getClass(this.compressorClassName);

        Object obj = ReflectHelper.getNewInstance(compressorClass);

        try {
            return (String) ReflectHelper.invokeMethod(obj, "getCompressedCss", new Object[]{input});
        } catch (BadConfigurationException e) {
            if (e.getCause() instanceof CompressionException) {
                throw (CompressionException) e.getCause();
            } else {
                throw e;
            }
        }
    }
}