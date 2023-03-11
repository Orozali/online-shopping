package com.project.onlineshopping.dto;

import com.project.onlineshopping.model.Category;
import com.project.onlineshopping.model.Product_size;
import com.project.onlineshopping.service.ProductService;
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
    private String name;
    private String imageURL;
    private double price;
    private String description;
    private String carcas;
    private String meachanism;
    private String material;
    private String usb;
    private String removable_case;
    private String decorative_pillow;
    private String delivery_option;
    private String manufacturer;
    private CategoryDTO categoryDTO;
    private TypeDTO typeDTO;
    private ProductSizeDTO productSizeDTO;
}
