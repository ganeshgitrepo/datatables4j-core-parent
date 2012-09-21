package org.datatables4j.exception;

/**
 * Raised if there's something wrong in the datatables4j configuration file (datatables4j(-default).properties).
 * 
 * @author Thibault Duchateau
 */
public class BadConfigurationException extends Exception {

	private static final long serialVersionUID = 3243845798907773547L;

	public BadConfigurationException() {
	};

	/**
	 * Crée une nouvelle instance de NombreNonValideException
	 * 
	 * @param message
	 *            Le message détaillant exception
	 */
	public BadConfigurationException(String message) {
		super(message);
	}

	/**
	 * Crée une nouvelle instance de NombreNonValideException
	 * 
	 * @param cause
	 *            L'exception à l'origine de cette exception
	 */
	public BadConfigurationException(Throwable cause) {
		super(cause);
	}

	/**
	 * Crée une nouvelle instance de NombreNonValideException
	 * 
	 * @param message
	 *            Le message détaillant exception
	 * @param cause
	 *            L'exception à l'origine de cette exception
	 */
	public BadConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}
}
