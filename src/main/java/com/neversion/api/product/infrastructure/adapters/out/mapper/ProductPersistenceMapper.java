package com.neversion.api.product.infrastructure.adapters.out.mapper;

import org.springframework.stereotype.Component;

import com.neversion.api.product.domain.model.Product;
import com.neversion.api.product.infrastructure.adapters.out.ProductEntity;

import io.micrometer.common.lang.NonNull;

@Component
public class ProductPersistenceMapper {

    @NonNull
    public Product toDomain(ProductEntity entity) {
        if (entity == null)
            return null;
        return Product.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .imageUrl(entity.getImageUrl())
                .category(entity.getCategory())
                .build();
    }

    public ProductEntity toEntity(Product domain) {
        if (domain == null)
            return null;
        return ProductEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .description(domain.getDescription())
                .imageUrl(domain.getImageUrl())
                .category(domain.getCategory())
                .build();
    }
}
