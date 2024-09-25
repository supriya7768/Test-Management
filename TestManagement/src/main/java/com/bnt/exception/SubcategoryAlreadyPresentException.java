package com.bnt.exception;

public class SubcategoryAlreadyPresentException extends RuntimeException {

    public SubcategoryAlreadyPresentException (String message){
        super(message);
    }
    
}
