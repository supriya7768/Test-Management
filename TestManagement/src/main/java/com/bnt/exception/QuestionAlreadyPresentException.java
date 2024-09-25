package com.bnt.exception;

public class QuestionAlreadyPresentException extends RuntimeException{
    
    public QuestionAlreadyPresentException (String message){
        super(message);
    }
}
