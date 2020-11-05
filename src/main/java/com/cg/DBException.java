package com.cg;

public class DBException extends Exception {

	public enum ExceptionType{
		CONNECTION_FAIL, SQL_ERROR, UPDATE_ERROR, INVALID_PAYROLL_DATA, RETRIEVE_ERROR
	}
	ExceptionType type;

	public DBException(String message, ExceptionType type) {
		super(message);
		this.type = type;
	}
}