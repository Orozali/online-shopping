package com.project.onlineshopping.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.onlineshopping.dto.ProductDTO;
import com.project.onlineshopping.exceptions.CategoryNotFoundException;
import com.project.onlineshopping.exceptions.ErrorMessage;
import com.project.onlineshopping.exceptions.JwtExpiredException;
import com.project.onlineshopping.exceptions.ProductNotFoundException;
import com.project.onlineshopping.model.*;
import com.project.onlineshopping.service.ProductService;
import com.project.onlineshopping.utils.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;



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


//    @PostMapping("/post/uploadImage")
//    public ResponseEntity<ApiResponse> uplaodImage(@RequestParam("image") MultipartFile multipartFile){
//        try{
//            imageService.save(multipartFile);
//        }catch (IOException e){
//            System.out.println(e.getMessage());
//        }
//        return new ResponseEntity<>(new ApiResponse(true,"success"),HttpStatus.OK);
//    }
//
//    @GetMapping("/download/{fileName}")
//    public ResponseEntity<byte[]> downloadImage(@PathVariable String fileName) {
//        byte[] image = imageService.downloadImage(fileName);
//        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(image);
//    }


    @PostMapping(value = {"/post/create"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponse> createProduct(@RequestParam("product") String productDTO
                                                     ,@RequestParam("image") MultipartFile multipartFile){
        try {
            ProductDTO product = objectMapper.readValue(productDTO, ProductDTO.class);
            Product product1 = convertViaModelMapper(product);
            productService.save(product1,multipartFile);
        }catch(JsonProcessingException e){
            return new ResponseEntity<>(new ApiResponse(false,"JsonProcessingException"),HttpStatus.BAD_REQUEST);
        }catch (IOException e){
            return  new ResponseEntity<>(new ApiResponse(false, "IOException"),HttpStatus.BAD_REQUEST);
        }

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


    @ExceptionHandler
    public ResponseEntity<ErrorMessage> jwtException(JwtExpiredException e){
        ErrorMessage message = new ErrorMessage(e.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
    public Product convertViaModelMapper(ProductDTO productDTO){
        return modelMapper.map(productDTO, Product.class);
    }

}
