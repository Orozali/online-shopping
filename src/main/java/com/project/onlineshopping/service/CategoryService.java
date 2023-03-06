package com.project.onlineshopping.service;

import com.project.onlineshopping.exceptions.CategoryNotFoundException;
import com.project.onlineshopping.model.Category;
import com.project.onlineshopping.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll(Sort.by("id"));
    }
    @Transactional
    public void save(Category category) {
        categoryRepository.save(category);
    }
    @Transactional
    public void update(int id, Category updatedCategory) {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()){
            category.get().setName(updatedCategory.getName());
            categoryRepository.save(category.get());
        }else {
            throw new CategoryNotFoundException("Категория с таким Id не найден!");
        }
    }
}
