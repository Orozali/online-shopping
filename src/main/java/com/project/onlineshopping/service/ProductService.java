package com.project.onlineshopping.service;

import com.project.onlineshopping.exceptions.CategoryNotFoundException;
import com.project.onlineshopping.model.Category;
import com.project.onlineshopping.model.Product;
import com.project.onlineshopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public List<Product> findAll() {
        return productRepository.findAll();
    }
    @Transactional
    public void save(Product product) {
        Optional<Category> category = categoryService.findByName(product.getCategory().getName());
        if(category.isEmpty()){
            throw new CategoryNotFoundException("Категория с таким именем не найдена!");
        }
        product.setCategory(category.get());
        productRepository.save(product);
    }
}
