package com.trantor.phonebookapi.service.extraservice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class AppExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public List<String> handleInvalidArgument(ConstraintViolationException ex) {
        ArrayList<String> errorList = new ArrayList<>();
        ex.getConstraintViolations()
                .forEach(err ->
                        errorList.add(err.getMessage()));
        return errorList;
    }


    @ResponseStatus
    @ExceptionHandler(ResourceAccessException.class)
    public String handleConnectionTimeout() {
        return "Connection Is Not Established | Please try again later";
    }

    //    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(NullPointerException.class)
    public String handleFirstNameNull() {
        return "Doesn't Exist ! Please enter valid data";
    }



}
