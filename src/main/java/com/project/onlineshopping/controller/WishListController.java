package com.project.onlineshopping.controller;

import com.project.onlineshopping.exceptions.AuthenticationFailException;
import com.project.onlineshopping.exceptions.ErrorMessage;
import com.project.onlineshopping.exceptions.JwtExpiredException;
import com.project.onlineshopping.model.AuthenticationToken;
import com.project.onlineshopping.model.Product;
import com.project.onlineshopping.model.UserInfo;
import com.project.onlineshopping.model.WishList;
import com.project.onlineshopping.repository.TokenRepository;
import com.project.onlineshopping.service.ProductService;
import com.project.onlineshopping.service.TokenService;
import com.project.onlineshopping.service.WishListService;
import com.project.onlineshopping.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/wishList")
@RequiredArgsConstructor
public class WishListController {
    private final WishListService wishListService;
    private final TokenService tokenService;
    private final ProductService productService;


    @PostMapping("/post/add/{user_id}/{product_id}")
    public ResponseEntity<ApiResponse> addProductToWishList(@PathVariable("user_id") int user_id
                                                            ,@PathVariable("product_id") int product_id){
        wishListService.save(user_id,product_id);
        return new ResponseEntity<>(new ApiResponse(true,"Товар успешно добавлено в избранное"),HttpStatus.CREATED);
    }


    @GetMapping("/get/products/{user_id}")
    public ResponseEntity<List<Product>> getProductsFromWishList(@PathVariable("user_id") int id){
        return new ResponseEntity<>(wishListService.findProducts(id),HttpStatus.OK);
    }


    @DeleteMapping("/delete/{user_id}/{product_id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable("user_id") int user_id
                                                    ,@PathVariable("product_id") int product_id){
        wishListService.delete(user_id,product_id);
        return new ResponseEntity<>(new ApiResponse(true, "Товар успешно удалено из Избранного"),HttpStatus.OK);
    }


    @ExceptionHandler
    public ResponseEntity<ErrorMessage> jwtException(JwtExpiredException e){
        ErrorMessage message = new ErrorMessage(e.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
