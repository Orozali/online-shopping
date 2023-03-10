package com.project.onlineshopping.controller;

import com.project.onlineshopping.dto.ProductDTO;
import com.project.onlineshopping.exceptions.CategoryNotFoundException;
import com.project.onlineshopping.exceptions.ErrorMessage;
import com.project.onlineshopping.exceptions.ProductNotFoundException;
import com.project.onlineshopping.model.Category;
import com.project.onlineshopping.model.Product;
import com.project.onlineshopping.model.Product_size;
import com.project.onlineshopping.model.Type;
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
import java.util.Optional;

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
    @GetMapping("/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable("id") int id){
        Optional<Product> product = productService.findById(id);
        if(product.isEmpty()){
            throw new ProductNotFoundException("Такой товар не найден!");
        }
        return new ResponseEntity<>(product.get(),HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDTO productDTO){
//        Product product = convert(productDTO);
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
    public Product convertViaModelMapper(ProductDTO productDTO){
        return modelMapper.map(productDTO, Product.class);
    }
    public Product convert(ProductDTO productDTO){
        Category category = new Category();
        Type type = new Type();
        Product_size productSize = new Product_size(productDTO.getProductSizeDTO().getLength()
                ,productDTO.getProductSizeDTO().getWidth(),productDTO.getProductSizeDTO().getHeight());
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setImageURL(productDTO.getImageURL());
        product.setCarcas(productDTO.getCarcas());
        product.setMaterial(productDTO.getMaterial());
        product.setDecorative_pillow(productDTO.getDecorative_pillow());
        product.setDelivery_option(productDTO.getDelivery_option());
        product.setManufacturer(productDTO.getManufacturer());
        product.setMeachanism(productDTO.getMeachanism());
        product.setRemovable_case(productDTO.getRemovable_case());
        product.setUsb(productDTO.getUsb());

        type.setName(productDTO.getTypeDTO().getName());
        category.setName(productDTO.getCategoryDTO().getName());

        product.setCategory(category);
        product.setType(type);
        product.setProductSize(productSize);
        return product;
    }
}
