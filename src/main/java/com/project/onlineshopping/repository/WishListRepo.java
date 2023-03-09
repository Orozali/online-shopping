package com.project.onlineshopping.repository;

import com.project.onlineshopping.model.WishList;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface WishListRepo extends JpaRepository<WishList, Integer> {
}
