package com.neversion.panel.sservice.infrastructure.adapters.in.rest.dto;

import java.util.List;

import com.neversion.panel.sserviceitem.infrastructure.adapters.in.rest.dto.SserviceItemRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SserviceRequest(

        @NotBlank(message = "Name is required") @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters") String name,
        String description,
        String imageUrl,
        String category,
        List<SserviceItemRequest> items) {
}
