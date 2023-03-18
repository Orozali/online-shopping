package com.project.onlineshopping.controller;

import com.project.onlineshopping.exceptions.AuthenticationFailException;
import com.project.onlineshopping.exceptions.ErrorMessage;
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

import java.util.Objects;

@RestController
@RequestMapping("/wishList")
@RequiredArgsConstructor
public class WishListController {
    private final WishListService wishListService;
    private final TokenService tokenService;
    private final ProductService productService;


    @PostMapping("/post/add")
    public ResponseEntity<ApiResponse> addProductToWishList(@RequestBody Product product){
        return new ResponseEntity<>(new ApiResponse(true,"A new WishList was created"),HttpStatus.CREATED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> authenticationFail(AuthenticationFailException e){
        ErrorMessage message = new ErrorMessage(e.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
