package com.ef.util;

import java.io.Serializable;
/**
 * 
 * @author abhishek.kumar
 *
 */
public class ResultObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object object;
	private long code;
	private String message;
	private Exception exception;
	public ResultObject(long code) {
		super();
		this.code = code;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public long getCode() {
		return code;
	}
	public void setCode(long code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}
	
	
	

}
