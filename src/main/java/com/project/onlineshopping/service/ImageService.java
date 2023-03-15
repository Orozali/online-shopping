//package com.project.onlineshopping.service;
//
//import com.project.onlineshopping.model.Image;
//import com.project.onlineshopping.repository.ImageRepository;
//import com.project.onlineshopping.utils.ImageUtil;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class ImageService {
//    private final ImageRepository imageRepository;
//
//    public String uploadImage(MultipartFile file) throws IOException {
//
//        Image imageData = imageRepository.save(Image.builder()
//                .name(file.getOriginalFilename())
//                .type(file.getContentType())
//                .imageData(ImageUtil.compressImage(file.getBytes())).build());
//        if (imageData != null) {
//            return "file uploaded successfully : " + file.getOriginalFilename();
//        }
//        return null;
//    }
//
//    public byte[] downloadImage(String fileName){
//        Optional<Image> dbImageData = imageRepository.findByName(fileName);
//        return ImageUtil.decompressImage(dbImageData.get().getImageData());
//    }
//}
