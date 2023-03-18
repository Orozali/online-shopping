package com.project.onlineshopping.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class BindingResultExceptions extends RuntimeException{
    public BindingResultExceptions(String errors){
        super(errors);
    }
}
