package com.trantor.phonebookapi.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisior extends ResponseEntityExceptionHandler {

	@ExceptionHandler(NoDataFoundException.class)
	public ResponseEntity<Object> noDataFoundHandler(NoDataFoundException noDataFoundException, WebRequest webRequest) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("Timestamp", LocalDateTime.now());
		body.put("Message", "Data is not comming from database or no data exist in the database");
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DataIsEmptyException.class)
	public ResponseEntity<Object> dataIsEmpty(DataIsEmptyException dataIsEmptyException, WebRequest webRequest) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("Timestamp", LocalDateTime.now());
		body.put("Message", "Not found any record");
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NameNotFoundException.class)
	public ResponseEntity<Object> dataIsEmpty(NameNotFoundException dataIsEmptyException, WebRequest webRequest) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("Timestamp", LocalDateTime.now());
		body.put("Message", "No record found of given first name");
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidFlagValueException.class)
	public ResponseEntity<Object> dataIsEmpty(InvalidFlagValueException invalidFlagValueException,
			WebRequest webRequest) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("Timestamp", LocalDateTime.now());
		body.put("Message", "Invalid Flag Value Please Enter 0 for self databse and 1 for remote database");
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidValueException.class)
	public ResponseEntity<Object> dataIsEmpty(InvalidValueException invalidValueException, WebRequest webRequest) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("Timestamp", LocalDateTime.now());
		body.put("Message", "Wrong value imputted please enter correct value !");
		return new ResponseEntity<>(body, HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@ExceptionHandler(ResourceAccessException.class)
	public ResponseEntity<Object> dataIsEmpty(ResourceAccessException resourceAccessException, WebRequest webRequest) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("Timestamp", LocalDateTime.now());
		body.put("Message", "Unable to Connect to Server Database");
		return new ResponseEntity<>(body, HttpStatus.SERVICE_UNAVAILABLE);
	}
}
