package com.project.onlineshopping.service;

import com.project.onlineshopping.exceptions.ModelAlreadyExistException;
import com.project.onlineshopping.model.Product;
import com.project.onlineshopping.model.Type;
import com.project.onlineshopping.repository.TypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TypeService {
    private final TypeRepository typeRepository;

    public List<Type> findAll() {
        return typeRepository.findAll();
    }
    @Transactional
    public void save(Type type) {
        typeRepository.save(type);
    }

    public Optional<Type> findByName(String name) {
        return typeRepository.findByName(name);
    }
    @Transactional
    public void update(Type updateType,int id) {
        Optional<Type> findByName = typeRepository.findByName(updateType.getName());
        if(findByName.isPresent()){
            throw new ModelAlreadyExistException("Тип с таким именем уже существует!");
        }
        Optional<Type> type = typeRepository.findById(id);
        updateType.setId(type.get().getId());
        typeRepository.save(updateType);
    }

    public List<Product> findByKeyword(String keyword) {
//        String s1 = keyword.substring(0, 1).toUpperCase();  // first letter = J
//        String s2 = keyword.substring(1).toLowerCase();     // after 1st letter = avatpoint
//        keyword = s1 + s2; // J + avatpoint
        List<Type> types = typeRepository.findByNameContaining(keyword);
        List<Product> products = new ArrayList<>();
        for(Type type: types){
            products.addAll(type.getProducts());
        }
        return products;
    }
}
