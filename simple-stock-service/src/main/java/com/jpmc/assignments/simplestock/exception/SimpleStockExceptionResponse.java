package com.jpmc.assignments.simplestock.exception;

/**
 * 
 * @author jnair1
 *
 */
public class SimpleStockExceptionResponse {
	
	private int errorCode;
	private String message;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
