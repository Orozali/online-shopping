package com.project.onlineshopping.exceptions;

import com.project.onlineshopping.repository.TokenRepository;

public class TokenNullException extends RuntimeException{
    public TokenNullException(String msg){
        super(msg);
    }
}
