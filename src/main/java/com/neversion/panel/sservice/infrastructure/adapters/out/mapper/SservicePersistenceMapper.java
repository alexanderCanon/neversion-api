package com.neversion.panel.sservice.infrastructure.adapters.out.mapper;

import org.springframework.stereotype.Component;

import com.neversion.panel.sservice.domain.model.Sservice;
import com.neversion.panel.sservice.infrastructure.adapters.out.SserviceEntity;
import com.neversion.panel.sserviceitem.infrastructure.adapters.out.SserviceItemEntity;
import com.neversion.panel.sserviceitem.infrastructure.adapters.out.mapper.SserviceItemPersistenceMapper;

@Component
public class SservicePersistenceMapper {

    private final SserviceItemPersistenceMapper itemPersistenceMapper;

    public SservicePersistenceMapper(SserviceItemPersistenceMapper itemPersistenceMapper) {
        this.itemPersistenceMapper = itemPersistenceMapper;
    }

    public Sservice toDomain(SserviceEntity entity) {
        if (entity == null)
            return null;
        Sservice sservice = Sservice.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .imageUrl(entity.getImageUrl())
                .category(entity.getCategory())
                .build();

        if (entity.getItems() != null) {
            entity.getItems().forEach(item -> {
                sservice.addItem(itemPersistenceMapper.toDomain(item));
            });
        }
        return sservice;
    }

    public SserviceEntity toEntity(Sservice domain) {
        if (domain == null)
            return null;
        SserviceEntity entity = SserviceEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .description(domain.getDescription())
                .imageUrl(domain.getImageUrl())
                .category(domain.getCategory())
                .build();

        domain.getItems().forEach(itemDomain -> {
            SserviceItemEntity itemEntity = itemPersistenceMapper.toEntity(itemDomain);
            entity.addItem(itemEntity);
        });

        return entity;
    }
}
