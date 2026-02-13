package com.neversion.panel.sservice.infrastructure.adapters.in.rest.mapper;

import org.springframework.stereotype.Component;

import com.neversion.panel.sservice.domain.model.Sservice;
import com.neversion.panel.sservice.infrastructure.adapters.in.rest.dto.SserviceRequest;
import com.neversion.panel.sservice.infrastructure.adapters.in.rest.dto.SserviceResponse;

@Component
public class SserviceMapper {

    public Sservice toDomain(SserviceRequest request) {
        return new Sservice(
            null,
            request.getName(),
            request.getDescription(),
            request.getImageUrl(),
            true
        );
    }

    public SserviceResponse toResponse(Sservice sservice) {
        return new SserviceResponse(
            sservice.id(),
            sservice.name(),
            sservice.description(),
            sservice.isActive()
        );
    }
}

