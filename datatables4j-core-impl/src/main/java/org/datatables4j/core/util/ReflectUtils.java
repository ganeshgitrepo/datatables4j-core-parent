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
package org.datatables4j.core.util;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.ClassUtils;
import org.datatables4j.core.api.exception.BadConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO
 * 
 * @author tduchate
 * 
 */
public class ReflectUtils {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(ReflectUtils.class);

	/**
	 * TODO
	 * 
	 * @param className
	 * @return
	 * @throws BadConfigurationException
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
	 * TODO
	 * 
	 * @param klass
	 * @return
	 * @throws BadConfigurationException
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
	 * TODO
	 * 
	 * @param obj
	 * @param methodName
	 * @param args
	 * @return
	 * @throws BadConfigurationException
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
	 * TODO
	 * @param className
	 * @return
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
