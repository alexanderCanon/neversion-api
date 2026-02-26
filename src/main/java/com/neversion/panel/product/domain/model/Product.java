package com.neversion.panel.product.domain.model;

import com.neversion.panel.product.domain.model.enums.CategoryType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Product {

    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private CategoryType category;

    public Product() {
    }

    public Product(Long id, String name, String description, String imageUrl, CategoryType category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
    }
}
