package com.project.onlineshopping.service;

import com.project.onlineshopping.dto.ProductDTO;
import com.project.onlineshopping.exceptions.CategoryNotFoundException;
import com.project.onlineshopping.model.*;
import com.project.onlineshopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final TypeService typeService;
    private final ProductSizeService productSizeService;

    public List<Product> findAll() {
        return productRepository.findAll();
    }
    @Transactional
    public void save(Product product){
        Optional<Category> category = categoryService.findByName(product.getCategory().getName());
        Optional<Type> type = typeService.findByName(product.getType().getName());
        if(category.isEmpty() || type.isEmpty()){
            throw new CategoryNotFoundException("Категория с таким именем не найдена!");
        }
        product.setCategory(category.get());
        product.setType(type.get());
        if(type.get().getProducts() == null){
            type.get().setProducts(List.of(product));
        }else{
            type.get().getProducts().add(product);
        }
        Product_size productSize = product.getProductSize();
        productSizeService.save(productSize);
        product.setProductSize(product.getProductSize());

        product.setCreatedAt(new Date());
        productRepository.save(product);
    }

    public Optional<Product> findById(int productId) {
        return productRepository.findById(productId);
    }

    public List<Product> findProductByKeyword(String keyword) {
        return null;
    }
}
