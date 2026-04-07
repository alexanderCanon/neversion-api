package com.neversion.api.product.infrastructure.adapters.out.converter;

import com.neversion.api.infrastructure.EnumConverter;
import com.neversion.api.product.domain.model.enums.CategoryType;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CategoryTypeConverter extends EnumConverter<CategoryType> {

    public CategoryTypeConverter() {
        super(CategoryType.class);
    }
}
