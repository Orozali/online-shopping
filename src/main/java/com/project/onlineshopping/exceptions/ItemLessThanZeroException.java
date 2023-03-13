package com.project.onlineshopping.exceptions;

public class ItemLessThanZeroException extends RuntimeException{
    public ItemLessThanZeroException(String msg){
        super(msg);
    }
}
