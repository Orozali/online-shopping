package com.project.onlineshopping.service;

import com.project.onlineshopping.model.Product_size;
import com.project.onlineshopping.repository.ProductSizeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductSizeService {
    private final ProductSizeRepository productSizeRepository;
    @Transactional
    public void save(Product_size productSize) {
        productSizeRepository.save(productSize);
    }
}
