package com.project.onlineshopping.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Product_size")
@NoArgsConstructor
@Getter
@Setter
public class Product_size {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "width")
    private Integer width;
    @Column(name = "height")
    private Integer height;
    @Column(name = "length")
    private Integer length;
    public Product_size(Integer length,Integer width,Integer height){
        this.length = length;
        this.width = width;
        this.height = height;
    }
}
