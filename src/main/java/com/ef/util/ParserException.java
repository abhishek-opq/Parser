package com.ef.util;
/**
 * 
 * @author abhishek.kumar
 *
 */
public class ParserException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message=null;
	@Override
	public String getMessage() {

		return message; 
	}
	
	public ParserException() {

	}
	
	public ParserException(String message) {
		this.message=message;
	}
	

}
