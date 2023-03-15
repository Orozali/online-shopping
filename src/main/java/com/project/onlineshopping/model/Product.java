package com.project.onlineshopping.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "Product")
@NoArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "imageurl")
    private String imageURL;
    @Column(name = "price")
    private double price;
    @Column(name = "description")
    private String description;
    @Column(name = "createdat")
    private Date createdAt;
    @Column(name = "carcas")
    private String carcas;
    @Column(name = "meachanism")
    private String meachanism;
    @Column(name = "material")
    private String material;
    @Column(name = "usb")
    private String usb;
    @Column(name = "removable_case")
    private String removable_case;
    @Column(name = "decorative_pillow")
    private String decorative_pillow;
    @Column(name = "delivery_option")
    private String delivery_option;
    @Column(name = "manufacturer")
    private String manufacturer;
    @Column(name = "quantity")
    private Integer quantity;

//    @OneToOne(targetEntity = Image.class)
//    private Image image;

    @OneToOne(targetEntity = Product_size.class)
    @JoinColumn(name = "product_size_id",referencedColumnName = "id")
    private Product_size productSize;
    @ManyToOne
    @JoinColumn(name = "category",referencedColumnName = "name")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "product_type",referencedColumnName = "name")
    private Type type;



}
