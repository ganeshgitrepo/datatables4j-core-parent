package org.datatables4j.exception;

public class DataNotFoundException extends Throwable {

	private static final long serialVersionUID = 7240738976355836256L;

	public DataNotFoundException() {}  
	/** 
	* Crée une nouvelle instance de NombreNonValideException 
	* @param message Le message détaillant exception 
	*/  
	public DataNotFoundException(String message) {  
		super(message); 
	}  
	/** 
	* Crée une nouvelle instance de NombreNonValideException 
	* @param cause L'exception à l'origine de cette exception 
	*/  
	public DataNotFoundException(Throwable cause) {  
		super(cause); 
	}  
	/** 
	* Crée une nouvelle instance de NombreNonValideException 
	* @param message Le message détaillant exception 
	* @param cause L'exception à l'origine de cette exception 
	*/  
	public DataNotFoundException(String message, Throwable cause) {  
		super(message, cause); 
	} 
}
