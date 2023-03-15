//package com.project.onlineshopping.model;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Table(name = "Image")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class Image {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String name;
//    private String type;
//    @Lob
//    @Column(name = "imagedata",length = 1000)
//    private byte[] imageData;
//
//    @OneToOne
//    @JoinColumn(name = "product_id",referencedColumnName = "id")
//    private Product product;
//}
