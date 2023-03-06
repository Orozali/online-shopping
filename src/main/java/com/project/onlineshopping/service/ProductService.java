package com.project.onlineshopping.service;

import com.project.onlineshopping.model.Product;
import com.project.onlineshopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }
    @Transactional
    public void save(Product product) {
        productRepository.save(product);
    }
}
