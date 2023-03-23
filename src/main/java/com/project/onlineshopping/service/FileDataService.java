package com.project.onlineshopping.service;

import com.project.onlineshopping.model.FileData;
import com.project.onlineshopping.repository.FileDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileDataService {
    private final FileDataRepository fileDataRepository;

    @Transactional
    public void save(FileData fileData){
        fileDataRepository.save(fileData);
    }
}
