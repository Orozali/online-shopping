package com.project.onlineshopping.controller;

import com.project.onlineshopping.exceptions.ErrorMessage;
import com.project.onlineshopping.exceptions.JwtExpiredException;
import com.project.onlineshopping.model.Product;
import com.project.onlineshopping.model.Type;
import com.project.onlineshopping.service.ProductService;
import com.project.onlineshopping.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {
    private final TypeService typeService;

    @PostMapping("/products")
    public ResponseEntity<List<Product>> search(@RequestParam("keyword") String keyword){
        keyword = keyword.toLowerCase();
        List<Product> products = typeService.findByKeyword(keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    @ExceptionHandler
    public ResponseEntity<ErrorMessage> jwtException(JwtExpiredException e){
        ErrorMessage message = new ErrorMessage(e.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
