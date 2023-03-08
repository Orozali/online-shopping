package com.project.onlineshopping.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Product")
@NoArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String imageURL;
    private double price;
    private String description;
    @ManyToOne
    @JoinColumn(name = "category",referencedColumnName = "name")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "product_type",referencedColumnName = "name")
    private Type type;

}
