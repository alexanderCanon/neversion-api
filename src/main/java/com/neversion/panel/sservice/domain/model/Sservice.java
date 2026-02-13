package com.neversion.panel.sservice.domain.model;

import java.time.Instant;

public record Sservice(
    Integer id,
    String name,
    String description,
    String imageUrl,
    Boolean isActive,
    Instant createdAt) {

}
