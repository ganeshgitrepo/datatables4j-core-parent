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
package com.github.datatables4j.core.util;

import com.github.datatables4j.core.api.exception.BadConfigurationException;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * Helper class used for all reflection stuff.
 *
 * @author Thibault Duchateau
 */
public class ReflectHelper {

    // Logger
    private static Logger logger = LoggerFactory.getLogger(ReflectHelper.class);

    /**
     * Get a Java class from its name.
     *
     * @param className The class name.
     * @return The corresponding class.
     * @throws BadConfigurationException if the class doesn't exist.
     */
    public static Class<?> getClass(String className) throws BadConfigurationException {
        Class<?> klass = null;

        try {
            klass = ClassUtils.getClass(className);
        } catch (ClassNotFoundException e) {
            logger.error("Unable to get class {}", className);
            throw new BadConfigurationException(e);
        }

        return klass;
    }

    /**
     * Instanciate a class.
     *
     * @param klass The class to instanciate.
     * @return a new instance of the given class.
     * @throws BadConfigurationException if the class is not instanciable.
     */
    public static Object getNewInstance(Class<?> klass) throws BadConfigurationException {
        Object retval = null;
        try {
            retval = klass.newInstance();
        } catch (InstantiationException e) {
            logger.error("Unable to get instance of {}", klass);
            throw new BadConfigurationException(e);
        } catch (IllegalAccessException e) {
            logger.error("Unable to get instance of {}", klass);
            throw new BadConfigurationException(e);
        }

        return retval;
    }

    /**
     * Invoke a method called methodName on the object obj, with arguments args.
     *
     * @param obj        The object on which to invoke the method.
     * @param methodName The method name to invoke.
     * @param args       The potential args used in the method.
     * @return An object returned by the invoked method.
     * @throws BadConfigurationException if the methodName doesn't exist for the given object.
     */
    public static Object invokeMethod(Object obj, String methodName, Object[] args)
            throws BadConfigurationException {
        Object retval = null;

        try {
            retval = MethodUtils.invokeExactMethod(obj, methodName, args);
        } catch (NoSuchMethodException e) {
            logger.error("Unable to invoke method {}", methodName);
            throw new BadConfigurationException(e);
        } catch (IllegalAccessException e) {
            logger.error("Unable to invoke method {}", methodName);
            throw new BadConfigurationException(e);
        } catch (InvocationTargetException e) {
            logger.error("Unable to invoke method {}", methodName);
            throw new BadConfigurationException(e);
        }

        return retval;
    }


    /**
     * Test if a class exists in the classPath, trying to load it with its name.
     *
     * @param className The class to test.
     * @return true if the class can be used, false otherwise.
     */
    public static Boolean canBeUsed(String className) {
        Boolean canBeUsed = false;
        try {
            ClassUtils.getClass(className);
            canBeUsed = true;
        } catch (ClassNotFoundException e) {
            logger.warn("Unable to get class {}", className);
        }
        return canBeUsed;
    }
}