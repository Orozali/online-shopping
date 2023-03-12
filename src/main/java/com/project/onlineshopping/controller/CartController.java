package com.project.onlineshopping.controller;

import com.project.onlineshopping.exceptions.ErrorMessage;
import com.project.onlineshopping.exceptions.ProductNotFoundException;
import com.project.onlineshopping.model.Cart;
import com.project.onlineshopping.model.Product;
import com.project.onlineshopping.model.UserInfo;
import com.project.onlineshopping.service.CardService;
import com.project.onlineshopping.service.ProductService;
import com.project.onlineshopping.service.UserService;
import com.project.onlineshopping.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final ProductService productService;
    private final UserService userService;
    private final CardService cardService;
    @PostMapping("/add/{product_id}/{user_id}")
    public ResponseEntity<ApiResponse> addToCart(@PathVariable("product_id") int product_id,
                                                 @PathVariable("user_id") int user_id){
        Optional<Product> product = productService.findById(product_id);
        Optional<UserInfo> user = userService.findById(user_id);
        if(product.isEmpty()){
            throw new ProductNotFoundException("Товар не найден!");
        }
        if(user.isEmpty()){
            throw new ProductNotFoundException("Пользователь не найден!");
        }
        cardService.save(user.get(),product.get());
        return new ResponseEntity<>(new ApiResponse(true,"Товар успешно добавлен в корзину!"), HttpStatus.OK);
    }
    @GetMapping("/{user_id}")
    public ResponseEntity<List<Product>> getCart(@PathVariable("user_id") int id){
        Optional<UserInfo> user = userService.findById(id);
        if(user.isEmpty()){
            throw new ProductNotFoundException("Пользователь не найден!");
        }
        List<Product> productList = cardService.findProductsByUserId(id);
        System.out.println(productList);
        return new ResponseEntity<>(productList,HttpStatus.OK);
    }


    @ExceptionHandler
    public ResponseEntity<ErrorMessage> productNotFound(ProductNotFoundException exception){
        return new ResponseEntity<>(new ErrorMessage(exception.getMessage()),HttpStatus.BAD_REQUEST);
    }
}
