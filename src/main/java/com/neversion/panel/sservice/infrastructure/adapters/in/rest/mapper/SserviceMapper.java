package com.neversion.panel.sservice.infrastructure.adapters.in.rest.mapper;

import org.springframework.stereotype.Component;

import com.neversion.panel.sserviceitem.infrastructure.adapters.in.rest.mapper.SserviceItemMapper;
import com.neversion.panel.sservice.domain.model.Sservice;
import com.neversion.panel.sservice.domain.model.enums.CategoryType;
import com.neversion.panel.sservice.infrastructure.adapters.in.rest.dto.SserviceRequest;
import com.neversion.panel.sservice.infrastructure.adapters.in.rest.dto.SserviceResponse;

@Component
public class SserviceMapper {

    public Sservice toDomain(SserviceRequest request) {

        if (request == null)
            return null;

        Sservice sservice = Sservice.builder()
                .name(request.name())
                .description(request.description())
                .imageUrl(request.imageUrl())
                .category(CategoryType.valueOf(request.category().toUpperCase()))
                .build();

        if (request.items() != null) {
            request.items().forEach(itemRequest -> {
                sservice.addItem(SserviceItemMapper.toDomain(itemRequest));
            });
        }
        return sservice;
    }

    public SserviceResponse toResponse(Sservice sservice) {

        if (sservice == null)
            return null;

        return new SserviceResponse(
                sservice.getId(),
                sservice.getName(),
                sservice.getDescription(),
                sservice.getCategory(),
                sservice.getItems().stream()
                        .map(SserviceItemMapper::toResponse)
                        .toList());
    }

    // private SserviceItemResponse toItemResponse(SserviceItem sserviceItem) {
    // return new SserviceItemResponse(
    // sserviceItem.getId(),
    // sserviceItem.getPrice(),
    // sserviceItem.getDuration(),
    // sserviceItem.getAccountType());
    // }
}
