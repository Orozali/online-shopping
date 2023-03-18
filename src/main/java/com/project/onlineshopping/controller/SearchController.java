package com.project.onlineshopping.controller;

import com.project.onlineshopping.model.Product;
import com.project.onlineshopping.model.Type;
import com.project.onlineshopping.service.ProductService;
import com.project.onlineshopping.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
