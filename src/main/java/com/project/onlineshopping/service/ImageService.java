//package com.project.onlineshopping.service;
//
//import com.project.onlineshopping.model.Image;
//import com.project.onlineshopping.repository.ImageRepository;
//import com.project.onlineshopping.utils.ImageUtil;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.Optional;
//
////@Service
////@RequiredArgsConstructor
////@Transactional(readOnly = true)
//public class ImageService {
////    private final ImageRepository imageRepository;
//    @Transactional
//    public void save(MultipartFile multipartFile) throws IOException {
//        var image = Image.builder()
//                .name(multipartFile.getOriginalFilename())
//                .type(multipartFile.getContentType())
//                .picByte(ImageUtil.compressImage(multipartFile.getBytes()))
//                .build();
//        imageRepository.save(image);
//    }
//
//    public byte[] downloadImage(String fileName){
//        Optional<Image> imageData = imageRepository.findByName(fileName);
//        return ImageUtil.decompressImage(imageData.get().getPicByte());
//    }
//}
