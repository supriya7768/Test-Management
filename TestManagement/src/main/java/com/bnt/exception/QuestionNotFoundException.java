package com.bnt.exception;

public class QuestionNotFoundException extends RuntimeException {

    public QuestionNotFoundException (String message){
        super(message);
    }

    public QuestionNotFoundException (String message, int id){
        super(message);
    }
}
