package org.datatables4j.util;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.ClassUtils;
import org.datatables4j.exception.BadConfigurationException;
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
