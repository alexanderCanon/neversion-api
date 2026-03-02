package com.neversion.panel.product.infrastructure.adapters.in.rest.dto;

import java.util.UUID;

public record ProductResponse(
                UUID id,
                String name,
                String description,
                String imageUrl,
                String category) {
}
