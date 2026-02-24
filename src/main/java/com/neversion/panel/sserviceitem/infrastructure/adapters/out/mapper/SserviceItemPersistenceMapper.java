package com.neversion.panel.sserviceitem.infrastructure.adapters.out.mapper;

import org.springframework.stereotype.Component;

import com.neversion.panel.sserviceitem.domain.model.SserviceItem;
import com.neversion.panel.sserviceitem.domain.model.SservicePrice;
import com.neversion.panel.sserviceitem.infrastructure.adapters.out.SserviceItemEntity;

@Component
public class SserviceItemPersistenceMapper {

    public SserviceItem toDomain(SserviceItemEntity entity) {
        if (entity == null)
            return null;
        return SserviceItem.builder()
                .id(entity.getId())
                .price(new SservicePrice(entity.getPrice()))
                .duration(entity.getDuration())
                .accountType(entity.getAccountType())
                .build();
    }

    public SserviceItemEntity toEntity(SserviceItem domain) {
        if (domain == null)
            return null;
        return SserviceItemEntity.builder()
                .id(domain.getId())
                .price(domain.getPrice().amount()) // Extraemos el primitivo del Value Object
                .duration(domain.getDuration())
                .accountType(domain.getAccountType())
                .build();
    }
}
