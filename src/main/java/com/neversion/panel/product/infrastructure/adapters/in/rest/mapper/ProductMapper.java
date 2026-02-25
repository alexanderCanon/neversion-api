package com.neversion.panel.product.infrastructure.adapters.in.rest.mapper;

import org.springframework.stereotype.Component;

import com.neversion.panel.plan.infrastructure.adapters.in.rest.mapper.PlanMapper;
import com.neversion.panel.product.domain.model.Product;
import com.neversion.panel.product.domain.model.enums.CategoryType;
import com.neversion.panel.product.infrastructure.adapters.in.rest.dto.ProductRequest;
import com.neversion.panel.product.infrastructure.adapters.in.rest.dto.ProductResponse;

@Component
public class ProductMapper {

    public Product toDomain(ProductRequest request) {

        if (request == null)
            return null;

        Product product = Product.builder()
                .name(request.name())
                .description(request.description())
                .imageUrl(request.imageUrl())
                .category(CategoryType.valueOf(request.category().toUpperCase()))
                .build();

        if (request.items() != null) {
            request.items().forEach(itemRequest -> {
                product.addItem(PlanMapper.toDomain(itemRequest));
            });
        }
        return product;
    }

    public ProductResponse toResponse(Product product) {

        if (product == null)
            return null;

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategory(),
                product.getItems().stream()
                        .map(PlanMapper::toResponse)
                        .toList());
    }

}
