package com.jpmc.assignments.simplestock.exception;

/**
 * Custom exception for any super simple stock errors.
 * 
 * @author jnair1
 *
 */
public class SimpleStockException extends Exception {

	private static final long serialVersionUID = 1L;

	public SimpleStockException(String message) {
		super(message);
	}

}
