package com.project.onlineshopping.controller;

import com.project.onlineshopping.dto.ProductDTO;
import com.project.onlineshopping.exceptions.CategoryNotFoundException;
import com.project.onlineshopping.exceptions.ErrorMessage;
import com.project.onlineshopping.exceptions.ProductNotFoundException;
import com.project.onlineshopping.model.*;
import com.project.onlineshopping.service.ProductService;
import com.project.onlineshopping.utils.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ModelMapper modelMapper;


    @GetMapping("/get/all")
    public ResponseEntity<List<Product>> productList(){
        return new ResponseEntity<>(productService.findAll(),HttpStatus.OK);
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable("id") int id){

        Optional<Product> product = productService.findById(id);
        if(product.isEmpty()){
            throw new ProductNotFoundException("Такой товар не найден!");
        }
        return new ResponseEntity<>(product.get(),HttpStatus.OK);
    }


    @PostMapping("/post/create")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDTO productDTO){
        Product product = convertViaModelMapper(productDTO);
        productService.save(product);
        return new ResponseEntity<>(new ApiResponse(true
                ,"A new product created"),HttpStatus.CREATED);
    }


    @ExceptionHandler
    public ResponseEntity<ErrorMessage> categoryNotFound(CategoryNotFoundException exception){
        ErrorMessage msg = new ErrorMessage(exception.getMsg());
        return new ResponseEntity<>(msg,HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    public ResponseEntity<ErrorMessage> productNotFound(ProductNotFoundException exception){
        ErrorMessage msg = new ErrorMessage(exception.getMessage());
        return new ResponseEntity<>(msg,HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    public ResponseEntity<ErrorMessage> tokenFail(ExpiredJwtException e){
        ErrorMessage message = new ErrorMessage(e.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }


    public Product convertViaModelMapper(ProductDTO productDTO){
        return modelMapper.map(productDTO, Product.class);
    }

}
