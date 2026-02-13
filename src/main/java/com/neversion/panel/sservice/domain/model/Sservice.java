package com.neversion.panel.sservice.domain.model;

public record Sservice(
    Integer id,
    String name,
    String description,
    String imageUrl,
    Boolean isActive) {
}
