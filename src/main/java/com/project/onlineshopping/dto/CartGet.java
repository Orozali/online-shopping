package com.project.onlineshopping.dto;

import com.project.onlineshopping.model.Product;
import com.project.onlineshopping.model.UserInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class CartGet {
    private Product products;
    private Integer quantity;
    public CartGet(Product products, Integer quantity){
        this.products = products;
        this.quantity = quantity;
    }
}
