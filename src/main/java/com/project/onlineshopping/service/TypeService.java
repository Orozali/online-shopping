package com.project.onlineshopping.service;

import com.project.onlineshopping.model.Type;
import com.project.onlineshopping.repository.TypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
