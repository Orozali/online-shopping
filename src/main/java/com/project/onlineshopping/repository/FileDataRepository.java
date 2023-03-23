package com.project.onlineshopping.repository;

import com.project.onlineshopping.model.FileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileDataRepository extends JpaRepository<FileData,Integer> {
    Optional<FileData> findByName(String name);
}
