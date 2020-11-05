package com.cg;

public class DBException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum ExceptionType{
		CONNECTION_FAIL, SQL_ERROR, UPDATE_ERROR, INVALID_PAYROLL_DATA
	}
	ExceptionType type;

	public DBException(String message, ExceptionType type) {
		super(message);
		this.type = type;
	}
}