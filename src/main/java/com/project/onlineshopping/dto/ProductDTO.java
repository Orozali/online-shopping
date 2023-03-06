package com.project.onlineshopping.dto;

import com.project.onlineshopping.model.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
public class ProductDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String imageURL;
    private double price;
    private String description;
    @ManyToOne
    @JoinColumn(name = "category_id",referencedColumnName = "id")
    private CategoryDTO categoryDTO;

}
