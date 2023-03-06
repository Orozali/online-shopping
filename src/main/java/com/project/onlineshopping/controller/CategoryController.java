package com.project.onlineshopping.controller;

import com.project.onlineshopping.exceptions.CategoryNotFoundException;
import com.project.onlineshopping.exceptions.ErrorMessage;
import com.project.onlineshopping.model.Category;
import com.project.onlineshopping.service.CategoryService;
import com.project.onlineshopping.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping("/")
    public ResponseEntity<List<Category>> categoryList(){
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCategory(@RequestBody Category category){
        categoryService.save(category);
        return new ResponseEntity<>(new ApiResponse(true,"A new category created!"),HttpStatus.CREATED);
    }
    @PostMapping("/update/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryId") int id,@RequestBody Category category){
        categoryService.update(id,category);
        return new ResponseEntity<>(new ApiResponse(true,"Category was updated!"),HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> error(CategoryNotFoundException exception){
        ErrorMessage message = new ErrorMessage(exception.getMsg());
        return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
    }

}