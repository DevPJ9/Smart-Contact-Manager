package com.trantor.phonebookapi.exception;

public class NameNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NameNotFoundException() {
		super("No data found of given name");
	}

}
