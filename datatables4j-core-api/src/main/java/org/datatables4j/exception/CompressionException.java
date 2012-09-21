package org.datatables4j.exception;

public class CompressionException extends Exception {

	private static final long serialVersionUID = 3243845798907773547L;

	public CompressionException() {
	};

	/**
	 * Crée une nouvelle instance de NombreNonValideException
	 * 
	 * @param message
	 *            Le message détaillant exception
	 */
	public CompressionException(String message) {
		super(message);
	}

	/**
	 * Crée une nouvelle instance de NombreNonValideException
	 * 
	 * @param cause
	 *            L'exception à l'origine de cette exception
	 */
	public CompressionException(Throwable cause) {
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
	public CompressionException(String message, Throwable cause) {
		super(message, cause);
	}
}
