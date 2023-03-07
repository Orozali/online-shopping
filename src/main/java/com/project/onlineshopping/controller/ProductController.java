package com.project.onlineshopping.controller;

import com.project.onlineshopping.dto.ProductDTO;
import com.project.onlineshopping.exceptions.CategoryNotFoundException;
import com.project.onlineshopping.exceptions.ErrorMessage;
import com.project.onlineshopping.model.Category;
import com.project.onlineshopping.model.Product;
import com.project.onlineshopping.service.ProductService;
import com.project.onlineshopping.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ModelMapper modelMapper;
    @GetMapping("/")
    public ResponseEntity<List<Product>> productList(){
        return new ResponseEntity<>(productService.findAll(),HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDTO productDTO){
        Product product = convert(productDTO);
        productService.save(product);
        return new ResponseEntity<>(new ApiResponse(true
                ,"A new product created"),HttpStatus.CREATED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> categoryNotFound(CategoryNotFoundException exception){
        ErrorMessage msg = new ErrorMessage(exception.getMsg());
        return new ResponseEntity<>(msg,HttpStatus.BAD_REQUEST);
    }

    public Product convert(ProductDTO productDTO){
        Category category = new Category();
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setImageURL(productDTO.getImageURL());

        category.setName(productDTO.getCategoryDTO().getName());
        product.setCategory(category);
        return product;
    }
}
