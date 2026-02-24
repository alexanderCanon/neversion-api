package com.neversion.panel.sservice.infrastructure.adapters.in.rest.dto;

import java.util.List;

import com.neversion.panel.sserviceitem.infrastructure.adapters.in.rest.dto.SserviceItemResponse;
import com.neversion.panel.sservice.domain.model.enums.CategoryType;

public record SserviceResponse(
        Integer id,
        String name,
        String description,
        CategoryType category,
        List<SserviceItemResponse> items) {
}
