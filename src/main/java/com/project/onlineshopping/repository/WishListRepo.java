package com.project.onlineshopping.repository;

import com.project.onlineshopping.model.UserInfo;
import com.project.onlineshopping.model.WishList;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Repository
public interface WishListRepo extends JpaRepository<WishList, Integer> {
    @Query("select p from WishList p where p.userInfo.id = ?1")
    List<WishList> findWishListsByUserInfoId(int id);
    @Modifying
    @Query("delete from WishList p where p.userInfo.id = ?1 and p.product.id = ?2")
    void deleteByUserIdAndProductId(int userId, int productId);
}
