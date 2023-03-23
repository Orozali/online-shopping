package com.project.onlineshopping.service;

import com.project.onlineshopping.dto.ProductDTO;
import com.project.onlineshopping.exceptions.CategoryNotFoundException;
import com.project.onlineshopping.model.*;
import com.project.onlineshopping.repository.FileDataRepository;
//import com.project.onlineshopping.repository.ImageRepository;
import com.project.onlineshopping.repository.ProductRepository;
import com.project.onlineshopping.utils.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final TypeService typeService;
    private final ProductSizeService productSizeService;
//    private final ImageRepository imageRepository;
    private final FileDataRepository fileDataRepository;

    private final String FILE_PATH = "/home/oroz/Main/Java/Spring_Projects/online-shopping/src/main/resources/static/images/";

    public List<Product> findAll() {
        return productRepository.findAll();
    }
    @Transactional
    public void save(Product product, MultipartFile multipartFile) throws IOException {
        FileData image = uploadToSystem(multipartFile);
        fileDataRepository.save(image);
        product.setFileData(image);

        Optional<Category> category = categoryService.findByName(product.getCategory().getName());
        Optional<Type> type = typeService.findByName(product.getType().getName());
        if(category.isEmpty() || type.isEmpty()){
            throw new CategoryNotFoundException("Категория с таким именем не найдена!");
        }
        product.setCategory(category.get());
        product.setType(type.get());
        if(type.get().getProducts() == null){
            type.get().setProducts(List.of(product));
        }else{
            type.get().getProducts().add(product);
        }

        Product_size productSize = product.getProductSize();
        productSizeService.save(productSize);
        product.setProductSize(product.getProductSize());

        product.setCreatedAt(new Date());
        productRepository.save(product);
    }

//    public Image uploadImage(MultipartFile multipartFile) throws IOException {
//        var image = Image.builder()
//                .name(multipartFile.getOriginalFilename())
//                .type(multipartFile.getContentType())
//                .picByte(ImageUtil.compressImage(multipartFile.getBytes()))
//                .build();
//        return image;
//    }
    public FileData uploadToSystem(MultipartFile file) throws IOException {
        String filePath = FILE_PATH + file.getOriginalFilename();
        FileData fileData = FileData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath)
                .build();
        file.transferTo(new File(filePath));
        return fileData;
    }
    public byte[] downloadImageFromSystem(String fileName) throws IOException {
        Optional<FileData> fileData = fileDataRepository.findByName(fileName);
        String filePath = fileData.get().getFilePath();
        return Files.readAllBytes(new File(filePath).toPath());
    }

    public Optional<Product> findById(int productId) {
        return productRepository.findById(productId);
    }

    public List<Product> findProductByKeyword(String keyword) {
        return null;
    }
}
