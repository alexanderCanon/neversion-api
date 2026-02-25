package com.neversion.panel.plan.infrastructure.adapters.in.rest.mapper;

import org.springframework.stereotype.Component;

import com.neversion.panel.plan.domain.model.Plan;
import com.neversion.panel.plan.domain.model.ProductPrice;
import com.neversion.panel.plan.domain.model.enums.AccountType;
import com.neversion.panel.plan.infrastructure.adapters.in.rest.dto.PlanRequest;
import com.neversion.panel.plan.infrastructure.adapters.in.rest.dto.PlanResponse;

@Component
public class PlanMapper {

    public static Plan toDomain(PlanRequest request) {
        if (request == null)
            return null;

        return Plan.builder()
                .price(new ProductPrice(request.priceAmount()))
                .duration(request.duration())
                .accountType(AccountType.valueOf(request.accountType().toUpperCase()))
                .build();
    }

    public static PlanResponse toResponse(Plan plan) {
        if (plan == null)
            return null;

        return new PlanResponse(
                plan.getId(),
                plan.getPrice(),
                plan.getDuration(),
                plan.getAccountType());
    }
}
