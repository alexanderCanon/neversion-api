package com.neversion.panel.sserviceitem.infrastructure.adapters.in.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.neversion.panel.sserviceitem.application.port.in.CreateSserviceItemUseCase;
import com.neversion.panel.sserviceitem.domain.model.SserviceItem;
import com.neversion.panel.sserviceitem.infrastructure.adapters.in.rest.dto.SserviceItemRequest;
import com.neversion.panel.sserviceitem.infrastructure.adapters.in.rest.dto.SserviceItemResponse;
import com.neversion.panel.sserviceitem.infrastructure.adapters.in.rest.mapper.SserviceItemMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/sservice-items")
public class SserviceItemPostController {

    private final CreateSserviceItemUseCase createSserviceItemUseCase;

    public SserviceItemPostController(CreateSserviceItemUseCase createSserviceItemUseCase) {
        this.createSserviceItemUseCase = createSserviceItemUseCase;
    }

    @PostMapping
    public ResponseEntity<SserviceItemResponse> createSserviceItem(@Valid @RequestBody SserviceItemRequest request) {
        if (request.serviceId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "serviceId is required to create a service item independently.");
        }

        SserviceItem sserviceItem = SserviceItemMapper.toDomain(request);
        SserviceItem createdItem = createSserviceItemUseCase.create(request.serviceId(), sserviceItem);
        SserviceItemResponse response = SserviceItemMapper.toResponse(createdItem);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
