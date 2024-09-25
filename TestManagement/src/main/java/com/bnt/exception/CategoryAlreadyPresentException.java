package com.bnt.exception;

public class CategoryAlreadyPresentException extends RuntimeException {

    public CategoryAlreadyPresentException (String message){
        super(message);
    }
}
