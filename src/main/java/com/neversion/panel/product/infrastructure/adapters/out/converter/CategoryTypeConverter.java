package com.neversion.panel.product.infrastructure.adapters.out.converter;

import com.neversion.panel.infrastructure.EnumConverter;
import com.neversion.panel.product.domain.model.enums.CategoryType;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CategoryTypeConverter extends EnumConverter<CategoryType> {

    public CategoryTypeConverter() {
        super(CategoryType.class);
    }
}
