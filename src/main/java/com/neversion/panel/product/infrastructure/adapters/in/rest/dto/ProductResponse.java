package com.neversion.panel.product.infrastructure.adapters.in.rest.dto;

import java.util.List;

import com.neversion.panel.plan.infrastructure.adapters.in.rest.dto.PlanResponse;
import com.neversion.panel.product.domain.model.enums.CategoryType;

public record ProductResponse(
        Integer id,
        String name,
        String description,
        CategoryType category,
        List<PlanResponse> items) {
}
