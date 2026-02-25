package com.neversion.panel.product.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.neversion.panel.product.domain.model.enums.CategoryType;
import com.neversion.panel.plan.domain.model.Plan;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Product {

    private Integer id;
    private String name;
    private String description;
    private String imageUrl;
    private CategoryType category;
    @Builder.Default
    private List<Plan> items = new ArrayList<>();

    public void addItem(Plan item) {
        this.items.add(item);
        item.setProduct(this);
    }

    public List<Plan> getItems() {
        return Collections.unmodifiableList(this.items);
    }

    public void removeItem(Plan item) {
        this.items.remove(item);
        item.setProduct(null);
    }

    public Product() {
    }

    public Product(Integer id,
            String name,
            String description,
            String imageUrl,
            CategoryType category,
            List<Plan> items) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
        this.items = items;
    }

    public Product(String name, String description, String imageUrl, CategoryType category) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public Product(Integer id,
            String name,
            String description,
            String imageUrl,
            CategoryType category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
    }
}