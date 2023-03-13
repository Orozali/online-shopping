package com.project.onlineshopping.repository;

import com.project.onlineshopping.model.Cart;
import com.project.onlineshopping.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Cart,Integer> {
    @Query("SELECT p FROM Cart p where p.userInfo.id = ?1")
    List<Cart> findByUserId(int id);
    @Modifying
    @Query("delete FROM Cart p where p.userInfo.id = ?1 AND p.product.id = ?2")
    void deleteProduct(Integer id, Integer id1);
    @Query("select p from Cart p where p.product.id = ?1")
    Optional<Cart> findByProductId(Integer id);
}
