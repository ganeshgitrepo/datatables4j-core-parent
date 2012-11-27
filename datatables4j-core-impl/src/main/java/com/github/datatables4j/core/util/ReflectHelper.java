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