package com.project.onlineshopping.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@NoArgsConstructor
public class UserAlreadyExistException extends RuntimeException{
    private String msg;
    public UserAlreadyExistException(String msg){
        super(msg);
    }
}
