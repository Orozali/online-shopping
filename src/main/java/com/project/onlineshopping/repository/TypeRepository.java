package com.project.onlineshopping.repository;

import com.project.onlineshopping.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TypeRepository extends JpaRepository<Type,Integer> {
    Optional<Type> findByName(String name);
    @Query("SELECT p FROM Type p WHERE lower(p.name) LIKE %?1%")
    List<Type> findByNameContaining(String keyword);
}
