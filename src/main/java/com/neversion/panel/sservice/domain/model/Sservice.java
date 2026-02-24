package com.neversion.panel.sservice.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.neversion.panel.sservice.domain.model.enums.CategoryType;
import com.neversion.panel.sserviceitem.domain.model.SserviceItem;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Sservice {

    private Integer id;
    private String name;
    private String description;
    private String imageUrl;
    private CategoryType category;
    @Builder.Default
    private List<SserviceItem> items = new ArrayList<>();

    public void addItem(SserviceItem item) {
        this.items.add(item);
        item.setSservice(this);
    }

    public List<SserviceItem> getItems() {
        return Collections.unmodifiableList(this.items);
    }

    public void removeItem(SserviceItem item) {
        this.items.remove(item);
        item.setSservice(null);
    }

    public Sservice() {
    }

    public Sservice(Integer id,
            String name,
            String description,
            String imageUrl,
            CategoryType category,
            List<SserviceItem> items) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
        this.items = items;
    }

    public Sservice(String name, String description, String imageUrl, CategoryType category) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public Sservice(Integer id,
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