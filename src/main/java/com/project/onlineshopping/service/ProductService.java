package com.project.onlineshopping.service;

import com.project.onlineshopping.exceptions.CategoryNotFoundException;
import com.project.onlineshopping.model.Category;
import com.project.onlineshopping.model.Product;
import com.project.onlineshopping.model.Product_size;
import com.project.onlineshopping.model.Type;
import com.project.onlineshopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void save(Product product) {
        Optional<Category> category = categoryService.findByName(product.getCategory().getName());
        Optional<Type> type = typeService.findByName(product.getType().getName());
        if(category.isEmpty() || type.isEmpty()){
            throw new CategoryNotFoundException("Категория с таким именем не найдена!");
        }
        product.setCategory(category.get());
        product.setType(type.get());

        Product_size productSize = product.getProductSize();
        productSizeService.save(productSize);
        product.setProductSize(product.getProductSize());

        product.setCreatedAt(new Date());
        productRepository.save(product);
    }

    public Optional<Product> findById(int productId) {
        return productRepository.findById(productId);
    }
}
