package com.project.onlineshopping.controller;

import com.project.onlineshopping.exceptions.CategoryNotFoundException;
import com.project.onlineshopping.exceptions.ErrorMessage;
import com.project.onlineshopping.exceptions.ModelAlreadyExistException;
import com.project.onlineshopping.model.Category;
import com.project.onlineshopping.model.Type;
import com.project.onlineshopping.service.CategoryService;
import com.project.onlineshopping.service.TypeService;
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
    private final TypeService typeService;
    @GetMapping("/get/")
    public ResponseEntity<List<Category>> categoryList(){
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }
    @PostMapping("/post/create")
    public ResponseEntity<ApiResponse> createCategory(@RequestBody Category category){
        categoryService.save(category);
        return new ResponseEntity<>(new ApiResponse(true,"A new category was created!"),HttpStatus.CREATED);
    }
    @PostMapping("/post/update/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryId") int id,@RequestBody Category category){
        categoryService.update(id,category);
        return new ResponseEntity<>(new ApiResponse(true,"Category was updated!"),HttpStatus.OK);
    }

    @GetMapping("/get/type")
    public ResponseEntity<List<Type>> listOfTypes(){
        return new ResponseEntity<>(typeService.findAll(),HttpStatus.OK);
    }
    @PostMapping("/post/type/update/{id}")
    public ResponseEntity<ApiResponse> listOfTypes(@PathVariable("id") int id,@RequestBody Type type){
        typeService.update(type,id);
        return new ResponseEntity<>(new ApiResponse(true,"Category was updated!"),HttpStatus.OK);
    }
    @PostMapping("/post/type/create")
    public ResponseEntity<ApiResponse> createType(@RequestBody Type type){
        typeService.save(type);
        return new ResponseEntity<>(new ApiResponse(true,"A new type was created!"),HttpStatus.CREATED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> error(CategoryNotFoundException exception){
        ErrorMessage message = new ErrorMessage(exception.getMsg());
        return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorMessage> modelAlreadyExist(ModelAlreadyExistException exception){
        ErrorMessage message = new ErrorMessage(exception.getMessage());
        return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
    }

}
