package com.neversion.panel.product.infrastructure.adapters.out.mapper;

import org.springframework.stereotype.Component;

import com.neversion.panel.product.domain.model.Product;
import com.neversion.panel.product.infrastructure.adapters.out.ProductEntity;
import com.neversion.panel.plan.infrastructure.adapters.out.PlanEntity;
import com.neversion.panel.plan.infrastructure.adapters.out.mapper.PlanPersistenceMapper;

@Component
public class ProductPersistenceMapper {

    private final PlanPersistenceMapper itemPersistenceMapper;

    public ProductPersistenceMapper(PlanPersistenceMapper itemPersistenceMapper) {
        this.itemPersistenceMapper = itemPersistenceMapper;
    }

    public Product toDomain(ProductEntity entity) {
        if (entity == null)
            return null;
        Product product = Product.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .imageUrl(entity.getImageUrl())
                .category(entity.getCategory())
                .build();

        if (entity.getItems() != null) {
            entity.getItems().forEach(item -> {
                product.addItem(itemPersistenceMapper.toDomain(item));
            });
        }
        return product;
    }

    public ProductEntity toEntity(Product domain) {
        if (domain == null)
            return null;
        ProductEntity entity = ProductEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .description(domain.getDescription())
                .imageUrl(domain.getImageUrl())
                .category(domain.getCategory())
                .build();

        domain.getItems().forEach(itemDomain -> {
            PlanEntity itemEntity = itemPersistenceMapper.toEntity(itemDomain);
            entity.addItem(itemEntity);
        });

        return entity;
    }
}
