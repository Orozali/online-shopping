package com.project.onlineshopping.exceptions;

import com.project.onlineshopping.model.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String msg){
        super(msg);
    }
}
