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
    @ManyToOne
    @JoinColumn(name = "category",referencedColumnName = "name")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "product_type",referencedColumnName = "name")
    private Type type;

}
