package com.project.onlineshopping.exceptions;

public class ModelAlreadyExistException extends RuntimeException{
    public ModelAlreadyExistException(String msg){
        super(msg);
    }
}
