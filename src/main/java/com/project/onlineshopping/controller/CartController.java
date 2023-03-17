package com.project.onlineshopping.controller;

import com.project.onlineshopping.dto.CartDTO;
import com.project.onlineshopping.dto.CartGet;
import com.project.onlineshopping.exceptions.ErrorMessage;
import com.project.onlineshopping.exceptions.ItemLessThanZeroException;
import com.project.onlineshopping.exceptions.ProductNotFoundException;
import com.project.onlineshopping.model.Cart;
import com.project.onlineshopping.model.Product;
import com.project.onlineshopping.model.UserInfo;
import com.project.onlineshopping.service.CardService;
import com.project.onlineshopping.service.ProductService;
import com.project.onlineshopping.service.UserService;
import com.project.onlineshopping.utils.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @PostMapping("/add/{user_id}")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody CartDTO cartDTO,
                                                 @PathVariable("user_id") int user_id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo user = (UserInfo) authentication.getPrincipal();
        System.out.println(user.getFirstName());
        cardService.save(cartDTO,user_id);
        return new ResponseEntity<>(new ApiResponse(true,"Товар успешно добавлен в корзину!"), HttpStatus.OK);
    }
    @GetMapping("/{user_id}")
    public ResponseEntity<List<CartGet>> getCart(@PathVariable("user_id") int id){
        Optional<UserInfo> user = userService.findById(id);
        if(user.isEmpty()){
            throw new ProductNotFoundException("Пользователь не найден!");
        }
        List<CartGet> productList = cardService.getCart(id);
//        Integer quantity = cardService.productQuantity();
//        CartGet cartGet = new CartGet(productList,)
        return new ResponseEntity<>(productList,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{product_id}/{user_id}")
    public ResponseEntity<ApiResponse> deleteProductFromCart(@PathVariable("product_id") int product_id,
                                                             @PathVariable("user_id") int user_id){
        Optional<Product> product = productService.findById(product_id);
        Optional<UserInfo> user = userService.findById(user_id);
        if(product.isEmpty()){
            throw new ProductNotFoundException("Товар не найден!");
        }
        if(user.isEmpty()){
            throw new ProductNotFoundException("Пользователь не найден!");
        }
        cardService.deleteProductById(user.get(),product.get());
        return new ResponseEntity<>(new ApiResponse(true,"Товар успешно удален из корзины"), HttpStatus.OK);
    }


    @ExceptionHandler
    public ResponseEntity<ErrorMessage> productNotFound(ProductNotFoundException exception){
        return new ResponseEntity<>(new ErrorMessage(exception.getMessage()),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorMessage> itemLessThanZero(ItemLessThanZeroException exception){
        return new ResponseEntity<>(new ErrorMessage(exception.getMessage()),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorMessage> tokenFail(ExpiredJwtException e){
        ErrorMessage message = new ErrorMessage(e.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
