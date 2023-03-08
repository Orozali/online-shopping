package com.project.onlineshopping.repository;

import com.project.onlineshopping.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeRepository extends JpaRepository<Type,Integer> {
    Optional<Type> findByName(String name);
}
