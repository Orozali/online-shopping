package com.project.onlineshopping.repository;

import com.project.onlineshopping.model.Cart;
import com.project.onlineshopping.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Cart,Integer> {
//    List<Product> findByUserInfo(int id);
    @Query("SELECT p FROM Cart p where p.userInfo.id = ?1")
    List<Cart> findByUserId(int id);
}
