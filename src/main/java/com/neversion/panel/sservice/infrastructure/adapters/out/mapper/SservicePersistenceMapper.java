package com.neversion.panel.sservice.infrastructure.adapters.out.mapper;

import org.springframework.stereotype.Component;

import com.neversion.panel.sservice.domain.model.Sservice;
import com.neversion.panel.sservice.infrastructure.adapters.out.SserviceEntity;

@Component
public class SservicePersistenceMapper {
    public Sservice toDomain(SserviceEntity sserviceEntity) {
        return new Sservice(
            sserviceEntity.getId(),
            sserviceEntity.getName(),
            sserviceEntity.getDescription(),
            sserviceEntity.getImageUrl(),
            sserviceEntity.getIsActive()
        );
    }

    public SserviceEntity toEntity(Sservice sservice) {
        return new SserviceEntity(
            sservice.id(),
            sservice.name(),
            sservice.description(),
            sservice.imageUrl(),
            sservice.isActive()
        );
    }
}
