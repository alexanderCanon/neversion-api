package com.neversion.panel.sservice.infrastructure.adapters.in.rest.dto;

public record SserviceResponse(
    Integer id,
    String name,
    String description,
    Boolean isActive
) {

}
