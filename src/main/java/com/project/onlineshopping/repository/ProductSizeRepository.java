package com.project.onlineshopping.repository;

import com.project.onlineshopping.model.Product_size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSizeRepository extends JpaRepository<Product_size,Integer> {
}
