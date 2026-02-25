package com.neversion.panel.plan.infrastructure.adapters.in.rest.dto;

import com.neversion.panel.plan.domain.model.ProductPrice;
import com.neversion.panel.plan.domain.model.enums.AccountType;

public record PlanResponse(
        Long id,
        ProductPrice price,
        String duration,
        AccountType accountType) {
}
