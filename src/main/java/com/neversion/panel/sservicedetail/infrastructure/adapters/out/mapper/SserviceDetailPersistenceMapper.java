package com.neversion.panel.sservicedetail.infrastructure.adapters.out.mapper;

import org.springframework.stereotype.Component;

import com.neversion.panel.sservicedetail.domain.model.SserviceDetail;
import com.neversion.panel.sservicedetail.infrastructure.adapters.out.SserviceDetailEntity;

@Component
public class SserviceDetailPersistenceMapper {

    public SserviceDetail toDomain(SserviceDetailEntity entity) {
        return new SserviceDetail(
            entity.getId(),
            entity.getServiceId(),
            entity.getCategoryId(),
            entity.getService().getName(),
            entity.getCategory().getName(),
            entity.getPriceIndividual(),
            entity.getPriceFamiliar()
        );
    }

    public SserviceDetailEntity toEntity(SserviceDetail domain) {
        return new SserviceDetailEntity(
            domain.id(),
            domain.serviceId(),
            domain.categoryId(),
            domain.priceIndividual(),
            domain.priceFamiliar()
        );
    }
}
