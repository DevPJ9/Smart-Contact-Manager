package com.trantor.phonebookapi.exception;

public class DataIsEmptyException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DataIsEmptyException() {
		super("Data is Empty Not found any record !");
	}
}
