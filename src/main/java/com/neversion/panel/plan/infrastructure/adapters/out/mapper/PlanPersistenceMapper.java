package com.neversion.panel.plan.infrastructure.adapters.out.mapper;

import org.springframework.stereotype.Component;

import com.neversion.panel.plan.domain.model.Plan;
import com.neversion.panel.plan.domain.model.ProductPrice;
import com.neversion.panel.plan.infrastructure.adapters.out.PlanEntity;

@Component
public class PlanPersistenceMapper {

    public Plan toDomain(PlanEntity entity) {
        if (entity == null)
            return null;
        return Plan.builder()
                .id(entity.getId())
                .price(new ProductPrice(entity.getPrice()))
                .duration(entity.getDuration())
                .accountType(entity.getAccountType())
                .build();
    }

    public PlanEntity toEntity(Plan domain) {
        if (domain == null)
            return null;
        return PlanEntity.builder()
                .id(domain.getId())
                .price(domain.getPrice().amount()) // Extraemos el primitivo del Value Object
                .duration(domain.getDuration())
                .accountType(domain.getAccountType())
                .build();
    }
}
