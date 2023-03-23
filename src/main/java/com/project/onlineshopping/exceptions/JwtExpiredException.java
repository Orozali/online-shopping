package com.project.onlineshopping.exceptions;

import com.project.onlineshopping.security.JwtService;

public class JwtExpiredException extends RuntimeException{
    public JwtExpiredException(String msg){
        super(msg);
    }
}
